package io.avaje.jsonb.generator;

import javax.lang.model.element.TypeElement;

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
   * Create builder config from @Json.Builder annotation.
   */
  static BuilderConfig fromBuilderAnnotation(TypeElement typeElement) {
    var builderPrism = BuilderPrism.getInstanceOn(typeElement);
    if (builderPrism == null) {
      return disabled();
    }

    String setterPrefix = builderPrism.setterPrefix();
    String builderMethod = builderPrism.builderMethod();
    String buildMethod = builderPrism.buildMethod();

    return new BuilderConfig(true, setterPrefix, builderMethod, buildMethod);
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
