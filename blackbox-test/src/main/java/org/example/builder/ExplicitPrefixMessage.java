package org.example.builder;

import io.avaje.jsonb.Json;
import java.util.Objects;

/**
 * Test class that demonstrates explicit prefix configuration using @Json.Builder annotation.
 * This allows for any custom prefix without relying on auto-detection.
 */
@Json
@Json.Builder(setterPrefix = "use")
public final class ExplicitPrefixMessage {
    private final String title;
    private final String content;

    private ExplicitPrefixMessage(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private ExplicitPrefixMessage(Builder builder) {
        this(builder.title, builder.content);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ExplicitPrefixMessage) obj;
        return Objects.equals(this.title, that.title) && Objects.equals(this.content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }

    @Override
    public String toString() {
        return "ExplicitPrefixMessage[" + "title=" + title + ", " + "content=" + content + ']';
    }

    public static final class Builder {
        private String title;
        private String content;

        private Builder() {}

        // Custom "use" prefix - explicitly configured via @Json.Builder annotation
        public Builder useTitle(String val) {
            title = val;
            return this;
        }

        public Builder useContent(String val) {
            content = val;
            return this;
        }

        public ExplicitPrefixMessage build() {
            return new ExplicitPrefixMessage(this);
        }
    }
}