package org.sma.model;

import org.sma.frames.WindowFrames;
import org.sma.model.entities.SlidingWindowsSystemsSeries;
import org.sma.model.reader.ServiceReader;

import java.util.ArrayList;
import java.util.List;

public class SlidingWindowsSystems {

    private static List<SlidingWindowsSystemsSeries> availableSeries = new ArrayList<>();

    public SlidingWindowsSystems() {
        ServiceReader reader = new ServiceReader();
        availableSeries = reader.generatingSlidingSeries();
    }


    public static boolean isSlidingSystemValid(int indexSerie) {
        int index = 0;
        WindowFrames.selectedFrameSystem.setFrameCategory(FrameCategory.SlidingFrame);
        for (SlidingWindowsSystemsSeries serie : availableSeries) {
            index++;
            if (index > indexSerie && checkIsValid(serie)) {
                WindowFrames.selectedFrameSystem.setName(serie.name);
                WindowFrames.selectedFrameSystem.setQualityRange(serie.qualityRange);
                return true;
            }
        }

        return false;
    }

    private static boolean checkIsValid(SlidingWindowsSystemsSeries serie) {
         boolean isValid = isValidThickness(WindowFrames.selectedFrameSystem.getWindowThickness(), serie.maxThickness) &&
                isValidHeight(WindowFrames.selectedFrameSystem.getWindowHeight(), serie.maxHeight) &&
                isValidGlassWeight(computeGlassWeight(), serie.maxWeight) &&
                isValidArea(computeArea(), serie.maxArea);

        Evidence e;
        if (isValid) {
            e = new Evidence("Sistema " + serie.name + " gama " + serie.qualityRange + " - ", "válido");

        } else {
            e = new Evidence("Sistema " + serie.name + " gama " + serie.qualityRange + " - ", "inválido");
        }
        WindowFrames.agendaEventListener.addLhs(e);

        return isValid;
    }


    private static double computeGlassWeight() {
         return WindowFrames.selectedFrameSystem.getWindowHeight() * WindowFrames.selectedFrameSystem.getWindowWidth() *
                (WindowFrames.selectedFrameSystem.getGlassThickness() * 2.5);
    }

    private static double computeArea() {
         return WindowFrames.selectedFrameSystem.getWindowHeight() * WindowFrames.selectedFrameSystem.getWindowWidth();
    }

    private static boolean isValidArea(double area, double maxArea) {
        boolean isValid = area <= maxArea;
        if (isValid) {
            Evidence e = new Evidence("- A área é ", "válida");
            WindowFrames.agendaEventListener.addLhs(e);
        } else {
            Evidence e = new Evidence("- A área é ", "inválida. A área máxima devia ser de " + maxArea + " e é de " + area);
            WindowFrames.agendaEventListener.addLhs(e);
        }
        return isValid;
    }

    private static boolean isValidGlassWeight(double weight, double maxWeight) {
        boolean isValid = weight <= maxWeight;
        Evidence e;
        if (isValid) {
            e = new Evidence("- O peso é ", "válido");
        } else {
            e = new Evidence("- O peso é ", "inválido. O peso máxima devia ser de " + maxWeight + " e é de " + weight);
        }
        WindowFrames.agendaEventListener.addLhs(e);

        return isValid;
    }

    private static boolean isValidHeight(double height, double maxHeight) {
        boolean isValid = height <= maxHeight;
        Evidence e;
        if (isValid) {
            e = new Evidence("- A altura é ", "válida");
        } else {
            e = new Evidence("- A altura é ", "inválida. A altura máxima devia ser de " + maxHeight + " e é de " + height);
        }
        WindowFrames.agendaEventListener.addLhs(e);

        return isValid;
    }

    private static boolean isValidThickness(double thickness, double maxThickness) {
        boolean isValid = thickness <= maxThickness;
        Evidence e;
        if (isValid) {
            e = new Evidence("- A espessura é ", "válida");
        } else {
            e = new Evidence("- A espessura é ", "inválida. A espessura máxima devia ser de " + maxThickness + " e é de " + thickness);
        }
        WindowFrames.agendaEventListener.addLhs(e);

        return isValid;
    }

}
