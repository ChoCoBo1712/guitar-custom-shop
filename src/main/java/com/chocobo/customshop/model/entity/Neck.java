package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class Neck extends AbstractEntity {

    public enum TunerSet {
        LOCKING,
        NON_LOCKING
    }

    private String shape;
    private TunerSet tunerSet;
    private long woodId;
    private long fretboardWoodId;

    private Neck() {

    }

    public static NeckBuilder builder() {
        return new Neck().new NeckBuilder();
    }

    public String getShape() {
        return shape;
    }

    public TunerSet getTunerSet() {
        return tunerSet;
    }

    public long getWoodId() {
        return woodId;
    }

    public long getFretboardId() {
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
        return super.equals(neck) && Objects.equals(neck.shape, shape) && Objects.equals(neck.tunerSet, tunerSet)
                && neck.woodId == woodId && neck.fretboardWoodId == fretboardWoodId;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + super.hashCode();

        result = prime * result + (shape != null ? shape.hashCode() : 0);
        result = prime * result + (tunerSet != null ? tunerSet.hashCode() : 0);
        result = prime * result + Long.hashCode(woodId);
        result = prime * result + Long.hashCode(fretboardWoodId);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Neck ");
        builder.append(super.toString()).append(": (");
        builder.append("shape = ").append(shape).append(", ");
        builder.append("tuner = ").append(tunerSet).append(", ");
        builder.append("wood id = ").append(woodId).append(", ");
        builder.append("fretboard id = ").append(fretboardWoodId).append(")");

        return builder.toString();
    }

    public class NeckBuilder extends AbstractBuilder {

        private NeckBuilder() {

        }

        public NeckBuilder setShape(String shape) {
            Neck.this.shape = shape;
            return this;
        }

        public NeckBuilder setTunerSet(TunerSet tunerSet) {
            Neck.this.tunerSet = tunerSet;
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
            Neck.this.shape = neck.shape;
            Neck.this.tunerSet = neck.tunerSet;
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
