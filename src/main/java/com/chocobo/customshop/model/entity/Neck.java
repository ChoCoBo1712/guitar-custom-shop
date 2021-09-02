package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class Neck extends AbstractEntity {

    public enum TunerSet {
        LOCKING,
        NON_LOCKING
    }

    private StringSet shape;
    private TunerSet tunerSet;
    private long woodId;
    private long fretboardId;

    private Neck() {

    }

    public static NeckBuilder builder() {
        return new Neck().new NeckBuilder();
    }

    public StringSet getShape() {
        return shape;
    }

    public TunerSet getTunerSet() {
        return tunerSet;
    }

    public long getWoodId() {
        return woodId;
    }

    public long getFretboardId() {
        return fretboardId;
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
                && neck.woodId == woodId && neck.fretboardId == fretboardId;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + super.hashCode();

        result = prime * result + (shape != null ? shape.hashCode() : 0);
        result = prime * result + (tunerSet != null ? tunerSet.hashCode() : 0);
        result = prime * result + Long.hashCode(woodId);
        result = prime * result + Long.hashCode(fretboardId);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Neck ");
        builder.append(super.toString()).append(": (");
        builder.append("shape = ").append(shape).append(", ");
        builder.append("tuner = ").append(tunerSet).append(", ");
        builder.append("wood id = ").append(woodId).append(", ");
        builder.append("fretboard id = ").append(fretboardId).append(")");

        return builder.toString();
    }

    public class NeckBuilder extends AbstractBuilder {

        private NeckBuilder() {

        }

        public NeckBuilder setShape(StringSet shape) {
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

        public NeckBuilder setFretboardId(long fretboardId) {
            Neck.this.fretboardId = fretboardId;
            return this;
        }

        @Override
        public Neck build() {
            return Neck.this;
        }
    }
}
