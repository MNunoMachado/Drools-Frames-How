package org.sma.model;

import org.sma.frames.WindowFrames;
import org.sma.model.entities.FrameSystem;

public class Conclusion extends Fact {
    Observation observation;
    public static final String NOT_VALID = "Consulta Externa";

    public static final String RESGUARDO_FIXO_BATENTE = "Série RB para resguardo fixo ou de batente.";
    public static final String RESGUARDO_CORRER = "Série CM para resguardo de correr.";

    public static final String JANELA_BATENTE = "Janela batente";
    public static final String JANELA_FIXA = "Janela fixa";
    public static final String PORTA_BATENTE = "Porta de batente";

    public static String CORRER = "Vão de correr";

    private String description;

    public Conclusion(String description) {
        this.description = description;
        addAccessoriesDetails();
        WindowFrames.agendaEventListener.addRhs(this);
    }

    public Conclusion(String description, FrameSystem frameSystem) {
        addAccessoriesDetails();
        this.description = description + " da série " + frameSystem.getName() + " de " + frameSystem.getQualityRange();
        WindowFrames.agendaEventListener.addRhs(this);
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return ("Conclusion: " + description);
    }

    private void addAccessoriesDetails() {
        if (!FrameCategory.CasementOrFixedFrame.equals(WindowFrames.selectedFrameSystem.getFrameCategory()) && !NOT_VALID.equals(description)) {
            observation = new Observation();
        }
    }
}
