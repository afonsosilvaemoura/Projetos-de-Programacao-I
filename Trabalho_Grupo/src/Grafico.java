public class Grafico {
    //Cálculo do número de asteriscos por gráfico para cada valor e o seu output
    public static void graph(int bpm, float temp, int oxi, int lim1, int lim2, int lim3) {
        String graphBpm, graphTemp, graphOxi;
        int asterisks;
        final int LIMITE0 = 0, LIMITE4 = 4;
        final int LIMITE33 = 33, LIMITE36 = 36;
        final float LIMITE37_5 = (float) 37.5, LIMITE38_5 = (float) 38.5;
        final int LIMITE55 = 55, LIMITE56 = 56, LIMITE57 = 57, LIMITE58 = 58;
        final int LIMITE60 = 60, LIMITE61 = 61;
        final int LIMITE73 = 73, LIMITE74 = 74, LIMITE75 = 75, LIMITE76 = 76, LIMITE77 = 77, LIMITE78 = 78;
        final int LIMITE80 = 80, LIMITE82 = 82, LIMITE83 = 83, LIMITE84 = 84, LIMITE85 = 85;
        final int LIMITE90 = 90, LIMITE95 = 95;
        final int LIMITE100 = 100;

        //Crítico = 4 ou 33
        //Normal [9, 18]
        //Atenção ]18, 28]
        asterisks = LIMITE0;
        if (lim1 == LIMITE56 && lim2 == LIMITE73 && lim3 == LIMITE82) {
            if (bpm < lim1) { //]-inf,56[ Crítico
                asterisks = LIMITE4;
            } else if (bpm <= lim2) { //[56,73] Normal
                asterisks = (int) (Math.round(0.5294117647059 * bpm - 20.6470588235294));
            } else if (bpm <= lim3) { //]73, 82] Atenção
                asterisks = (int) (Math.round(1.1111111111111 * bpm - 63.1111111111111));
            } else { //]82, +inf[ Crítico
                asterisks = LIMITE33;
            }
        } else if (lim1 == LIMITE55 && lim2 == LIMITE74 && lim3 == LIMITE82) {
            if (bpm < lim1) { //]-inf,55[ Crítico
                asterisks = LIMITE4;
            } else if (bpm <= lim2) { //[55,74] Normal
                asterisks = (int) (Math.round(0.4736842105263 * bpm - 17.0526315789474));
            } else if (bpm <= lim3) { //]74, 82] Atenção
                asterisks = (int) (Math.round(1.25 * bpm - 74.5));
            } else { //]82, +inf[ Crítico
                asterisks = LIMITE33;
            }
        } else if (lim1 == LIMITE57 && lim2 == LIMITE75 && lim3 == LIMITE83) {
            if (bpm < lim1) { //]-inf,57[ Crítico
                asterisks = LIMITE4;
            } else if (bpm <= lim2) { //[57,75] Normal
                asterisks = (int) (Math.round(0.5 * bpm - 19.5));
            } else if (bpm <= lim3) { //]75, 83] Atenção
                asterisks = (int) (Math.round(1.25 * bpm - 75.75));
            } else { //]83, +inf[ Crítico
                asterisks = LIMITE33;
            }
        } else if (lim1 == LIMITE58 && lim2 == LIMITE76 && lim3 == LIMITE84) {
            if (bpm < lim1) { //]-inf,58[ Crítico
                asterisks = LIMITE4;
            } else if (bpm <= lim2) { //[58,76] Normal
                asterisks = (int) (Math.round(0.5 * bpm - 20));
            } else if (bpm <= lim3) { //]76, 84] Atenção
                asterisks = (int) (Math.round(1.25 * bpm - 77));
            } else { //]84, +inf[ Crítico
                asterisks = LIMITE33;
            }
        } else if (lim1 == LIMITE57 && lim2 == LIMITE75 && lim3 == LIMITE82) {
            if (bpm < lim1) { //]-inf,57[ Crítico
                asterisks = LIMITE4;
            } else if (bpm <= lim2) { //[57,75] Normal
                asterisks = (int) (Math.round(0.5 * bpm - 19.5));
            } else if (bpm <= lim3) { //]75, 82] Atenção
                asterisks = (int) (Math.round(1.4285714285714 * bpm - 89.1428571428571));
            } else { //]82, +inf[ Crítico
                asterisks = LIMITE33;
            }
        } else if (lim1 == LIMITE56 && lim2 == LIMITE73 && lim3 == LIMITE80) {
            if (bpm < lim1) { //]-inf,56[
                asterisks = LIMITE4;
            } else if (bpm <= lim2) { //[56, 73]
                asterisks = (int) (Math.round(0.5294117647059 * bpm - 20.6470588235294));
            } else if (bpm <= lim3) { //]73, 80]
                asterisks = (int) (Math.round(1.4285714285714 * bpm - 86.2857142857143));
            } else { //]82, +inf[ Crítico
                asterisks = LIMITE33;
            }
        } else if (lim1 == LIMITE61 && lim2 == LIMITE78 && lim3 == LIMITE85) {
            if (bpm < lim1) { //]-inf,61[ Crítico
                asterisks = LIMITE4;
            } else if (bpm <= lim2) { //[61,78] Normal
                asterisks = (int) (Math.round(0.5294117647059 * bpm - 23.2941176470588));
            } else if (bpm <= lim3) { //]78, 85] Atenção
                asterisks = (int) (Math.round(1.4285714285714 * bpm - 93.4285714285714));
            } else { //]85, +inf[ Crítico
                asterisks = LIMITE33;
            }
        } else if (lim1 == LIMITE60 && lim2 == LIMITE76 && lim3 == LIMITE83) {
            if (bpm < lim1) { //]-inf,60[ Crítico
                asterisks = LIMITE4;
            } else if (bpm <= lim2) { //[60,76] Normal
                asterisks = (int) (Math.round(0.5625 * bpm - 24.75));
            } else if (bpm <= lim3) { //]76, 83] Atenção
                asterisks = (int) (Math.round(1.4285714285714 * bpm - 90.5714285714286));
            } else { //]83, +inf[ Crítico
                asterisks = LIMITE33;
            }
        } else if (lim1 == LIMITE60 && lim2 == LIMITE78 && lim3 == LIMITE85) {
            if (bpm < lim1) { //]-inf,60[ Crítico
                asterisks = LIMITE4;
            } else if (bpm <= lim2) { //[60,78] Normal
                asterisks = (int) (Math.round(0.5 * bpm - 21));
            } else if (bpm <= lim3) { //]78, 85] Atenção
                asterisks = (int) (Math.round(1.4285714285714 * bpm - 93.4285714285714));
            } else { //]85, +inf[ Crítico
                asterisks = LIMITE33;
            }
        } else if (lim1 == LIMITE61 && lim2 == LIMITE77 && lim3 == LIMITE84) {
            if (bpm < lim1) { //]-inf,61[ Crítico
                asterisks = LIMITE4;
            } else if (bpm <= lim2) { //[61,77] Normal
                asterisks = (int) (Math.round(0.5625 * bpm - 25.3125));
            } else if (bpm <= lim3) { //]77, 84] Atenção
                asterisks = (int) (Math.round(1.4285714285714 * bpm - 92));
            } else { //]84, +inf[ Crítico
                asterisks = LIMITE33;
            }
        } else if (lim1 == LIMITE60 && lim2 == LIMITE77 && lim3 == LIMITE84) {
            if (bpm < lim1) { //]-inf,60[ Crítico
                asterisks = LIMITE4;
            } else if (bpm <= lim2) { //[60,77] Normal
                asterisks = (int) (Math.round(0.5294117647059 * bpm - 22.7647058823529));
            } else if (bpm <= lim3) { //]77, 84] Atenção
                asterisks = (int) (Math.round(1.4285714285714 * bpm - 92));
            } else { //]84, +inf[ Crítico
                asterisks = LIMITE33;
            }
        } else if (lim1 == LIMITE60 && lim2 == LIMITE76 && lim3 == LIMITE84) {
            if (bpm < lim1) { //]-inf,60[ Crítico
                asterisks = LIMITE4;
            } else if (bpm <= lim2) { //[60,76] Normal
                asterisks = (int) (Math.round(0.5625 * bpm - 24.75));
            } else if (bpm <= lim3) { //]76, 84] Atenção
                asterisks = (int) (Math.round(1.25 * bpm - 77));
            } else { //]84, +inf[ Crítico
                asterisks = LIMITE33;
            }
        }
        graphBpm = colourGraph(asterisks, makeGraph(asterisks));
        if (temp < LIMITE36) { //]-inf, 36[ Crítico
            asterisks = LIMITE4;
        } else if (temp <= LIMITE37_5) { //[36, 37.5] Normal
            asterisks = Math.round((6 * temp) - 207);
        } else if (temp <= LIMITE38_5) { //]37.5, 38.5] Atenção
            asterisks = Math.round((10 * temp) - 357);
        } else { //]38.5, +inf[ Crítico.
            asterisks = LIMITE33;
        }
        graphTemp = colourGraph(asterisks, makeGraph(asterisks));
        asterisks = LIMITE0;
        if (oxi < LIMITE90) { //]-inf, 90[ Crítico
            asterisks = LIMITE33;
        } else if (oxi < LIMITE95) { //[90, 95[ Atenção
            asterisks = (-2 * oxi) + 208;
        } else if (oxi <= LIMITE100) { //[95, 100] Normal
            asterisks = (int) (Math.round((-1.8 * oxi) + 189));
        }
        graphOxi = colourGraph(asterisks, makeGraph(asterisks));
        System.out.printf("\t\t\t\t\t\t  %sCrítico%s | %sNormal%s | %sAtenção%s | %sCrítico%s\n", Main.ANSI_RED, Main.ANSI_RESET, Main.ANSI_GREEN, Main.ANSI_RESET, Main.ANSI_YELLOW, Main.ANSI_RESET, Main.ANSI_RED, Main.ANSI_RESET);
        System.out.printf("Frequência cardíaca (bpm):%s\n", graphBpm);
        System.out.printf("Temperatura corporal (ºC):%s\n", graphTemp);
        System.out.printf("Saturação de oxigénio (%%):%s\n", graphOxi);
    }

    //faz o gráfico com asteriscos
    public static String makeGraph(int asterisks) {
        String graph = "";
        for (int v = 0; v < asterisks; v++) {
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
            graph = String.format("%s%s%s", Main.ANSI_RED, graph, Main.ANSI_RESET);
        } else if (asterisks > LIMITE18 && asterisks <= LIMITE28) {
            graph = String.format("%s%s%s", Main.ANSI_YELLOW, graph, Main.ANSI_RESET);
        } else if (asterisks >= LIMITE9 && asterisks <= LIMITE18) {
            graph = String.format("%s%s%s", Main.ANSI_GREEN, graph, Main.ANSI_RESET);
        }
        return graph;
    }

    //Faz output de todos os valores e chama "graph" para mostrar o gráfico
    public static void output(String nome, int idade, char sexo, int bpmMin, int bpmMax, float tempMin, float tempMax, int oxiMin, int oxiMax, int bpmMed, float tempMed, int oxiMed, int lim1, int lim2, int lim3) {
        System.out.printf("Nome: %s\n", nome);
        System.out.printf("Idade: %d\n", idade);
        System.out.printf("Sexo: %c\n", sexo);
        System.out.printf("Valor mínimo de bpm: %d. Valor máximo de bpm: %d\n", bpmMin, bpmMax);
        System.out.printf("Valor mínimo de temperatura: %.2fºC. Valor máximo de temperatura: %.2fºC\n", tempMin, tempMax);
        System.out.printf("Valor mínimo de saturação: %d%%. Valor máximo de saturação: %d%%\n", oxiMin, oxiMax);
        graph(bpmMed, tempMed, oxiMed, lim1, lim2, lim3);
    }
}