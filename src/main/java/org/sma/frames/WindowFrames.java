package org.sma.frames;

import org.sma.model.*;
import org.sma.model.SlidingWindowsSystems;
import org.sma.model.entities.FrameSystem;
import org.sma.view.Gui;
import org.sma.view.UI;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.ConsequenceException;
import org.kie.api.runtime.rule.LiveQuery;
import org.kie.api.runtime.rule.Row;
import org.kie.api.runtime.rule.ViewChangedEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

public class WindowFrames {
    public   KieSession KS;
     public static TrackingAgendaEventListener agendaEventListener;
    public  Map<Integer, Justification> justifications;
    public static FrameSystem selectedFrameSystem = new FrameSystem();
    public Gui gui;
    public LiveQuery query;

   public WindowFrames() {
    }
    public Gui getGui() {
        return gui;
    }



    public KieSession getKieSession() {
        return KS;
    }

    public static TrackingAgendaEventListener getAgendaEventListener() {
        return agendaEventListener;
    }

    public static void main(String[] args) {
        CasementOrFixedWindowsSystems casementOrFixedsystem = new CasementOrFixedWindowsSystems();
        SlidingWindowsSystems slidingSystem = new SlidingWindowsSystems();
        UI.uiInit();

    }

    public static void defineWindowMeasures(String width, String height, String glassThickness, String windowThickness) {
        double widthMeasures = Double.parseDouble(width);
        double heightMeasures = Double.parseDouble(height);
        int glassThicknessMeasures = Integer.parseInt(glassThickness);
        int windowThicknessMeasures = Integer.parseInt(windowThickness);

        if (widthMeasures > 0 && heightMeasures > 0 && glassThicknessMeasures > 0 && windowThicknessMeasures >0) {
            selectedFrameSystem.setWindowWidth(widthMeasures/100);
            selectedFrameSystem.setWindowHeight(heightMeasures/100);
            selectedFrameSystem.setGlassThickness(glassThicknessMeasures);
            selectedFrameSystem.setWindowThickness(windowThicknessMeasures);
         }
    }

    public void setupEngine() {
        try {
            if (KS != null) {
                System.out.println("Depois da n vez: " + KS.getObjects().size());
                for (Object object : KS.getObjects()) {
                    System.out.println(object.toString());
                    KS.delete(KS.getFactHandle(object));
                }
            }
             justifications = new TreeMap<Integer, Justification>();
             // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KS = kContainer.newKieSession("ksession-rules");

            WindowFrames.agendaEventListener = new TrackingAgendaEventListener( justifications);
            KS.addEventListener(agendaEventListener);


            // Query listener
            ViewChangedEventListener listener = new ViewChangedEventListener() {
                @Override
                public void rowDeleted(Row row) {
                }

                @Override
                public void rowInserted(Row row) {
                    Conclusion conclusion = (Conclusion) row.get("$conclusion");
                    gui.setConclusion(conclusion.toString());
                    How how = new How( justifications);
                    gui.setExplanation(how.getHowExplanation(conclusion.getId()));
                    KS.halt();
                    UI.getFinishWindow();

                    PrintStream out = null;
                    try {
                        out = new PrintStream(
                                new FileOutputStream("Diagnosis.txt", false), false);
                        out.println(conclusion + "\n" + how.getHowExplanation(conclusion.getId()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    System.setOut(out);
                }

                @Override
                public void rowUpdated(Row row) {
                }
            };
            LiveQuery query = KS.openLiveQuery("Conclusions", null, listener);
        } catch (Throwable t) {
            t.printStackTrace();
         }
    }

    public void runEngine() {
        try {
            KS.fireAllRules();
        } catch (ConsequenceException ex) {
            // se alguma regra falhar, aparece aviso de reinicio e da restart ao UI
            UI.getWarningWindow();
        } catch (IllegalStateException ex) {
            UI.getWarningWindow();
        }
    }

}

