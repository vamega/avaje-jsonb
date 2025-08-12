package org.example.customer.guava;

import io.avaje.jsonb.Json;
import java.util.ArrayList;

@Json
public record BookWithArrayList(String title, ArrayList<String> authors) { }
