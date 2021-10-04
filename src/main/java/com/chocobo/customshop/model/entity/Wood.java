package com.chocobo.customshop.model.entity;

import java.util.Objects;

/**
 * {@code Wood} class represents a wood species entity.
 * @see AbstractEntity
 * @author Evgeniy Sokolchik
 */
public class Wood extends AbstractEntity {

    private String name;

    private Wood() {

    }

    public static WoodBuilder builder() {
        return new Wood().new WoodBuilder();
    }

    public String getName() {
        return name;
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

    /**
     * {@code WoodBuilder} is a subclass of {@link AbstractBuilder} class and used for building the wood species entity.
     * @author Evgeniy Sokolchik
     */
    public class WoodBuilder extends AbstractBuilder {

        private WoodBuilder() {

        }

        public WoodBuilder setName(String name) {
            Wood.this.name = name;
            return this;
        }

        public WoodBuilder of(Wood wood) {
            super.of(wood);
            Wood.this.name = wood.name;
            return this;
        }

        @Override
        public Wood build() {
            return Wood.this;
        }
    }
}
