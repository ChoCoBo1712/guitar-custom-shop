package com.chocobo.customshop.model.entity;

/**
 * {@code AbstractEntity} is a top abstract class in the hierarchy of domain entities.
 * @author Evgeniy Sokolchik
 */
public abstract class AbstractEntity {

    private long entityId;

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
        AbstractEntity that = (AbstractEntity) object;

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

    /**
     * {@code AbstractBuilder} is a top class in the hierarchy of inner builder classes.
     * @author Evgeniy Sokolchik
     */
    public abstract class AbstractBuilder {

        public AbstractBuilder setEntityId(long entityId) {
            AbstractEntity.this.entityId = entityId;
            return this;
        }

        public AbstractBuilder of(AbstractEntity entity) {
            AbstractEntity.this.entityId = entity.entityId;
            return this;
        }

        public abstract AbstractEntity build();
    }
}
