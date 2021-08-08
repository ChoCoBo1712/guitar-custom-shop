package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class Fretboard extends BaseEntity {

    private String radius;
    private String frets;
    private long woodId;

    public Fretboard(long entityId, String radius, String frets, long woodId) {
        super(entityId);
        this.radius = radius;
        this.frets = frets;
        this.woodId = woodId;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getFrets() {
        return frets;
    }

    public void setFrets(String frets) {
        this.frets = frets;
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

        Fretboard fretboard = (Fretboard) obj;
        return super.equals(fretboard) && Objects.equals(fretboard.radius, radius)
                && Objects.equals(fretboard.frets, frets) && fretboard.woodId == woodId;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + super.hashCode();

        result = prime * result + (radius != null ? radius.hashCode() : 0);
        result = prime * result + (frets != null ? radius.hashCode() : 0);
        result = prime * result + Long.hashCode(woodId);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Fretboard ");
        builder.append(super.toString()).append(": (");
        builder.append("radius = ").append(radius).append(", ");
        builder.append("frets = ").append(radius).append(", ");
        builder.append("wood id = ").append(woodId).append(")");

        return builder.toString();
    }
}
