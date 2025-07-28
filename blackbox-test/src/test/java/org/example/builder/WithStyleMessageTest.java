package org.example.builder;

import static org.assertj.core.api.Assertions.assertThat;

import io.avaje.jsonb.Jsonb;
import org.junit.jupiter.api.Test;

class WithStyleMessageTest {

  @Test
  void toJson() {
    var message = WithStyleMessage.builder()
        .withId("123")
        .withText("Custom builder test")
        .build();

    var jsonb = Jsonb.instance();
    var json = jsonb.toJson(message);

    assertThat(json).contains("\"id\":\"123\"");
    assertThat(json).contains("\"text\":\"Custom builder test\"");
  }

  @Test
  void fromJson() {
    var json = "{\"id\":\"456\",\"text\":\"From JSON\"}";

    var jsonb = Jsonb.instance();
    var message = jsonb.type(WithStyleMessage.class).fromJson(json);

    assertThat(message.getId()).isEqualTo("456");
    assertThat(message.getText()).isEqualTo("From JSON");
  }

  @Test
  void roundTrip() {
    var original = WithStyleMessage.builder()
        .withId("round-trip-id")
        .withText("Testing custom builder methods")
        .build();

    var jsonb = Jsonb.instance();
    var json = jsonb.toJson(original);
    var deserialized = jsonb.type(WithStyleMessage.class).fromJson(json);

    assertThat(deserialized).isEqualTo(original);
  }
}
