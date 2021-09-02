package com.chocobo.customshop.model.entity;

import java.util.Objects;

public class GuitarSetup extends AbstractEntity {

    private boolean stringsChanging;
    private long stringSetId;
    private boolean bodyCleaning;
    private boolean fretboardConditioning;
    private boolean neckPolishing;
    private boolean fretsPolishing;
    private boolean nutCutting;
    private String comment;
    private long userId;

    private GuitarSetup() {

    }

    public static GuitarSetupBuilder builder() {
        return new GuitarSetup().new GuitarSetupBuilder();
    }

    public boolean isStringsChanging() {
        return stringsChanging;
    }

    public long getStringSetId() {
        return stringSetId;
    }

    public boolean isBodyCleaning() {
        return bodyCleaning;
    }

    public boolean isFretboardConditioning() {
        return fretboardConditioning;
    }

    public boolean isNeckPolishing() {
        return neckPolishing;
    }

    public boolean isFretsPolishing() {
        return fretsPolishing;
    }

    public boolean isNutCutting() {
        return nutCutting;
    }

    public String getComment() {
        return comment;
    }

    public long getUserId() {
        return userId;
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

    public class GuitarSetupBuilder extends AbstractBuilder {

        private GuitarSetupBuilder() {

        }

        public GuitarSetupBuilder setStringsChanging(boolean stringsChanging) {
            GuitarSetup.this.stringsChanging = stringsChanging;
            return this;
        }

        public GuitarSetupBuilder setStringSetId(long stringSetId) {
            GuitarSetup.this.stringSetId = stringSetId;
            return this;
        }

        public GuitarSetupBuilder setBodyCleaning(boolean bodyCleaning) {
            GuitarSetup.this.bodyCleaning = bodyCleaning;
            return this;
        }

        public GuitarSetupBuilder setFretboardConditioning(boolean fretboardConditioning) {
            GuitarSetup.this.fretboardConditioning = fretboardConditioning;
            return this;
        }

        public GuitarSetupBuilder setNeckPolishing(boolean neckPolishing) {
            GuitarSetup.this.neckPolishing = neckPolishing;
            return this;
        }

        public GuitarSetupBuilder setFretsPolishing(boolean fretsPolishing) {
            GuitarSetup.this.fretsPolishing = fretsPolishing;
            return this;
        }

        public GuitarSetupBuilder setNutCutting(boolean nutCutting) {
            GuitarSetup.this.nutCutting = nutCutting;
            return this;
        }

        public GuitarSetupBuilder setComment(String comment) {
            GuitarSetup.this.comment = comment;
            return this;
        }

        public GuitarSetupBuilder setUserId(long userId) {
            GuitarSetup.this.userId = userId;
            return this;
        }

        @Override
        public GuitarSetup build() {
            return GuitarSetup.this;
        }
    }
}
