package io.avaje.jsonb.generator;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import java.util.List;
import java.util.ArrayList;

/**
 * Configuration for builder pattern deserialization.
 */
final class BuilderConfig {
  
  private final boolean enabled;
  private final String methodPrefix;
  private final String builderMethod;
  private final String buildMethod;
  
  BuilderConfig(boolean enabled, String methodPrefix, String builderMethod, String buildMethod) {
    this.enabled = enabled;
    this.methodPrefix = methodPrefix;
    this.builderMethod = builderMethod;
    this.buildMethod = buildMethod;
  }
  
  /**
   * Create a disabled builder config.
   */
  static BuilderConfig disabled() {
    return new BuilderConfig(false, "", "", "");
  }
  
  /**
   * Create builder config from JsonPrism annotation with automatic method prefix detection.
   */
  static BuilderConfig fromPrism(JsonPrism jsonPrism, TypeElement typeElement) {
    // Check if the old builder() method is used
    Boolean legacyBuilder = jsonPrism.builder();
    
    // Check if builderConfig is specified
    try {
      var builderConfigPrism = jsonPrism.builderConfig();
      if (builderConfigPrism != null && builderConfigPrism.enabled() != null && builderConfigPrism.enabled()) {
        return new BuilderConfig(
          true,
          builderConfigPrism.methodPrefix() != null ? builderConfigPrism.methodPrefix() : "with",
          builderConfigPrism.builderMethod() != null ? builderConfigPrism.builderMethod() : "builder",
          builderConfigPrism.buildMethod() != null ? builderConfigPrism.buildMethod() : "build"
        );
      }
    } catch (Exception e) {
      // BuilderPrism might not be generated yet, fall back to legacy
    }
    
    // Use legacy builder() flag with automatic prefix detection
    if (legacyBuilder != null && legacyBuilder) {
      String detectedPrefix = detectBuilderMethodPrefix(typeElement);
      return new BuilderConfig(true, detectedPrefix, "builder", "build");
    }
    
    return disabled();
  }
  
  boolean isEnabled() {
    return enabled;
  }
  
  String getMethodPrefix() {
    return methodPrefix;
  }
  
  String getBuilderMethod() {
    return builderMethod;
  }
  
  String getBuildMethod() {
    return buildMethod;
  }
  
  /**
   * Generate the method name for a field setter in the builder.
   */
  String getSetterMethodName(String fieldName) {
    if (methodPrefix.isEmpty()) {
      return fieldName;
    }
    return methodPrefix + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
  }
  
  
  /**
   * Detect the method prefix used by the builder class by analyzing its methods.
   */
  private static String detectBuilderMethodPrefix(TypeElement typeElement) {
    // Find the builder class - look for static inner class named "Builder"
    TypeElement builderClass = null;
    for (Element enclosedElement : typeElement.getEnclosedElements()) {
      if (enclosedElement.getKind() == ElementKind.CLASS
          && enclosedElement.getSimpleName().toString().equals("Builder")
          && enclosedElement.getModifiers().contains(Modifier.STATIC)) {
        builderClass = (TypeElement) enclosedElement;
        break;
      }
    }
    
    if (builderClass == null) {
      // If no inner Builder class found, default to "with"
      return "with";
    }
    
    // Get ALL public methods first to see what we're working with
    List<String> allMethods = new ArrayList<>();
    List<String> setterMethods = new ArrayList<>();
    for (ExecutableElement method : ElementFilter.methodsIn(builderClass.getEnclosedElements())) {
      String methodName = method.getSimpleName().toString();
      if (method.getModifiers().contains(Modifier.PUBLIC)) {
        allMethods.add(methodName + "(" + method.getParameters().size() + " params)");
        if (method.getParameters().size() == 1
            && !methodName.equals("build")  // Exclude build method
            && isBuilderReturnType(method.getReturnType(), builderClass)) { // Must return builder type
          setterMethods.add(methodName);
        }
      }
    }
    
    // If no setter methods found, try a more lenient approach
    if (setterMethods.isEmpty()) {
      // Just get all public methods with 1 parameter (except build)
      for (ExecutableElement method : ElementFilter.methodsIn(builderClass.getEnclosedElements())) {
        String methodName = method.getSimpleName().toString();
        if (method.getModifiers().contains(Modifier.PUBLIC)
            && method.getParameters().size() == 1
            && !methodName.equals("build")) {
          setterMethods.add(methodName);
        }
      }
    }
    
    if (setterMethods.isEmpty()) {
      return "with"; // Default fallback
    }
    
    // Analyze the method names to detect the common prefix
    String detectedPrefix = detectCommonPrefix(setterMethods);
    return detectedPrefix;
  }
  
  /**
   * Check if the return type is the builder type (for fluent setters).
   */
  private static boolean isBuilderReturnType(TypeMirror returnType, TypeElement builderClass) {
    String returnTypeName = returnType.toString();
    String builderClassName = builderClass.getSimpleName().toString();
    String builderQualifiedName = builderClass.getQualifiedName().toString();
    
    // Check various forms of the return type name
    return returnTypeName.equals(builderQualifiedName)
        || returnTypeName.equals(builderClassName)
        || returnTypeName.endsWith("." + builderClassName)
        || returnTypeName.equals("Builder"); // Simple case
  }
  
  /**
   * Detect the common prefix from a list of setter method names.
   */
  private static String detectCommonPrefix(List<String> methodNames) {
    if (methodNames.isEmpty()) {
      return "with";
    }
    
    // Check for common prefixes - need to be more careful about the logic
    boolean allStartWithWith = true;
    boolean allStartWithSet = true;
    
    for (String name : methodNames) {
      if (name.length() <= 4 || !name.startsWith("with") || !Character.isUpperCase(name.charAt(4))) {
        allStartWithWith = false;
      }
      if (name.length() <= 3 || !name.startsWith("set") || !Character.isUpperCase(name.charAt(3))) {
        allStartWithSet = false;
      }
    }
    
    if (allStartWithWith) {
      return "with";
    } else if (allStartWithSet) {
      return "set";
    } else {
      // Likely no prefix (like Lombok default)
      return "";
    }
  }
}