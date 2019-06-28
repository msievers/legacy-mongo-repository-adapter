package com.github.msievers.legacy.mongorepositoryadapter.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Pet {

    @Id
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public Pet setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Pet setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) &&
            Objects.equals(name, pet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
