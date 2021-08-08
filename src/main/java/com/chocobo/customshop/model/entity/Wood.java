package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class Wood extends BaseEntity {

    private StringSet name;

    public Wood(long entityId, StringSet name) {
        super(entityId);
        this.name = name;
    }

    public StringSet getName() {
        return name;
    }

    public void setName(StringSet name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Wood wood = (Wood) obj;
        return super.equals(wood) && Objects.equals(wood.name, name);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + super.hashCode();

        result = prime * result + (name != null ? name.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Wood ");
        builder.append(super.toString()).append(": (");
        builder.append("name = ").append(name).append(")");

        return builder.toString();
    }
}
