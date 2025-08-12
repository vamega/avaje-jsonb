package org.example.customer.guava;

import io.avaje.jsonb.Json;
import java.util.HashSet;

@Json
public record BookWithHashSet(String title, HashSet<String> authors) { }
