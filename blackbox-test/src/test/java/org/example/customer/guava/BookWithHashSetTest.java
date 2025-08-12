package org.example.customer.guava;

import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BookWithHashSetTest {

  private static final Jsonb jsonb = Jsonb.builder().build();
  private static final JsonType<BookWithHashSet> jsonType = jsonb.type(BookWithHashSet.class);
  private static final BookWithHashSet sample_book = new BookWithHashSet("1984",
    new HashSet<>(Set.of("George Orwell")));

  @Test
  void testSerialization() {
    String asJson = jsonb.toJson(sample_book);
    assertThat(asJson).contains("""
      "title":"1984\"""");
    assertThat(asJson).contains("""
      "authors":["George Orwell"]""");
  }

  @Test
  void testDeserialization() {
    var bookAsJson = """
      {"title":"1984","authors":["George Orwell"]}""";

    BookWithHashSet book = jsonType.fromJson(bookAsJson);
    assertThat(book.title()).isEqualTo("1984");
    assertThat(book.authors()).containsExactly("George Orwell");
    assertThat(book.authors()).isInstanceOf(HashSet.class);
  }

  @Test
  void testRoundTrip() {
    var originalAuthors = new HashSet<>(Set.of("Author One", "Author Two", "Author Three"));
    var originalBook = new BookWithHashSet("Round Trip Test", originalAuthors);

    String json = jsonb.toJson(originalBook);
    BookWithHashSet deserializedBook = jsonType.fromJson(json);

    assertThat(deserializedBook.title()).isEqualTo(originalBook.title());
    assertThat(deserializedBook.authors()).containsExactlyInAnyOrderElementsOf(originalBook.authors());
    assertThat(deserializedBook.authors()).isInstanceOf(HashSet.class);
  }
}
