package org.example.builder;

import io.avaje.jsonb.Json;
import java.util.Objects;

/**
 * Test class that mimics Lombok's default builder style (no prefix).
 */
@Json(builder = true)
public final class LombokStyleMessage {
    private final String title;
    private final String content;

    private LombokStyleMessage(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private LombokStyleMessage(Builder builder) {
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
        var that = (LombokStyleMessage) obj;
        return Objects.equals(this.title, that.title) && Objects.equals(this.content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }

    @Override
    public String toString() {
        return "LombokStyleMessage[" + "title=" + title + ", " + "content=" + content + ']';
    }

    public static final class Builder {
        private String title;
        private String content;

        private Builder() {}

        // Lombok default style - no prefix
        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
            return this;
        }

        public LombokStyleMessage build() {
            return new LombokStyleMessage(this);
        }
    }
}