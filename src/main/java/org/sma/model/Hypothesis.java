package org.sma.model;

import org.sma.frames.WindowFrames;

public class Hypothesis extends Fact{
    private String description;
    private String value;

    public Hypothesis(String description, String value) {
        this.description = description;
        this.value = value;
        WindowFrames.agendaEventListener.addRhs(this);
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return (description + " = " + value);
    }
}
