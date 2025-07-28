package org.example.builder;

import io.avaje.jsonb.Jsonb;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomPrefixMessageTest {

  private final Jsonb jsonb = Jsonb.builder().build();

  @Test
  void testSerialization() {
    CustomPrefixMessage message = CustomPrefixMessage.builder()
        .addName("Test Name")
        .addDescription("Test Description")
        .addPriority(1)
        .build();

    String json = jsonb.toJson(message);
    assertThat(json).isEqualTo("{\"name\":\"Test Name\",\"description\":\"Test Description\",\"priority\":1}");
  }

  @Test
  void testDeserialization() {
    String json = "{\"name\":\"Test Name\",\"description\":\"Test Description\",\"priority\":1}";
    
    CustomPrefixMessage message = jsonb.type(CustomPrefixMessage.class).fromJson(json);
    
    assertThat(message.getName()).isEqualTo("Test Name");
    assertThat(message.getDescription()).isEqualTo("Test Description");
    assertThat(message.getPriority()).isEqualTo(1);
  }

  @Test
  void testRoundTrip() {
    CustomPrefixMessage original = CustomPrefixMessage.builder()
        .addName("Round Trip")
        .addDescription("Testing round trip")
        .addPriority(5)
        .build();

    String json = jsonb.toJson(original);
    CustomPrefixMessage deserialized = jsonb.type(CustomPrefixMessage.class).fromJson(json);

    assertThat(deserialized).isEqualTo(original);
  }
}