package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class StringSet extends BaseEntity {

    private String name;
    private String size;

    public StringSet(long entityId, String name, String size) {
        super(entityId);
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        StringSet stringSet = (StringSet) obj;
        return super.equals(stringSet) && Objects.equals(stringSet.name, name)
                && Objects.equals(stringSet.size, size);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + super.hashCode();

        result = prime * result + (name != null ? name.hashCode() : 0);
        result = prime * result + (size != null ? size.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Wood ");
        builder.append(super.toString()).append(": (");
        builder.append("name = ").append(name).append(", ");
        builder.append("size = ").append(size).append(")");

        return builder.toString();
    }
}
