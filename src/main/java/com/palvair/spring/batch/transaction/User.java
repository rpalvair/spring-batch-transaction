package com.palvair.spring.batch.transaction;

import java.util.Optional;

public class User {

    private final String name;
    private final Boolean done;

    public User(final String name, final boolean done) {
        this.name = name;
        this.done = done;
    }

    public User(final String name) {
        this.name = name;
        this.done = null;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return Optional.ofNullable(done)
                .orElse(false);
    }
}
