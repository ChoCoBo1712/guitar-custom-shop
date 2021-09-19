package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class Pickup extends AbstractEntity {

    private String name;

    private Pickup() {

    }

    public static PickupBuilder builder() {
        return new Pickup().new PickupBuilder();
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

        Pickup pickup = (Pickup) obj;
        return super.equals(pickup) && Objects.equals(pickup.name, name);
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
        StringBuilder builder = new StringBuilder("Pickup ");
        builder.append(super.toString()).append(": (");
        builder.append("name = ").append(name).append(")");

        return builder.toString();
    }

    public class PickupBuilder extends AbstractBuilder {

        private PickupBuilder() {

        }

        public PickupBuilder setName(String name) {
            Pickup.this.name = name;
            return this;
        }

        public PickupBuilder of(Pickup pickup) {
            super.of(pickup);
            Pickup.this.name = pickup.name;
            return this;
        }

        @Override
        public Pickup build() {
            return Pickup.this;
        }
    }
}
