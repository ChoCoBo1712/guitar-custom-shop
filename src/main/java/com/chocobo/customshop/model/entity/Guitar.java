package com.chocobo.customshop.model.entity;

import java.util.Objects;

/**
 * {@code Guitar} class represents a guitar entity.
 * @see AbstractEntity
 * @author Evgeniy Sokolchik
 */
public class Guitar extends AbstractEntity {

    /**
     * Enum containing possible variants of guitar neck joints.
     */
    public enum NeckJoint {
        BOLT_ON,
        SET_NECK,
        NECK_THROUGH
    }

    /**
     * Enum containing order status of the guitar.
     */
    public enum OrderStatus {
        ORDERED,
        IN_PROGRESS,
        COMPLETED
    }

    private String name;
    private String picturePath;
    private NeckJoint neckJoint;
    /**
     * Foreign key to {@link Body} entity.
     */
    private long bodyId;
    /**
     * Foreign key to {@link Neck} entity.
     */
    private long neckId;
    /**
     * Foreign key to {@link Pickup} entity.
     */
    private long pickupId;
    private String color;
    /**
     * Foreign key to {@link User} entity.
     */
    private long userId;
    private OrderStatus orderStatus;

    private Guitar() {

    }

    public static GuitarBuilder builder() {
        return new Guitar().new GuitarBuilder();
    }

    public String getName() {
        return name;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public NeckJoint getNeckJoint() {
        return neckJoint;
    }

    public long getBodyId() {
        return bodyId;
    }

    public long getNeckId() {
        return neckId;
    }

    public long getPickupId() {
        return pickupId;
    }

    public String getColor() {
        return color;
    }

    public long getUserId() {
        return userId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Guitar guitar = (Guitar) obj;
        return super.equals(guitar) && Objects.equals(guitar.picturePath, picturePath)
                && Objects.equals(guitar.neckJoint, neckJoint) && Objects.equals(guitar.name, name)
                && guitar.bodyId == bodyId && guitar.neckId == neckId
                && guitar.pickupId == pickupId && Objects.equals(guitar.color, color)
                && guitar.userId == userId && Objects.equals(guitar.orderStatus, orderStatus);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + super.hashCode();

        result = prime * result + (name != null ? name.hashCode() : 0);
        result = prime * result + (picturePath != null ? picturePath.hashCode() : 0);
        result = prime * result + (neckJoint != null ? neckJoint.hashCode() : 0);
        result = prime * result + Long.hashCode(bodyId);
        result = prime * result + Long.hashCode(neckId);
        result = prime * result + Long.hashCode(pickupId);
        result = prime * result + (color != null ? color.hashCode() : 0);
        result = prime * result + Long.hashCode(userId);
        result = prime * result + (orderStatus != null ? orderStatus.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Guitar ");
        builder.append(super.toString()).append(": (");
        builder.append("name = ").append(picturePath).append(", ");
        builder.append("picture path = ").append(picturePath).append(", ");
        builder.append("neck joint = ").append(neckJoint).append(", ");
        builder.append("body id = ").append(bodyId).append(", ");
        builder.append("neck id = ").append(neckId).append(", ");
        builder.append("pickup id = ").append(pickupId).append(", ");
        builder.append("color = ").append(color).append(", ");
        builder.append("user id = ").append(userId).append(", ");
        builder.append("order status = ").append(orderStatus).append(")");

        return builder.toString();
    }

    /**
     * {@code GuitarBuilder} is a subclass of {@link AbstractBuilder} class and used for building the guitar entity.
     * @author Evgeniy Sokolchik
     */
    public class GuitarBuilder extends AbstractBuilder {

        private GuitarBuilder() {

        }

        public GuitarBuilder setName(String name) {
            Guitar.this.name = name;
            return this;
        }

        public GuitarBuilder setPicturePath(String picturePath) {
            Guitar.this.picturePath = picturePath;
            return this;
        }

        public GuitarBuilder setNeckJoint(NeckJoint neckJoint) {
            Guitar.this.neckJoint = neckJoint;
            return this;
        }

        public GuitarBuilder setBodyId(long bodyId) {
            Guitar.this.bodyId = bodyId;
            return this;
        }

        public GuitarBuilder setNeckId(long neckId) {
            Guitar.this.neckId = neckId;
            return this;
        }

        public GuitarBuilder setPickupId(long pickupId) {
            Guitar.this.pickupId = pickupId;
            return this;
        }

        public GuitarBuilder setColor(String color) {
            Guitar.this.color = color;
            return this;
        }

        public GuitarBuilder setUserId(long userId) {
            Guitar.this.userId = userId;
            return this;
        }

        public GuitarBuilder setOrderStatus(OrderStatus orderStatus) {
            Guitar.this.orderStatus = orderStatus;
            return this;
        }


        public GuitarBuilder of(Guitar guitar) {
            super.of(guitar);
            Guitar.this.name = guitar.name;
            Guitar.this.picturePath = guitar.picturePath;
            Guitar.this.neckJoint = guitar.neckJoint;
            Guitar.this.bodyId = guitar.bodyId;
            Guitar.this.neckId = guitar.neckId;
            Guitar.this.pickupId = guitar.pickupId;
            Guitar.this.color = guitar.color;
            Guitar.this.userId = guitar.userId;
            Guitar.this.orderStatus = guitar.orderStatus;
            return this;
        }

        @Override
        public Guitar build() {
            return Guitar.this;
        }
    }
}
