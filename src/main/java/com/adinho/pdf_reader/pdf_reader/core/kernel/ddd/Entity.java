package com.adinho.pdf_reader.pdf_reader.core.kernel.ddd;


import java.time.Instant;
import java.util.Objects;

public class Entity<T extends Identify> extends Log {
    private T id;

    public Entity(T id) {
        super();
        this.id = Objects.requireNonNull(id);
    }

    public Entity(T id,Instant createdAt, Instant updatedAt, Instant deletedAt) {
        super(createdAt, deletedAt, updatedAt);
        this.id = id;
    }

    public final T getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
