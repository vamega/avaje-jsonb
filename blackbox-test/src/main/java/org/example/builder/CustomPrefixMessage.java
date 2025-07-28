package org.example.builder;

import io.avaje.jsonb.Json;
import java.util.Objects;

/**
 * Test class that demonstrates custom prefix support beyond the basic "with", "set", and no-prefix patterns.
 * This builder uses "add" as the prefix to demonstrate the general algorithm.
 */
@Json
@Json.Builder(setterPrefix = "add")
public final class CustomPrefixMessage {
    private final String name;
    private final String description;
    private final int priority;

    private CustomPrefixMessage(String name, String description, int priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    private CustomPrefixMessage(Builder builder) {
        this(builder.name, builder.description, builder.priority);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CustomPrefixMessage) obj;
        return Objects.equals(this.name, that.name) && 
               Objects.equals(this.description, that.description) && 
               this.priority == that.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, priority);
    }

    @Override
    public String toString() {
        return "CustomPrefixMessage[" + 
               "name=" + name + ", " + 
               "description=" + description + ", " + 
               "priority=" + priority + ']';
    }

    public static final class Builder {
        private String name;
        private String description;
        private int priority;

        private Builder() {}

        // Custom "add" prefix - should be auto-detected by the general algorithm
        public Builder addName(String val) {
            name = val;
            return this;
        }

        public Builder addDescription(String val) {
            description = val;
            return this;
        }

        public Builder addPriority(int val) {
            priority = val;
            return this;
        }

        public CustomPrefixMessage build() {
            return new CustomPrefixMessage(this);
        }
    }
}