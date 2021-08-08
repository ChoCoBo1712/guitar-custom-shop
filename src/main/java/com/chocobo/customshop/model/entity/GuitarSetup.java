package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class GuitarSetup extends BaseEntity {

    private boolean stringsChanging;
    private long stringSetId;
    private boolean bodyCleaning;
    private boolean fretboardConditioning;
    private boolean neckPolishing;
    private boolean fretsPolishing;
    private boolean nutCutting;
    private String comment;
    private long userId;

    public GuitarSetup(
            long entityId, boolean stringsChanging, long stringSetId, boolean bodyCleaning,
            boolean fretboardConditioning, boolean neckPolishing, boolean fretsPolishing,
            boolean nutCutting, String comment, long userId
    ) {
        super(entityId);
        this.stringsChanging = stringsChanging;
        this.stringSetId = stringSetId;
        this.bodyCleaning = bodyCleaning;
        this.fretboardConditioning = fretboardConditioning;
        this.neckPolishing = neckPolishing;
        this.fretsPolishing = fretsPolishing;
        this.nutCutting = nutCutting;
        this.comment = comment;
        this.userId = userId;
    }

    public boolean isStringsChanging() {
        return stringsChanging;
    }

    public void setStringsChanging(boolean stringsChanging) {
        this.stringsChanging = stringsChanging;
    }

    public long getStringSetId() {
        return stringSetId;
    }

    public void setStringSetId(long guitarStringsId) {
        this.stringSetId = guitarStringsId;
    }

    public boolean isBodyCleaning() {
        return bodyCleaning;
    }

    public void setBodyCleaning(boolean bodyCleaning) {
        this.bodyCleaning = bodyCleaning;
    }

    public boolean isFretboardConditioning() {
        return fretboardConditioning;
    }

    public void setFretboardConditioning(boolean fretboardConditioning) {
        this.fretboardConditioning = fretboardConditioning;
    }

    public boolean isNeckPolishing() {
        return neckPolishing;
    }

    public void setNeckPolishing(boolean neckPolishing) {
        this.neckPolishing = neckPolishing;
    }

    public boolean isFretsPolishing() {
        return fretsPolishing;
    }

    public void setFretsPolishing(boolean fretsPolishing) {
        this.fretsPolishing = fretsPolishing;
    }

    public boolean isNutCutting() {
        return nutCutting;
    }

    public void setNutCutting(boolean nutCutting) {
        this.nutCutting = nutCutting;
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

        GuitarSetup setup = (GuitarSetup) obj;
        return super.equals(setup) && setup.stringsChanging == stringsChanging
                && setup.stringSetId == stringSetId && setup.bodyCleaning == bodyCleaning
                && setup.fretboardConditioning == fretboardConditioning && setup.neckPolishing == neckPolishing
                && setup.fretsPolishing == fretsPolishing && setup.nutCutting == nutCutting
                && Objects.equals(setup.comment, comment) && setup.userId == userId;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + super.hashCode();

        result = prime * result + Boolean.hashCode(stringsChanging);
        result = prime * result + Long.hashCode(stringSetId);
        result = prime * result + Boolean.hashCode(bodyCleaning);
        result = prime * result + Boolean.hashCode(fretboardConditioning);
        result = prime * result + Boolean.hashCode(neckPolishing);
        result = prime * result + Boolean.hashCode(fretsPolishing);
        result = prime * result + Boolean.hashCode(nutCutting);
        result = prime * result + (comment != null ? comment.hashCode() : 0);
        result = prime * result + Long.hashCode(userId);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("GuitarSetup ");
        builder.append(super.toString()).append(": (");
        builder.append("is strings changing = ").append(stringsChanging).append(", ");
        builder.append("string set id = ").append(stringSetId).append(", ");
        builder.append("is body cleaning = ").append(bodyCleaning).append(", ");
        builder.append("is fretboard conditioning = ").append(fretboardConditioning).append(", ");
        builder.append("is neck polishing = ").append(neckPolishing).append(", ");
        builder.append("is frets polishing = ").append(fretsPolishing).append(", ");
        builder.append("is nut cutting = ").append(nutCutting).append(", ");
        builder.append("comment = ").append(comment).append(", ");
        builder.append("user id = ").append(userId).append(")");

        return builder.toString();
    }
}
