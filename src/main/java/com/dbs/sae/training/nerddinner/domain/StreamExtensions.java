package com.dbs.sae.training.nerddinner.domain;

import org.javatuples.Pair;

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

    public static <L, R> List<Pair<Optional<L>, Optional<R>>> OuterJoin(
            Supplier<Stream<L>> leftStream,
            Supplier<Stream<R>> rightStream,
            BiFunction<L, R, Boolean> comparisonFunc) {
        Stream<Pair<Optional<L>, Optional<R>>> leftMappedToRight = leftStream.get().map(leftEntity -> {
            Optional<R> matchingR = rightStream.get().filter(rightEntity -> comparisonFunc.apply(leftEntity, rightEntity)).findFirst();
            return Pair.with(Optional.of(leftEntity), matchingR);
        });

        Stream<Pair<Optional<L>, Optional<R>>> rightMappedToLeft = rightStream.get()
                .map(rightEntity -> {
                    Optional<L> matchingL = leftStream.get().filter(leftEntity -> comparisonFunc.apply(leftEntity, rightEntity)).findFirst();
                    return Pair.with(matchingL, Optional.of(rightEntity));
                })
                .filter(p -> !p.getValue0().isPresent());
        List<Pair<Optional<L>, Optional<R>>> merged = Stream.concat(leftMappedToRight, rightMappedToLeft).collect(Collectors.toList());
        return merged;
    }

}
