package org.example.customer.guava;

import com.google.common.collect.ImmutableList;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookWithImmutableListTest {

  private static final Jsonb jsonb = Jsonb.builder().build();
  private static final JsonType<BookWithImmutableList> jsonType = jsonb.type(BookWithImmutableList.class);
  private static final BookWithImmutableList sampleBook = new
    BookWithImmutableList("Flowers for Algernon", ImmutableList.of("Daniel Keyes"));

  @Test
  void testSerialization() {
    var type = jsonb.type(BookWithImmutableList.class);
    String asJson = type.toJson(sampleBook);
    assertThat(asJson).isEqualTo("""
      {"title":"Flowers for Algernon","authors":["Daniel Keyes"]}""");
  }

  @Test
  @Disabled("Does not work with current version")
  void testDeserialization() {
    var bookAsJson = """
      {"title":"Flowers for Algernon","authors":["Daniel Keyes"]}""";
    BookWithImmutableList book = jsonType.fromJson(bookAsJson);
    assertThat(sampleBook).isEqualTo(book);
  }
}
