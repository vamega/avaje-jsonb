package org.example.builder;

import io.avaje.jsonb.Json;
import java.util.Objects;

/**
 * Test class that uses setter-style builder methods (with "set" prefix).
 */
@Json(builder = true)
public final class SetterStyleMessage {
    private final String subject;
    private final String body;
    private final String author;

    private SetterStyleMessage(String subject, String body, String author) {
        this.subject = subject;
        this.body = body;
        this.author = author;
    }

    private SetterStyleMessage(Builder builder) {
        this(builder.subject, builder.body, builder.author);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SetterStyleMessage) obj;
        return Objects.equals(this.subject, that.subject) &&
               Objects.equals(this.body, that.body) &&
               Objects.equals(this.author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, body, author);
    }

    @Override
    public String toString() {
        return "SetterStyleMessage[" + "subject=" + subject + 
               ", body=" + body + ", author=" + author + ']';
    }

    public static final class Builder {
        private String subject;
        private String body;
        private String author;

        private Builder() {}

        // Setter style - uses "set" prefix
        public Builder setSubject(String val) {
            subject = val;
            return this;
        }

        public Builder setBody(String val) {
            body = val;
            return this;
        }

        public Builder setAuthor(String val) {
            author = val;
            return this;
        }

        public SetterStyleMessage build() {
            return new SetterStyleMessage(this);
        }
    }
}