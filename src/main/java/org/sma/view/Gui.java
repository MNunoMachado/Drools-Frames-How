package org.sma.view;

import org.sma.frames.WindowFrames;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;

public class Gui extends JFrame {
    private DefaultListModel model;
    private JList list;
    private JTextArea text;
    private JTextArea textExplain;

    public Gui gui;

    public Gui() {
        gui = this;
        initialAppUI();
    }

    private void initialAppUI() {
        final JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 690);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

        //Image background
        JLabel backgroundImage = new JLabel(new ImageIcon("src/main/resources/backgroundImage.png"));
        frame.add(backgroundImage);
        backgroundImage.setLayout(new FlowLayout());

        //Title
        JLabel title = new JLabel("<html><br/><br/><br/><br/><br/><br/><div style='text-align: center; colour:black; '><b>Sistema Pericial <br/><br/>Apoio à escolha de caixilharias tradicionais</b></div></html>", SwingConstants.CENTER);

        title.setForeground(new Color(100, 100, 100));
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        Border border = BorderFactory.createLineBorder(Color.GRAY, 5);
        backgroundImage.setBorder(border);

        frame.add(title, BorderLayout.NORTH);
        backgroundImage.add(title);

        //Mouse event to start diagnosis
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                createComponents();
                gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gui.setSize(900, 690);
                gui.setVisible(true);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - gui.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - gui.getHeight()) / 2);
                gui.setLocation(x, y);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
        frame.addMouseListener(mouseListener);

        frame.setVisible(true);
    }

    private void createComponents() {

        model = new DefaultListModel();
        list = new JList(model);

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 130));
        listScroller.setAlignmentX(LEFT_ALIGNMENT);

        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));

        listPane.add(new JLabel("Evidências"));
        listPane.add(Box.createRigidArea(new Dimension(0, 5)));

        listPane.add(listScroller);
        listPane.add(Box.createRigidArea(new Dimension(0, 5)));

        listPane.add(new JLabel("Conclusões"));
        listPane.add(Box.createRigidArea(new Dimension(0, 5)));

        text = new JTextArea();
        text.setEditable(false);
        JScrollPane concPane = new JScrollPane(text);
        concPane.setAlignmentX(LEFT_ALIGNMENT);
        concPane.setPreferredSize(new Dimension(250, 130));
        listPane.add(concPane);
        listPane.add(Box.createRigidArea(new Dimension(0, 5)));
        listPane.add(new JLabel("Explicações"));

        listPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textExplain = new JTextArea();
        textExplain.setEditable(false);
        JScrollPane textPane = new JScrollPane(textExplain);

        textPane.setPreferredSize(new Dimension(250, 250));
        textPane.setAlignmentX(LEFT_ALIGNMENT);
        listPane.add(textPane);

        listPane.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel btnPanel = new JPanel();
        btnPanel.setAlignmentX(LEFT_ALIGNMENT);

        JButton startDiagnoseButton = new JButton();
        startDiagnoseButton.setPreferredSize(new Dimension(200, 30));
        startDiagnoseButton.setText("Iniciar a Análise");
        startDiagnoseButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (WindowFrames.selectedFrameSystem.hasValues())
                    UI.startEngine();
            }
        });


        JButton dimensionsButton = new JButton();

        dimensionsButton.setPreferredSize(new Dimension(200, 30));
        dimensionsButton.setText("Definir Dimensões");
        dimensionsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defineWindowDimensions();
            }
        });

        btnPanel.add(dimensionsButton, BorderLayout.WEST);

        btnPanel.add(startDiagnoseButton, BorderLayout.EAST);
        btnPanel.add(Box.createHorizontalGlue());

        listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        listPane.add(btnPanel);


        Container contentPane = getContentPane();
        contentPane.add(listPane, BorderLayout.CENTER);

    }

    public void setConclusion(String conclusion) {
        text.setText(conclusion);
    }

    public void addEvidence(String evidence) {
        model.addElement(evidence);

    }

    public void clearEvidence() {
        model.removeAllElements();
        text.setText("");
        textExplain.setText("");
    }

    public void setExplanation(String Explanation) {
        textExplain.setText(Explanation);
    }


    public void defineWindowDimensions() {
        JPanel panel = new JPanel(new GridLayout(9, 1));
        panel.setSize(400, 500);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        panel.add(new JLabel("Largura do vão [cm]"));
        JFormattedTextField width = new JFormattedTextField(formatter);
        panel.add(width);
        panel.add(new JLabel("Altura do vão [cm]"));
        JFormattedTextField height = new JFormattedTextField(formatter);
        panel.add(height);
        panel.add(new JLabel("Espessura total do vidro (ou vidros) [mm]"));
        JFormattedTextField glassthickness = new JFormattedTextField(formatter);
        panel.add(glassthickness);
        panel.add(new JLabel("Espessura total da janela (com caixa-de-ar) [mm]"));
        JFormattedTextField windowThickness = new JFormattedTextField(formatter);
        panel.add(windowThickness);
        JOptionPane.showMessageDialog(null, panel, "INSERIR MEDIDAS", 1);

        if (width != null && height != null && glassthickness != null && windowThickness != null)
            WindowFrames.defineWindowMeasures(width.getText(), height.getText(), glassthickness.getText(), windowThickness.getText());
    }

}
