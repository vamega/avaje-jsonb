import io.avaje.jsonb.spi.JsonbExtension;

module blackbox.test {

  requires static io.avaje.jsonb;
  requires static io.avaje.spi;
  requires java.validation;
  requires io.avaje.json.node;
  requires com.google.common;

  provides JsonbExtension
    with
      org.example.customer.customtype.CustomTypeComponent,
      org.example.other.custom.CustomJsonComponent,
      org.example.jsonb.GeneratedJsonComponent,
      org.example.pkg_private.PkgPrivateJsonComponent;

}
