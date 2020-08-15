package luccas.dev.logmanager.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public abstract class Pages {

    private Pages() {}

    public static final <D, T> org.springframework.data.domain.Page<T> duplicate(
            List<T> data,
            Pageable pageable,
            long TotalElements
    ) {
        return new PageImpl<>(
                data,
                pageable,
                TotalElements
        );
    }

    @SafeVarargs
    public static final <D, T> org.springframework.data.domain.Page<T> from(
            org.springframework.data.domain.Page<D> actualPage,
            Function<D, T> mapper,
            Consumer<T>... builders
    ) {
        return new PageImpl<>(
                actualPage
                        .stream()
                        .map(data -> applyMapperAndBuilder(data, mapper, builders))
                        .collect(Collectors.toList()),
                actualPage.getPageable(),
                actualPage.getTotalElements()
        );
    }

    @SafeVarargs
    private static final <D, T> T applyMapperAndBuilder(
            D data,
            Function<D, T> mapper,
            Consumer<T>... builders
    ) {
        T convertedData = mapper.apply(data);
        if (Objects.nonNull(builders)) {
            for (Consumer<T> builder : builders) {
                builder.accept(convertedData);
            }
        }
        return convertedData;
    }
}