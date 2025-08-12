/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.avaje.json.core;

import io.avaje.json.JsonAdapter;
import io.avaje.json.JsonReader;
import io.avaje.json.JsonWriter;
import io.avaje.json.view.ViewBuilder;
import io.avaje.json.view.ViewBuilderAware;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;

/**
 * Converts ArrayList to JSON arrays containing their converted contents.
 */
final class ArrayListAdapter<T> implements ViewBuilderAware, JsonAdapter<ArrayList<T>> {

  static <T> JsonAdapter<ArrayList<T>> create(JsonAdapter<T> elementAdapter) {
    return new ArrayListAdapter<>(elementAdapter);
  }

  private final JsonAdapter<T> elementAdapter;

  private ArrayListAdapter(JsonAdapter<T> elementAdapter) {
    this.elementAdapter = elementAdapter;
  }

  @Override
  public boolean isViewBuilderAware() {
    return elementAdapter.isViewBuilderAware();
  }

  @Override
  public ViewBuilderAware viewBuild() {
    return this;
  }

  @Override
  public void build(ViewBuilder builder, String name, MethodHandle handle) {
    builder.addArray(name, elementAdapter, handle);
  }

  @Override
  public ArrayList<T> fromJson(JsonReader reader) {
    ArrayList<T> result = new ArrayList<>();
    reader.beginArray();
    while (reader.hasNextElement()) {
      result.add(elementAdapter.fromJson(reader));
    }
    reader.endArray();
    return result;
  }

  @Override
  public void toJson(JsonWriter writer, ArrayList<T> value) {
    if (value == null) {
      writer.nullValue();
      return;
    }
    if (value.isEmpty()) {
      writer.emptyArray();
      return;
    }
    writer.beginArray();
    for (T element : value) {
      elementAdapter.toJson(writer, element);
    }
    writer.endArray();
  }

  @Override
  public String toString() {
    return elementAdapter + ".arrayList()";
  }
}