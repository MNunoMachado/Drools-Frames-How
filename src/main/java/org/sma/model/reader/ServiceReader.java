package org.sma.model.reader;

import org.sma.model.entities.CasementOrFixedWindowsSeries;
import org.sma.model.entities.SlidingWindowsSystemsSeries;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceReader {

    public ServiceReader() {
    }


    public List<List<String>> readCSV(String path) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return records;
    }


    public List<CasementOrFixedWindowsSeries> generatingCasementAndFixedSeries() {
        List<List<String>> list = readCSV("src/main/resources/CasementFrames.csv");
        List<CasementOrFixedWindowsSeries> framesSeries = new ArrayList<>();
        list.forEach(row -> {
            CasementOrFixedWindowsSeries serie = new CasementOrFixedWindowsSeries();
            serie.qualityRange = row.get(0);
            serie.name = row.get(1);
            serie.maxThickness = Integer.parseInt(row.get(2));
            serie.maxWeight = Double.parseDouble(row.get(3));
            serie.maxSize = Double.parseDouble(row.get(4));
            framesSeries.add(serie);
        });


        return framesSeries;
    }

    public List<SlidingWindowsSystemsSeries> generatingSlidingSeries() {
        List<List<String>> list = readCSV("src/main/resources/SlidingFrames.csv");
        List<SlidingWindowsSystemsSeries> framesSeries = new ArrayList<>();
        list.forEach(row -> {
            SlidingWindowsSystemsSeries serie = new SlidingWindowsSystemsSeries();
            serie.qualityRange = row.get(0);
            serie.name = row.get(1);
            serie.maxThickness = Integer.parseInt(row.get(2));
            serie.maxWeight = Double.parseDouble(row.get(3));
            serie.maxHeight = Double.parseDouble(row.get(4));
            serie.maxArea = Double.parseDouble(row.get(5));
            framesSeries.add(serie);
        });


        return framesSeries;
    }

}
