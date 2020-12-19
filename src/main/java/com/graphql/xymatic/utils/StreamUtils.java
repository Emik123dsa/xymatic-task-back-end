package com.graphql.xymatic.utils;

import java.util.Collection;
import java.util.stream.Stream;

public final class StreamUtils {

  public static <T> Stream<T> collectionStreams(Collection<T> collection) {
    return !(collection.isEmpty() || collection == null)
      ? collection.stream()
      : Stream.empty();
  }
}
