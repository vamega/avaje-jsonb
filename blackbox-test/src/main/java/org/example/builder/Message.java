package org.example.builder;

import io.avaje.jsonb.Json;
import java.util.Objects;

@Json(builder = true)
public final class Message {
    private final String message;
    private final String greeting;

    private Message(String message, String greeting) {
        this.message = message;
        this.greeting = greeting;
    }

    private Message(Builder builder) {
        this(builder.message, builder.greeting);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Message message) {
        var builder = new Builder();
        builder.message = message.getMessage();
        builder.greeting = message.getGreeting();
        return builder;
    }

    public String getMessage() {
        return message;
    }

    public String getGreeting() {
        return greeting;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Message) obj;
        return Objects.equals(this.message, that.message) && Objects.equals(this.greeting, that.greeting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, greeting);
    }

    @Override
    public String toString() {
        return "Message[" + "message=" + message + ", " + "greeting=" + greeting + ']';
    }

    public static final class Builder {
        private String message;
        private String greeting;

        private Builder() {}

        public static Builder builder() {
            return new Builder();
        }

        public Builder withMessage(String val) {
            message = val;
            return this;
        }

        public Builder withGreeting(String val) {
            greeting = val;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
