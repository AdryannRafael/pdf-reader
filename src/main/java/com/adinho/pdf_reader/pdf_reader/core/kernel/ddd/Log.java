package com.adinho.pdf_reader.pdf_reader.core.kernel.ddd;

import java.time.Instant;

public abstract class Log {
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public Log() {
    }

    public Log(Instant createdAt,
               Instant updatedAt,
               Instant deletedAt
    ) {
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
    }

    protected void criado(){
        this.createdAt = Instant.now();
    }

    protected void editado(){
        this.updatedAt = Instant.now();
    }

    protected void deletado(){
        this.deletedAt = Instant.now();
    }

    protected void recussitar(){
        this.deletedAt = null;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant deletedAt() {
        return deletedAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

}
