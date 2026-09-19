package com.watashi.demobank.infrastructure.repository.common;

import org.springframework.cglib.core.internal.Function;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

public abstract class AbstractMemoryRepository<T> {

    protected final List<T> items = new ArrayList<>();
    protected final AtomicLong idSequence = new AtomicLong();

    private final Function<T, Long> getId;
    private final BiConsumer<T, Long> setId;
    private final Function<T, Instant> getCreatedAt;
    private final BiConsumer<T, Instant> setCreatedAt;
    private final BiConsumer<T, Instant> setUpdatedAt;

    public AbstractMemoryRepository(
            Function<T, Long> getId,
            BiConsumer<T, Long> setId,
            Function<T, Instant> getCreatedAt,
            BiConsumer<T, Instant> setCreatedAt,
            BiConsumer<T, Instant> setUpdatedAt
    ) {
        this.getId = getId;
        this.setId = setId;
        this.getCreatedAt = getCreatedAt;
        this.setCreatedAt = setCreatedAt;
        this.setUpdatedAt = setUpdatedAt;
    }

    public Optional<T> findById(Long id) {
        return items.stream()
                .filter(item -> Objects.equals(getId.apply(item), id))
                .findFirst();
    }

    public List<T> findAll() {
        return new ArrayList<>(items);
    }

    public T save(T entity) {
        Instant now = Instant.now();
        if (getId.apply(entity) == null) {
            setId.accept(entity, idSequence.getAndIncrement());
            if (getCreatedAt.apply(entity) == null) {
                setCreatedAt.accept(entity, now);
            }
            setUpdatedAt.accept(entity, now);
            items.add(entity);
        } else {
            setUpdatedAt.accept(entity, now);
            deleteById(getId.apply(entity));
            items.add(entity);
        }
        return entity;
    }

    public void deleteById(Long id) {
        items.removeIf(item -> Objects.equals(getId.apply(item), id));
    }


}
