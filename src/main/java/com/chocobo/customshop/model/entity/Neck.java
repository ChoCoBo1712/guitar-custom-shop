package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class Neck extends AbstractEntity {

    private String name;
    private long woodId;
    private long fretboardWoodId;

    private Neck() {

    }

    public static NeckBuilder builder() {
        return new Neck().new NeckBuilder();
    }

    public String getName() {
        return name;
    }

    public long getWoodId() {
        return woodId;
    }

    public long getFretboardWoodId() {
        return fretboardWoodId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Neck neck = (Neck) obj;
        return super.equals(neck)
                && Objects.equals(neck.name, this.name)
                && neck.woodId == woodId && neck.fretboardWoodId == fretboardWoodId;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + super.hashCode();

        result = prime * result + (name != null ? name.hashCode() : 0);
        result = prime * result + Long.hashCode(woodId);
        result = prime * result + Long.hashCode(fretboardWoodId);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Neck ");
        builder.append(super.toString()).append(": (");
        builder.append("name = ").append(name).append(", ");
        builder.append("wood id = ").append(woodId).append(", ");
        builder.append("fretboard id = ").append(fretboardWoodId).append(")");

        return builder.toString();
    }

    public class NeckBuilder extends AbstractBuilder {

        private NeckBuilder() {

        }

        public NeckBuilder setName(String name) {
            Neck.this.name = name;
            return this;
        }

        public NeckBuilder setWoodId(long woodId) {
            Neck.this.woodId = woodId;
            return this;
        }

        public NeckBuilder setFretboardId(long fretboardWoodId) {
            Neck.this.fretboardWoodId = fretboardWoodId;
            return this;
        }

        public NeckBuilder of(Neck neck) {
            super.of(neck);
            Neck.this.name = neck.name;
            Neck.this.woodId = neck.woodId;
            Neck.this.fretboardWoodId = neck.fretboardWoodId;
            return this;
        }

        @Override
        public Neck build() {
            return Neck.this;
        }
    }
}
