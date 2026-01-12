package com.adinho.pdf_reader.pdf_reader.core.processo;


import com.adinho.pdf_reader.pdf_reader.core.kernel.ddd.Entity;
import com.adinho.pdf_reader.pdf_reader.core.kernel.ddd.Identify;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.Error;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Process<T extends Identify> extends Entity<T> {
    private boolean finished = false;

    private Instant startedAt;
    private Instant failedAt;
    private Instant finishedAt;
    protected final Map<String, Object> metadata = new HashMap<>();

    public Process(T id) {
        super(id);
    }

    public Process(T id,
                   boolean finished, Instant startedAt, Instant finishedAt, Instant failedAt,
                   Instant createdAt, Instant updatedAt, Instant deletedAt) {
        super(id, createdAt, updatedAt, deletedAt);
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.failedAt = failedAt;
        this.finished = finished;
    }

    protected void start(){
        this.startedAt = Instant.now();
    }

    protected void finish(){
        this.finished = true;
        this.finishedAt = Instant.now();
    }

    protected void fail(){
        this.failedAt = Instant.now();
    }

    public abstract void failed(Error error, String cause);

    public abstract void retry();

    protected Process reprocess(){
        this.finished = false;
        this.failedAt = null;
        return this;
    }

    public Instant finishedAt() {
        return finishedAt;
    }

    public Instant startedAt() {
        return startedAt;
    }

    public Instant failedAt() {
        return failedAt;
    }

    public boolean isFinished() {
        return finished;
    }

    public Map<String, Object> getMetadata() {
        return Collections.unmodifiableMap(metadata);
    }
}

