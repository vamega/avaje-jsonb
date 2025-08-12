package org.example.customer.guava;

import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Does not work with current version")
class BookWithHashMapTest {

  private static final Jsonb jsonb = Jsonb.builder().build();
  private static final JsonType<BookWithHashMap> jsonType = jsonb.type(BookWithHashMap.class);
  private static final BookWithHashMap sample_book = new BookWithHashMap("Dune",
    new HashMap<>(Map.of("author", "Frank Herbert", "editor", "John Campbell")));

  @Test
  void testSerialization() {
    String asJson = jsonb.toJson(sample_book);
    assertThat(asJson).contains("""
      "title":"Dune\"""");
    assertThat(asJson).contains("""
      "contributors":{""");
    assertThat(asJson).contains("""
      "author":"Frank Herbert\"""");
    assertThat(asJson).contains("""
      "editor":"John Campbell\"""");
  }

  @Test
  void testDeserialization() {
    var bookAsJson = """
      {"title":"Dune","contributors":{"author":"Frank Herbert","editor":"John Campbell"}}""";

    BookWithHashMap deserializedBook = jsonType.fromJson(bookAsJson);
    assertThat(deserializedBook).isEqualTo(sample_book);
  }

  @Test
  void testRoundTrip() {
    var originalContributors = new HashMap<>(Map.of(
      "author", "Isaac Asimov",
      "editor", "John W. Campbell",
      "publisher", "Gnome Press"
    ));
    var originalBook = new BookWithHashMap("Foundation", originalContributors);

    String json = jsonb.toJson(originalBook);
    BookWithHashMap deserializedBook = jsonType.fromJson(json);

    assertThat(deserializedBook.title()).isEqualTo(originalBook.title());
    assertThat(deserializedBook.contributors()).containsExactlyInAnyOrderEntriesOf(originalBook.contributors());
    assertThat(deserializedBook.contributors()).isInstanceOf(HashMap.class);
  }
}
