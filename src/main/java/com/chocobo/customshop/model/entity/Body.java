package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class Body extends BaseEntity {

    private StringSet shape;
    private long woodId;

    public Body(long entityId, StringSet shape, long woodId) {
        super(entityId);
        this.shape = shape;
        this.woodId = woodId;
    }

    public StringSet getShape() {
        return shape;
    }

    public void setShape(StringSet shape) {
        this.shape = shape;
    }

    public long getWoodId() {
        return woodId;
    }

    public void setWoodId(long woodId) {
        this.woodId = woodId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Body body = (Body) obj;
        return super.equals(body) && Objects.equals(body.shape, shape) && body.woodId == woodId;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + super.hashCode();

        result = prime * result + (shape != null ? shape.hashCode() : 0);
        result = prime * result + Long.hashCode(woodId);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Body ");
        builder.append(super.toString()).append(": (");
        builder.append("shape = ").append(shape).append(", ");
        builder.append("wood id = ").append(woodId).append(")");

        return builder.toString();
    }
}
