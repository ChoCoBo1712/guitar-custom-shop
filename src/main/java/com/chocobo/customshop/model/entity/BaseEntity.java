package com.chocobo.customshop.model.entity;

public class BaseEntity {

    private long entityId;

    protected BaseEntity(long entityId) {
        this.entityId = entityId;
    }

    public long getEntityId() {
        return entityId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        BaseEntity that = (BaseEntity) object;

        return entityId == that.entityId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(entityId);
    }

    @Override
    public String toString() {
        return "id = " + entityId;
    }
}
