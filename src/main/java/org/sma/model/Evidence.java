package org.sma.model;

public class Evidence extends Fact {


    public static final String CAIXILHARIA_TRADICIONAL = "É um sistema de caixilharia tradicional.";

    public static final String CAIXILHARIA_EXTERIOR = "É um sistema de caixilharia para exterior.";

    public static final String FIXA_BATENTE = "É um sistema de caixilharia fixa ou de batente.";

    public static final String RESGUARDO = "É um resguardo de duche.";
    public static final String RESGUARDO_FIXO_BATENTE = "É um resguardo de duche fixo ou de batente.";
    public static final String RESGUARDO_CORRER = "É um resguardo de duche de correr.";

    public static final String INT_FIXA_OU_BATENTE = "É um sistema, interior, de caixilharia fixa ou de batente.";

    public static final String EXT_FIXA_OU_BATENTE = "É um sistema, exterior, de caixilharia fixa ou de batente.";

    public static final String AUTOMATISMO = "Necessita de automatismo.";
    public static final String ROTURA_TERMICA = "Necessita de rotura térmica ou isolamento sonoro.";
    public static final String GAMA_ALTA = "Pretende-se uma gama alta.";

    public static final String JANELA_FIXA = "Trata-se de uma janela fixa.";
    public static final String JANELA_BATENTE = "Trata-se de uma janela de batante.";
    public static final String PORTA = "Trata-se de uma porta.";


    private String evidence;
    private String value;

    public Evidence(String ev, String v) {
        evidence = ev;
        value = v;
    }

    public String getEvidence() {
        return evidence;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return (evidence + " = " + value);
    }

}

