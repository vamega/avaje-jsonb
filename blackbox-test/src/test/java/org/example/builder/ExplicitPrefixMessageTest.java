package org.example.builder;

import io.avaje.jsonb.Jsonb;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExplicitPrefixMessageTest {

  private final Jsonb jsonb = Jsonb.builder().build();

  @Test
  void testSerialization() {
    ExplicitPrefixMessage message = ExplicitPrefixMessage.builder()
        .useTitle("Test Title")
        .useContent("Test Content")
        .build();

    String json = jsonb.toJson(message);
    assertThat(json).isEqualTo("{\"title\":\"Test Title\",\"content\":\"Test Content\"}");
  }

  @Test
  void testDeserialization() {
    String json = "{\"title\":\"Test Title\",\"content\":\"Test Content\"}";
    
    ExplicitPrefixMessage message = jsonb.type(ExplicitPrefixMessage.class).fromJson(json);
    
    assertThat(message.getTitle()).isEqualTo("Test Title");
    assertThat(message.getContent()).isEqualTo("Test Content");
  }

  @Test
  void testRoundTrip() {
    ExplicitPrefixMessage original = ExplicitPrefixMessage.builder()
        .useTitle("Explicit Configuration")
        .useContent("Testing explicit prefix configuration")
        .build();

    String json = jsonb.toJson(original);
    ExplicitPrefixMessage deserialized = jsonb.type(ExplicitPrefixMessage.class).fromJson(json);

    assertThat(deserialized).isEqualTo(original);
  }
}