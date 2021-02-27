package org.sma.model;

import org.sma.frames.WindowFrames;
import org.sma.model.entities.AccessoriesSealants;
import org.sma.model.entities.CasementOrFixedWindowsSeries;
import org.sma.model.entities.FrameSystem;
import org.sma.model.reader.ServiceReader;

import java.util.ArrayList;
import java.util.List;

//Casement or fixed Windows - janelas batente
public class CasementOrFixedWindowsSystems {

    private static List<CasementOrFixedWindowsSeries> availableSeries = new ArrayList<>();
    private static List<AccessoriesSealants> accessoriesSealantFixedList = new ArrayList<>();
    private static List<AccessoriesSealants> accessoriesSealantCasementList = new ArrayList<>();

    public CasementOrFixedWindowsSystems() {
        ServiceReader reader = new ServiceReader();
        availableSeries = reader.generatingCasementAndFixedSeries();
    }


    public static boolean isSystemValid(int indexSerie) {
        int index = 0;
        WindowFrames.selectedFrameSystem.setFrameCategory(FrameCategory.CasementOrFixedFrame);
        for (CasementOrFixedWindowsSeries serie : availableSeries) {
            index++;
            if (index > indexSerie && checkIsValid(serie)) {
                WindowFrames.selectedFrameSystem.setName(serie.name);
                ;
                WindowFrames.selectedFrameSystem.setQualityRange(serie.qualityRange);

                readAccessoriesAndSealants();
                return true;
            }
        }
        return false;
    }


    private static boolean checkIsValid(CasementOrFixedWindowsSeries serie) {

        boolean isValid = isValidThickness(WindowFrames.selectedFrameSystem.getWindowThickness(), serie.maxThickness) &&
                isValidSize(WindowFrames.selectedFrameSystem.getWindowHeight(), serie.maxSize) &&
                isValidSize(WindowFrames.selectedFrameSystem.getWindowWidth(), serie.maxSize) &&
                isValidGlassWeight(computeGlassWeight(), serie.maxWeight);

        Evidence e;
        if (isValid) {
            e = new Evidence("- Sistema " + serie.name + " gama " + serie.qualityRange + " - ", "válido");
        } else {
            e = new Evidence("- Sistema " + serie.name + " gama " + serie.qualityRange + " - ", "inválido");
        }
        WindowFrames.agendaEventListener.addLhs(e);

        return isValid;
    }


    private static double computeGlassWeight() {
        return WindowFrames.selectedFrameSystem.getWindowHeight() * WindowFrames.selectedFrameSystem.getWindowWidth() *
                (WindowFrames.selectedFrameSystem.getGlassThickness() * 2.5);
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

    private static boolean isValidSize(double height, double maxHeight) {
        boolean isValid = height <= maxHeight;
        Evidence e;
        if (isValid) {
            e = new Evidence("- A dimensão é ", "válida");
        } else {
            e = new Evidence("- A dimensão é ", "inválida. A dimensão máxima devia ser de " + maxHeight + " e é de " + height);
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

    public static String defineAccessoriesAndSealant(String type) {
        AccessoriesSealants finalAccessories;
        FrameSystem frameSystem = WindowFrames.selectedFrameSystem;
        int selectedWindowThickness = frameSystem.getWindowThickness();
        if (accessoriesSealantCasementList.size() == 0 || type != null && type.equals("Janela fixa")) {
            finalAccessories = accessoriesSealantFixedList.stream().filter(item ->
                    item.glassThickness > selectedWindowThickness
            ).findFirst().orElse(null);
        } else {
            finalAccessories = accessoriesSealantCasementList.stream().filter(item ->
                    item.glassThickness > selectedWindowThickness
            ).findFirst().orElse(null);
        }
        return "--> " + type + " com a espessura total de " + selectedWindowThickness + "mm deve ter " + frameSystem.getPerimeter() + "metros do vendante Exterior " +
                finalAccessories.exteriorSealant + ",  " + frameSystem.getPerimeter() + " metros do vendante interior " + finalAccessories.interiorSealant + "  e o bite "
                + finalAccessories.glazingBead;
    }

    private static void readAccessoriesAndSealants() {
        ServiceReader reader = new ServiceReader();
        List<List<String>> list = reader.readCSV("src/main/resources/AccessoriesAndSealant-" + WindowFrames.selectedFrameSystem.getName() + ".csv");
        list.remove(0);
        list.forEach(row -> {
            AccessoriesSealants accessoriesSealants = new AccessoriesSealants();
            accessoriesSealants.exteriorSealant = row.get(0);
            accessoriesSealants.glassThickness = Integer.parseInt(row.get(1));
            accessoriesSealants.interiorSealant = row.get(2);
            accessoriesSealants.glazingBead = row.get(3);
            accessoriesSealantFixedList.add(accessoriesSealants);
            if (row.size() == 8) {
                AccessoriesSealants accessoriesSealantsCasement = new AccessoriesSealants();
                accessoriesSealantsCasement.exteriorSealant = row.get(0);
                accessoriesSealantsCasement.glassThickness = Integer.parseInt(row.get(1));
                accessoriesSealantsCasement.interiorSealant = row.get(2);
                accessoriesSealantsCasement.glazingBead = row.get(3);
                accessoriesSealantCasementList.add(accessoriesSealants);
            }
        });
    }

}
