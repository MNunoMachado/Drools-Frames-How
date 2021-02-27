package org.sma.model;

import org.sma.frames.WindowFrames;
import org.sma.model.entities.FrameSystem;

//This class was created to add details (define accessories) to the Casement and Fixed frames.
public class Observation extends Fact {

    private String description;

    public Observation() {
        String observation;
        FrameSystem frameSystem = WindowFrames.selectedFrameSystem;
        if (FrameCategory.SlidingFrame.equals(frameSystem.getFrameCategory())) {
            observation = "--> Caixilharia deve incluir vedante Exterior do modelo 2003 e vedante interior do modelo 2042. " +
                    frameSystem.getPerimeter() + "metros de cada modelo.";
        } else {
            observation = "--> Resguardo deve incluir vedantes modelo 2001,  " +
                    frameSystem.getPerimeter() + "metros de cada face do resguardo.";
        }
        this.description = observation;
        WindowFrames.agendaEventListener.addLhs(this);
    }

    public Observation(String frameType) {
        String observation = CasementOrFixedWindowsSystems.defineAccessoriesAndSealant(frameType);
        this.description = observation;
        WindowFrames.agendaEventListener.addLhs(this);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
