package org.example.builder;

import static org.assertj.core.api.Assertions.assertThat;

import io.avaje.jsonb.Jsonb;
import org.junit.jupiter.api.Test;

class SetterStyleMessageTest {

  @Test
  void toJson() {
    var message = SetterStyleMessage.builder()
        .setSubject("Important")
        .setBody("This is the message body")
        .setAuthor("John Doe")
        .build();

    var jsonb = Jsonb.instance();
    var json = jsonb.toJson(message);
    
    assertThat(json).contains("\"subject\":\"Important\"");
    assertThat(json).contains("\"body\":\"This is the message body\"");
    assertThat(json).contains("\"author\":\"John Doe\"");
  }

  @Test
  void fromJson() {
    var json = "{\"subject\":\"Test\",\"body\":\"Message\",\"author\":\"Jane\"}";
    
    var jsonb = Jsonb.instance();
    var message = jsonb.type(SetterStyleMessage.class).fromJson(json);
    
    assertThat(message.getSubject()).isEqualTo("Test");
    assertThat(message.getBody()).isEqualTo("Message");
    assertThat(message.getAuthor()).isEqualTo("Jane");
  }

  @Test
  void roundTrip() {
    var original = SetterStyleMessage.builder()
        .setSubject("Round Trip Test")
        .setBody("Testing setter style builder")
        .setAuthor("Test Author")
        .build();

    var jsonb = Jsonb.instance();
    var json = jsonb.toJson(original);
    var deserialized = jsonb.type(SetterStyleMessage.class).fromJson(json);
    
    assertThat(deserialized).isEqualTo(original);
  }
}