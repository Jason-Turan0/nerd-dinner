package com.dbs.sae.training.nerddinner.domain;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExtensions {

    public static <F, T, R> List<R> MapStreams(
            Supplier<Stream<F>> fromStream,
            Supplier<Stream<T>> toStream,
            BiFunction<F, T, Boolean> filter,
            BiFunction<F, Optional<T>, R> mappingFunc) {
        List<R> mapped = fromStream.get().map(fromEntity -> {
            Optional<T> matchingToEntity = toStream.get().filter(toEntity -> filter.apply(fromEntity, toEntity)).findFirst();
            return mappingFunc.apply(fromEntity, matchingToEntity);
        }).collect(Collectors.toList());
        return mapped;
    }
}
