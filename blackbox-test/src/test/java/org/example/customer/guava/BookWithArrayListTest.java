package org.example.customer.guava;

import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookWithArrayListTest {

  private static final Jsonb jsonb = Jsonb.builder().build();
  private static final JsonType<BookWithArrayList> jsonType = jsonb.type(BookWithArrayList.class);
  private static final BookWithArrayList sample_book = new BookWithArrayList("Flowers for Algernon",
    new ArrayList<>(List.of("Daniel Keyes")));

  @Test
  void testSerialization() {
    String asJson = jsonb.toJson(sample_book);
    assertThat(asJson).isEqualTo("""
      {"title":"Flowers for Algernon","authors":["Daniel Keyes"]}""");
  }

  @Test
  void testDeserialization() {
    var bookAsJson = """
      {"title":"Flowers for Algernon","authors":["Daniel Keyes"]}""";

    BookWithArrayList book = jsonType.fromJson(bookAsJson);
    assertThat(book).isEqualTo(sample_book);
  }

  @Test
  void testRoundTrip() {
    var originalAuthors = new ArrayList<>(Arrays.asList("Author One", "Author Two", "Author Three"));
    var originalBook = new BookWithArrayList("Round Trip Test", originalAuthors);

    String json = jsonb.toJson(originalBook);
    BookWithArrayList deserializedBook = jsonType.fromJson(json);

    assertThat(deserializedBook.title()).isEqualTo(originalBook.title());
    assertThat(deserializedBook.authors()).containsExactlyElementsOf(originalBook.authors());
    assertThat(deserializedBook.authors()).isInstanceOf(ArrayList.class);
  }
}
