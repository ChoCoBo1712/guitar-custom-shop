package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class Body extends AbstractEntity {

    private StringSet shape;
    private long woodId;

    private Body() {

    }

    public static BodyBuilder builder() {
        return new Body().new BodyBuilder();
    }

    public StringSet getShape() {
        return shape;
    }

    public long getWoodId() {
        return woodId;
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

    public class BodyBuilder extends AbstractBuilder {

        private BodyBuilder() {

        }

        public BodyBuilder setShape(StringSet shape) {
            Body.this.shape = shape;
            return this;
        }

        public BodyBuilder setWoodId(long woodId) {
            Body.this.woodId = woodId;
            return this;
        }

        @Override
        public Body build() {
            return Body.this;
        }

    }
}
