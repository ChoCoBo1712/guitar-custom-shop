package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class Fretboard extends AbstractEntity {

    private String radius;
    private String frets;
    private long woodId;

    private Fretboard() {

    }

    public static FretboardBuilder builder() {
        return new Fretboard().new FretboardBuilder();
    }

    public String getRadius() {
        return radius;
    }

    public String getFrets() {
        return frets;
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

    public class FretboardBuilder extends AbstractBuilder {

        private FretboardBuilder() {

        }

        public FretboardBuilder setRadius(String radius) {
            Fretboard.this.radius = radius;
            return this;
        }

        public FretboardBuilder setFrets(String frets) {
            Fretboard.this.frets = frets;
            return this;
        }

        public FretboardBuilder setWoodId(long woodId) {
            Fretboard.this.woodId = woodId;
            return this;
        }

        @Override
        public Fretboard build() {
            return Fretboard.this;
        }
    }
}
