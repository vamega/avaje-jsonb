package org.example.customer.guava;

import com.google.common.collect.ImmutableList;
import io.avaje.jsonb.Json;

@Json
public record BookWithImmutableList(String title, ImmutableList<String> authors) { }
