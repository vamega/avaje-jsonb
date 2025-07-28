package org.example.builder;

import io.avaje.jsonb.Json;
import java.util.Objects;

/**
 * Test class that uses custom builder method names.
 */
@Json
@Json.Builder(setterPrefix = "with")
public final class WithStyleMessage {
    private final String id;
    private final String text;

    private WithStyleMessage(String id, String text) {
        this.id = id;
        this.text = text;
    }

    private WithStyleMessage(Builder builder) {
        this(builder.id, builder.text);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (WithStyleMessage) obj;
        return Objects.equals(this.id, that.id) && Objects.equals(this.text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public String toString() {
        return "CustomBuilderMessage[" + "id=" + id + ", " + "text=" + text + ']';
    }

    public static final class Builder {
        private String id;
        private String text;

        private Builder() {}

        public Builder withId(String val) {
            id = val;
            return this;
        }

        public Builder withText(String val) {
            text = val;
            return this;
        }

        public WithStyleMessage build() {
            return new WithStyleMessage(this);
        }
    }
}
