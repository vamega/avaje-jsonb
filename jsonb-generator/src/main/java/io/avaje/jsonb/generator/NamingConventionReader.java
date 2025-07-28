package io.avaje.jsonb.generator;

import static io.avaje.jsonb.generator.ProcessingContext.importedJson;

import javax.lang.model.element.TypeElement;

final class NamingConventionReader {

  private final String typeProperty;
  private final boolean caseInsensitiveKeys;
  private final boolean builder;
  private final NamingConvention namingConvention;

  NamingConventionReader(TypeElement element) {
    final var jsonOptional = JsonPrism.getOptionalOn(element).or(() -> importedJson(element));
    if (jsonOptional.isEmpty()) {
      typeProperty = null;
      namingConvention = null;
      caseInsensitiveKeys = false;
      builder = false;
      return;
    }
    final var jsonAnnotation = jsonOptional.get();
    namingConvention = NamingConvention.of(naming(jsonAnnotation.naming()));
    typeProperty = Util.escapeQuotes(jsonAnnotation.typeProperty());
    caseInsensitiveKeys = jsonAnnotation.caseInsensitiveKeys();
    builder = false; // We're not using this field, detection is done in ClassReader
  }

  static Naming naming(String entry) {
    final int pos = entry.lastIndexOf('.');
    if (pos > -1) {
      entry = entry.substring(pos + 1);
    }
    return Naming.valueOf(entry);
  }

  NamingConvention get() {
    return namingConvention != null ? namingConvention : NamingConvention.of(Naming.Match);
  }

  String typeProperty() {
    return typeProperty;
  }

  boolean isCaseInsensitiveKeys() {
    return caseInsensitiveKeys;
  }
}
