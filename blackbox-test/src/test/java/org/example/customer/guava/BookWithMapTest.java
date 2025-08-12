package org.example.customer.guava;

import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BookWithMapTest {

  private static final Jsonb jsonb = Jsonb.builder().build();
  private static final JsonType<BookWithMap> jsonType = jsonb.type(BookWithMap.class);
  private static final BookWithMap sample_book = new BookWithMap("The Hobbit",
    Map.of("author", "J.R.R. Tolkien", "illustrator", "Tolkien"));

  @Test
  void testSerialization() {
    String asJson = jsonb.toJson(sample_book);
    assertThat(asJson).contains("""
      "title":"The Hobbit\"""");
    assertThat(asJson).contains("""
      "contributors":{""");
    assertThat(asJson).contains("""
      "author":"J.R.R. Tolkien\"""");
    assertThat(asJson).contains("""
      "illustrator":"Tolkien\"""");
  }

  @Test
  void testDeserialization() {
    var bookAsJson = """
      {"title":"The Hobbit","contributors":{"author":"J.R.R. Tolkien","illustrator":"Tolkien"}}""";

    BookWithMap book = jsonType.fromJson(bookAsJson);
    assertThat(book.title()).isEqualTo("The Hobbit");
    assertThat(book.contributors().get("author")).isEqualTo("J.R.R. Tolkien");
    assertThat(book.contributors().get("illustrator")).isEqualTo("Tolkien");
  }

  @Test
  void testRoundTrip() {
    var originalContributors = Map.of(
      "author", "Ursula K. Le Guin",
      "editor", "Terry Carr",
      "publisher", "Ace Books"
    );
    var originalBook = new BookWithMap("The Left Hand of Darkness", originalContributors);

    String json = jsonb.toJson(originalBook);
    BookWithMap deserializedBook = jsonType.fromJson(json);

    assertThat(deserializedBook.title()).isEqualTo(originalBook.title());
    assertThat(deserializedBook.contributors()).containsExactlyInAnyOrderEntriesOf(originalBook.contributors());
  }
}
