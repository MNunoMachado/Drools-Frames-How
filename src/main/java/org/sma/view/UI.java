package org.sma.view;


import java.util.Collection;

import org.sma.model.entities.FrameSystem;
import org.kie.api.runtime.ClassObjectFilter;

import org.sma.frames.WindowFrames;
import org.sma.model.Evidence;
import org.kie.api.runtime.KieSession;

import javax.swing.*;

public class UI {
     private static KieSession KS;
    private static Gui gui;
    private static WindowFrames wf;

    public static void uiInit() {
         wf = new WindowFrames();
        UI.gui =  new Gui();
        wf.gui = UI.gui;
    }


    public static boolean answer(String ev, String v) {
        @SuppressWarnings("unchecked")
        Collection<Evidence> evidences = (Collection<Evidence>) KS.getObjects(new ClassObjectFilter(Evidence.class));
        boolean questionFound = false;
        Evidence evidence = null;
        for (Evidence e : evidences) {
            if (e.getEvidence().compareTo(ev) == 0) {
                questionFound = true;
                evidence = e;
                break;
            }
        }
        if (questionFound) {
            if (evidence.getValue().compareTo(v) == 0) {
                WindowFrames.agendaEventListener.addLhs(evidence);
                return true;
            } else {
                return false;
            }
        }
         String value = readLine(ev);

        Evidence e = new Evidence(ev, value);
        KS.insert(e);

        if (value == null) {
            KS.halt();
            UI.getWarningWindow();
            return true;
        }else if (value.compareTo(v) == 0) {
            WindowFrames.getAgendaEventListener().addLhs(e);
            gui.addEvidence(e.toString());

            return true;
        } else {
            return false;
        }
    }


    private static String readLine(String ev) {
        String answers[] = {"yes", "no"};
        String answer = null;
        while (answer == null) {
            answer = (String) JOptionPane.showInputDialog(gui, ev,
                    "Question", JOptionPane.PLAIN_MESSAGE, null, answers, answers[0]);
            if (answer == null){
                break;
            }

        }
        return answer;
    }

    public static FrameSystem getFinalWindow() {
        return WindowFrames.selectedFrameSystem;
    }

    public static void startEngine() {
        wf.gui.clearEvidence();
        wf.setupEngine();
        KS = wf.getKieSession();
        wf.runEngine();
    }

    public static void getWarningWindow() {
        final JPanel panel = new JPanel();

        JOptionPane.showMessageDialog(panel, "Análise Parada.", "Aviso",
                JOptionPane.WARNING_MESSAGE);

        wf.gui.clearEvidence();
    }

    public static void getFinishWindow() {
        final JPanel panel = new JPanel();

        JOptionPane.showMessageDialog(panel, "Análise Concluída .", "Successo",
                JOptionPane.PLAIN_MESSAGE);

    }


}
