package org.example.builder;

import static org.assertj.core.api.Assertions.assertThat;

import io.avaje.jsonb.Jsonb;
import org.junit.jupiter.api.Test;

class LombokStyleMessageTest {

  @Test
  void toJson() {
    var message = LombokStyleMessage.builder()
        .title("Test Title")
        .content("Test Content")
        .build();

    var jsonb = Jsonb.instance();
    var json = jsonb.toJson(message);
    
    assertThat(json).contains("\"title\":\"Test Title\"");
    assertThat(json).contains("\"content\":\"Test Content\"");
  }

  @Test
  void fromJson() {
    var json = "{\"title\":\"Hello\",\"content\":\"World\"}";
    
    var jsonb = Jsonb.instance();
    var message = jsonb.type(LombokStyleMessage.class).fromJson(json);
    
    assertThat(message.getTitle()).isEqualTo("Hello");
    assertThat(message.getContent()).isEqualTo("World");
  }

  @Test
  void roundTrip() {
    var original = LombokStyleMessage.builder()
        .title("Round Trip")
        .content("This should work")
        .build();

    var jsonb = Jsonb.instance();
    var json = jsonb.toJson(original);
    var deserialized = jsonb.type(LombokStyleMessage.class).fromJson(json);
    
    assertThat(deserialized).isEqualTo(original);
  }
}