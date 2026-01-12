package com.adinho.pdf_reader.pdf_reader.core.kernel.ddd;

import java.time.Instant;

public class AggregateRoot<T extends Identify> extends Entity<T>{
    public AggregateRoot(T id) {
        super(id);
    }

    public AggregateRoot(T id, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        super(id, createdAt, deletedAt, updatedAt);
    }
}
