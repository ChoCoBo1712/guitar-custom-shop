package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class Guitar extends AbstractEntity {

    public enum NeckJoint {
        BOLT_ON,
        SET_NECK,
        NECK_THROUGH
    }

    private String picturePath;
    private NeckJoint neckJoint;
    private long stringSetId;
    private long bodyId;
    private long neckId;
    private long pickupSetId;
    private String color;
    private String comment;
    private long userId;

    private Guitar() {

    }

    public static GuitarBuilder builder() {
        return new Guitar().new GuitarBuilder();
    }

    public String getPicturePath() {
        return picturePath;
    }

    public NeckJoint getNeckJoint() {
        return neckJoint;
    }

    public long getStringSetId() {
        return stringSetId;
    }

    public long getBodyId() {
        return bodyId;
    }

    public long getNeckId() {
        return neckId;
    }

    public long getPickupSetId() {
        return pickupSetId;
    }

    public String getColor() {
        return color;
    }

    public String getComment() {
        return comment;
    }

    public long getUserId() {
        return userId;
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
                && Objects.equals(guitar.neckJoint, neckJoint) && guitar.stringSetId == stringSetId
                && guitar.bodyId == bodyId && guitar.neckId == neckId
                && guitar.pickupSetId == pickupSetId && Objects.equals(guitar.color, color)
                && Objects.equals(guitar.comment, comment) && guitar.userId == userId;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + super.hashCode();

        result = prime * result + (picturePath != null ? picturePath.hashCode() : 0);
        result = prime * result + (neckJoint != null ? neckJoint.hashCode() : 0);
        result = prime * result + Long.hashCode(stringSetId);
        result = prime * result + Long.hashCode(bodyId);
        result = prime * result + Long.hashCode(neckId);
        result = prime * result + Long.hashCode(pickupSetId);
        result = prime * result + (color != null ? color.hashCode() : 0);
        result = prime * result + (comment != null ? comment.hashCode() : 0);
        result = prime * result + Long.hashCode(userId);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Guitar ");
        builder.append(super.toString()).append(": (");
        builder.append("picture path = ").append(picturePath).append(", ");
        builder.append("neck joint = ").append(neckJoint).append(", ");
        builder.append("string set id = ").append(stringSetId).append(", ");
        builder.append("body id = ").append(bodyId).append(", ");
        builder.append("neck id = ").append(neckId).append(", ");
        builder.append("pickup set id = ").append(pickupSetId).append(", ");
        builder.append("color = ").append(color).append(", ");
        builder.append("comment = ").append(comment).append(", ");
        builder.append("user id = ").append(userId).append(")");

        return builder.toString();
    }

    public class GuitarBuilder extends AbstractBuilder {

        private GuitarBuilder() {

        }

        public GuitarBuilder setPicturePath(String picturePath) {
            Guitar.this.picturePath = picturePath;
            return this;
        }

        public GuitarBuilder setNeckJoint(NeckJoint neckJoint) {
            Guitar.this.neckJoint = neckJoint;
            return this;
        }

        public GuitarBuilder setStringSetId(long stringSetId) {
            Guitar.this.stringSetId = stringSetId;
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

        public GuitarBuilder setPickupSetId(long pickupSetId) {
            Guitar.this.pickupSetId = pickupSetId;
            return this;
        }

        public GuitarBuilder setColor(String color) {
            Guitar.this.color = color;
            return this;
        }

        public GuitarBuilder setComment(String comment) {
            Guitar.this.comment = comment;
            return this;
        }

        public GuitarBuilder setUserId(long userId) {
            Guitar.this.userId = userId;
            return this;
        }

        @Override
        public Guitar build() {
            return Guitar.this;
        }
    }
}
