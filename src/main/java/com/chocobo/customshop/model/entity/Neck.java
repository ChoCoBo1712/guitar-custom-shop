package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class Neck extends BaseEntity {

    public enum TunerSet {
        LOCKING,
        NON_LOCKING
    }

    private StringSet shape;
    private TunerSet tunerSet;
    private long woodId;
    private long fretboardId;

    public Neck(long entityId, StringSet shape, TunerSet tunerSet, long woodId, long fretboardId) {
        super(entityId);
        this.shape = shape;
        this.tunerSet = tunerSet;
        this.woodId = woodId;
        this.fretboardId = fretboardId;
    }

    public StringSet getShape() {
        return shape;
    }

    public void setShape(StringSet shape) {
        this.shape = shape;
    }

    public TunerSet getTuner() {
        return tunerSet;
    }

    public void setTuner(TunerSet tunerSet) {
        this.tunerSet = tunerSet;
    }

    public long getWoodId() {
        return woodId;
    }

    public void setWoodId(long woodId) {
        this.woodId = woodId;
    }

    public long getFretboardId() {
        return fretboardId;
    }

    public void setFretboardId(long fretboardId) {
        this.fretboardId = fretboardId;
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
}
