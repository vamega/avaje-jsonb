package io.avaje.jsonb.generator;

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
   * Create builder config from JsonPrism annotation.
   */
  static BuilderConfig fromPrism(JsonPrism jsonPrism) {
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
    
    // Use legacy builder() flag
    if (legacyBuilder != null && legacyBuilder) {
      return new BuilderConfig(true, "with", "builder", "build");
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
}