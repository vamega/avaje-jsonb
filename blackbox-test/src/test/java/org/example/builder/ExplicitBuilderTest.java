package org.example.builder;

import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExplicitBuilderTest {

  private final Jsonb jsonb = Jsonb.builder().build();

  @Test
  void testCustomPrefixMessage() {
    JsonType<CustomPrefixMessage> type = jsonb.type(CustomPrefixMessage.class);
    
    CustomPrefixMessage original = CustomPrefixMessage.builder()
      .addName("Test Name")
      .addDescription("Test Description")  
      .addPriority(5)
      .build();
    
    String json = type.toJson(original);
    CustomPrefixMessage deserialized = type.fromJson(json);
    
    assertThat(deserialized).isEqualTo(original);
    assertThat(deserialized.getName()).isEqualTo("Test Name");
    assertThat(deserialized.getDescription()).isEqualTo("Test Description");
    assertThat(deserialized.getPriority()).isEqualTo(5);
  }

  @Test
  void testLombokStyleMessage() {
    JsonType<LombokStyleMessage> type = jsonb.type(LombokStyleMessage.class);
    
    LombokStyleMessage original = LombokStyleMessage.builder()
      .title("Test Title")
      .content("Test Content")
      .build();
    
    String json = type.toJson(original);
    LombokStyleMessage deserialized = type.fromJson(json);
    
    assertThat(deserialized).isEqualTo(original);
    assertThat(deserialized.getTitle()).isEqualTo("Test Title");
    assertThat(deserialized.getContent()).isEqualTo("Test Content");
  }

  @Test
  void testSetterStyleMessage() {
    JsonType<SetterStyleMessage> type = jsonb.type(SetterStyleMessage.class);
    
    SetterStyleMessage original = SetterStyleMessage.builder()
      .setSubject("Test Subject")
      .setBody("Test Body")
      .setAuthor("Test Author")
      .build();
    
    String json = type.toJson(original);
    SetterStyleMessage deserialized = type.fromJson(json);
    
    assertThat(deserialized).isEqualTo(original);
    assertThat(deserialized.getSubject()).isEqualTo("Test Subject");
    assertThat(deserialized.getBody()).isEqualTo("Test Body");
    assertThat(deserialized.getAuthor()).isEqualTo("Test Author");
  }
}