package org.example.builder;

import io.avaje.jsonb.Jsonb;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageTest {

  private final Jsonb jsonb = Jsonb.builder().build();

  @Test
  void testSerializeDeserialize() {
    // Create a message using the builder pattern
    Message message = Message.builder()
        .withMessage("Hello, world!")
        .withGreeting("Welcome")
        .build();

    // Serialize to JSON
    String json = jsonb.toJson(message);

    // Verify JSON contains expected values
    assertThat(json).contains("\"message\":\"Hello, world!\"");
    assertThat(json).contains("\"greeting\":\"Welcome\"");

    // Deserialize back to Message object
    Message deserialized = jsonb.type(Message.class).fromJson(json);

    // Verify deserialized object equals original
    assertThat(deserialized).isEqualTo(message);
    assertThat(deserialized.getMessage()).isEqualTo("Hello, world!");
    assertThat(deserialized.getGreeting()).isEqualTo("Welcome");
  }

  @Test
  void testDeserializeFromJson() {
    // JSON string to deserialize
    String json = "{\"message\":\"Test message\",\"greeting\":\"Hello\"}";

    // Deserialize to Message object
    Message message = jsonb.type(Message.class).fromJson(json);

    // Verify deserialized object has correct values
    assertThat(message.getMessage()).isEqualTo("Test message");
    assertThat(message.getGreeting()).isEqualTo("Hello");
  }
}
