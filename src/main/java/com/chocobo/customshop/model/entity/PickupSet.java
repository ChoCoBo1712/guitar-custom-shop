package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class PickupSet extends BaseEntity {

    private String name;

    public PickupSet(long entityId, String name) {
        super(entityId);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

        PickupSet pickupSet = (PickupSet) obj;
        return super.equals(pickupSet) && Objects.equals(pickupSet.name, name);
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
}
