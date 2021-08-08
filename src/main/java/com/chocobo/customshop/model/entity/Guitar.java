package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class Guitar extends BaseEntity {

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

    public Guitar(
            long entityId, String picturePath, NeckJoint neckJoint, long stringSetId, long bodyId, long neckId,
            long pickupSetId, String color, String comment, long userId
    ) {
        super(entityId);
        this.picturePath = picturePath;
        this.neckJoint = neckJoint;
        this.stringSetId = stringSetId;
        this.bodyId = bodyId;
        this.neckId = neckId;
        this.pickupSetId = pickupSetId;
        this.color = color;
        this.comment = comment;
        this.userId = userId;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public NeckJoint getNeckJoint() {
        return neckJoint;
    }

    public void setNeckJoint(NeckJoint neckJoint) {
        this.neckJoint = neckJoint;
    }

    public long getStringSetId() {
        return stringSetId;
    }

    public void setStringSetId(long stringSetId) {
        this.stringSetId = stringSetId;
    }

    public long getBodyId() {
        return bodyId;
    }

    public void setBodyId(long bodyId) {
        this.bodyId = bodyId;
    }

    public long getNeckId() {
        return neckId;
    }

    public void setNeckId(long neckId) {
        this.neckId = neckId;
    }

    public long getPickupSetId() {
        return pickupSetId;
    }

    public void setPickupSetId(long pickupSetId) {
        this.pickupSetId = pickupSetId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
}
