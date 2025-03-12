public class Grafico {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    //Cálculo do número de asteriscos por gráfico para cada valor e o seu output
    public static void graph(int bpm, float temp, int oxi) {
        String graphBpm, graphTemp, graphOxi;
        int asterisks;
        final int LIMITEM2 = -2;
        final int LIMITE0 = 0, LIMITE4 = 4, LIMITE6 = 6;
        final int LIMITE10 = 10;
        final int LIMITE32 = 32, LIMITE33 = 33, LIMITE36 = 36;
        final float LIMITE37_5 = 37.5f, LIMITE38_5 = 38.5f;
        final int LIMITE60 = 60;
        final int LIMITE90 = 90, LIMITE95 = 95;
        final int LIMITE100 = 100;
        final int LIMITE120 = 120;
        final int LIMITE189 = 189;
        final int LIMITE207 = 207, LIMITE208 = 208;
        final int LIMITE357 = 357;
        final float LIMITE0_225 = 0.225f, LIMITE0_5 = 0.5f;
        final float LIMITEM1_8 = -1.8f;
        final float LIMITE4_5 = 4.5f;

        //Crítico = 4 ou 33
        //Normal [9, 18]
        //Atenção ]18, 28]
        if (bpm < LIMITE60) { //]-inf,60[ Crítico
            asterisks = LIMITE4;
        } else if (bpm <= LIMITE100) { //[60,100] Normal
            asterisks = (int) (Math.round(LIMITE0_225 * bpm - LIMITE4_5));
        } else if (bpm <= LIMITE120) { //]100, 120] Atenção
            asterisks = (int) (Math.round(LIMITE0_5 * bpm - LIMITE32));
        } else { //]120, +inf[ Crítico
            asterisks = LIMITE33;
        }
        graphBpm = colourGraph(asterisks, makeGraph(asterisks));
        if (temp < LIMITE36) { //]-inf, 36[ Crítico
            asterisks = LIMITE4;
        } else if (temp <= LIMITE37_5) { //[36, 37.5] Normal
            asterisks = Math.round((LIMITE6 * temp) - LIMITE207);
        } else if (temp <= LIMITE38_5) { //]37.5, 38.5] Atenção
            asterisks = Math.round((LIMITE10 * temp) - LIMITE357);
        } else { //]38.5, +inf[ Crítico.
            asterisks = LIMITE33;
        }
        graphTemp = colourGraph(asterisks, makeGraph(asterisks));
        asterisks = LIMITE0;
        if (oxi < LIMITE90) { //]-inf, 90[ Crítico
            asterisks = LIMITE33;
        } else if (oxi < LIMITE95) { //[90, 95[ Atenção
            asterisks = (LIMITEM2 * oxi) + LIMITE208;
        } else if (oxi <= LIMITE100) { //[95, 100] Normal
            asterisks = (int) (Math.round((LIMITEM1_8 * oxi) + LIMITE189));
        }
        graphOxi = colourGraph(asterisks, makeGraph(asterisks));
        System.out.printf("\t\t\t\t\t\t  %sCrítico%s | %sNormal%s | %sAtenção%s | %sCrítico%s\n", ANSI_RED, ANSI_RESET, ANSI_GREEN, ANSI_RESET, ANSI_YELLOW, ANSI_RESET, ANSI_RED, ANSI_RESET);
        System.out.printf("Frequência cardíaca (bpm):%s\n", graphBpm);
        System.out.printf("Temperatura corporal (ºC):%s\n", graphTemp);
        System.out.printf("Saturação de oxigénio (%%):%s\n", graphOxi);
    }

    //faz o gráfico com asteriscos
    public static String makeGraph(int asterisks) {
        final int LIMITE0 = 0;
        String graph = "";
        for (int v = LIMITE0; v < asterisks; v++) {
            graph += '*';
        }
        return graph;
    }

    //dá cor ao gráfico dependendo da sua posição
    public static String colourGraph(int asterisks, String graph) {
        final int LIMITE4 = 4, LIMITE9 = 9;
        final int LIMITE18 = 18;
        final int LIMITE28 = 28;
        final int LIMITE33 = 33;
        if (asterisks == LIMITE33 || asterisks == LIMITE4) {
            graph = String.format("%s%s%s", ANSI_RED, graph, ANSI_RESET);
        } else if (asterisks > LIMITE18 && asterisks <= LIMITE28) {
            graph = String.format("%s%s%s", ANSI_YELLOW, graph, ANSI_RESET);
        } else if (asterisks >= LIMITE9 && asterisks <= LIMITE18) {
            graph = String.format("%s%s%s", ANSI_GREEN, graph, ANSI_RESET);
        }
        return graph;
    }

    //Faz output de todos os valores e chama "graph" para mostrar o gráfico
    public static void output(String nome, int idade, char sexo, int bpmMin, int bpmMax, float tempMin, float tempMax, int oxiMin, int oxiMax, int bpmMed, float tempMed, int oxiMed) {
        System.out.printf("Nome: %s\n", nome);
        System.out.printf("Idade: %d\n", idade);
        System.out.printf("Sexo: %c\n", sexo);
        System.out.printf("Valor mínimo de bpm: %d. Valor máximo de bpm: %d\n", bpmMin, bpmMax);
        System.out.printf("Valor mínimo de temperatura: %.2fºC. Valor máximo de temperatura: %.2fºC\n", tempMin, tempMax);
        System.out.printf("Valor mínimo de saturação: %d%%. Valor máximo de saturação: %d%%\n", oxiMin, oxiMax);
        graph(bpmMed, tempMed, oxiMed);
    }
}