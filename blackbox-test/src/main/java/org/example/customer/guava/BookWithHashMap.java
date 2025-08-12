package org.example.customer.guava;

import io.avaje.jsonb.Json;
import java.util.HashMap;

@Json
public record BookWithHashMap(String title, HashMap<String, String> contributors) { }
