package org.example.customer.guava;

import io.avaje.jsonb.Json;
import java.util.Map;

@Json
public record BookWithMap(String title, Map<String, String> contributors) { }
