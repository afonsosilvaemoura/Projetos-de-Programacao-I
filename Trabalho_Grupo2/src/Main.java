import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Scanner;

public class Main {
    public static final String ficheiro = "pacientes.txt";

    //Array [0,     1,           2,           3,     4]
    //dados [codigo,nome,        genero,      idade, data e Hora]
    //sinais[bpm,   temperatura, saturação O2]

    //tem a certeza que o ficheiro existe e chama o menu
    public static void main(String[] args) throws IOException {
        criarFicheiro();
        String[][] dados = null;
        float[][] sinais = null;
        menu(dados, sinais);
    }

    //abre o menu que pergunta ao utilizador o que quer fazer
    public static void menu(String[][] dados, float[][] sinais) throws IOException {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE4 = 4, LIMITE5 = 5, LIMITE6 = 6, LIMITE7 = 7, LIMITE8 = 8, LIMITE9 = 9;
        boolean zero = false, repete = true;
        do {
            dados = lerFicheiroDados();
            sinais = lerFicheiroSinais();
            System.out.println("1. Introduzir novo paciente\n2. Adicionar valores a um paciente existente\n3. Apagar paciente\n4. Mostrar gráfico de paciente\n5. Mostrar todos os códigos e nomes\n6. Mostrar os dados de todos os pacientes\n7. Calcular paciente com maior risco\n8. Calcular percentagem de pacientes Críticos\n9. Simular incremento percentual\n0. Sair");
            int opcao = lerInt("Introduza a opção: ", LIMITE0, LIMITE9, zero);
            switch (opcao) {
                case LIMITE1:
                    introduzirPaciente(dados, sinais);
                    break;
                case LIMITE3:
                    eliminarPaciente(dados);
                    break;
                case LIMITE4:
                    grafico(dados, sinais);
                    break;
                case LIMITE5:
                    codigosENomes(dados);
                    break;
                case LIMITE6:
                    // ordena os dados antes de mostrar
                    ordenarDados(dados, sinais);
                    dados = lerFicheiroDados();
                    sinais = lerFicheiroSinais();
                    mostrarDados(dados, sinais);
                    break;
                case LIMITE0:
                    repete = false;
                    ordenarDados(dados, sinais);
                    break;
                case LIMITE2:
                    adicionarValoresPacienteExistente(dados, sinais);
                    break;
                case LIMITE7:
                    maiorRisco(dados, sinais);
                    break;
                case LIMITE8:
                    percentagemDePacientesCriticos(dados, sinais);
                    break;
                case LIMITE9:
                    alterarSinaisVitais(dados, sinais);
                    break;
            }
        } while (repete);
    }

    //recebe uma matriz com os códigos dos pacientes e os seus sinais vitais, faz a média entre eles
    public static float[][] mediasDeValores(boolean continuar, boolean acabou, float[][] valores, float[][] valoreFinais) {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE4 = 4;
        int j = LIMITE0;
        for (int v = LIMITE1; v < LIMITE4; v++) {
            for (int i = LIMITE0; i < valores.length; i++) {
                int m = LIMITE0;
                float media = LIMITE0;
                while (continuar && !acabou) {
                    valoreFinais[i][LIMITE0] = valores[j][LIMITE0];
                    m++;
                    media = valores[j][v] + media;
                    try {
                        if (valores[j][LIMITE0] == valores[j = j + LIMITE1][LIMITE0]) {
                            continuar = true;
                        } else {
                            continuar = false;
                        }
                    } catch (Exception e) {
                        continuar = false;
                        acabou = true;
                        j--;
                    }
                    valoreFinais[i][v] = media / m;
                }
                continuar = true;
            }
            continuar = true;
            acabou = false;
            j = LIMITE0;
        }
        return valoreFinais;
    }

    //calcula a percentagem de pacientes cuja média dos sinais tem pelo menos um valor numa situação crítica
    public static void percentagemDePacientesCriticos(String[][] dados, float[][] sinais) {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE4 = 4;
        final int LIMITE36 = 36;
        final float LIMITE38_5 = 38.5f;
        final int LIMITE60 = 60;
        final int LIMITE90 = 90;
        final int LIMITE100 = 100;
        final int LIMITE120 = 120;
        if (dados[LIMITE0][LIMITE0] != null) {
            float[][] sinaisEId = new float[dados.length][LIMITE4];
            float[][] sinaisEIdFinal = new float[dados.length][LIMITE4];
            int length = dados.length, cont = LIMITE0;
            boolean continuar = true, acabou = false;
            for (int i = LIMITE0; i < length; i++) {
                sinaisEId[i][LIMITE0] = Float.parseFloat(dados[i][LIMITE0]);
                sinaisEId[i][LIMITE1] = sinais[i][LIMITE0];
                sinaisEId[i][LIMITE2] = sinais[i][LIMITE1];
                sinaisEId[i][LIMITE3] = sinais[i][LIMITE2];
            }
            sinaisEId = ordenaScoreEId(sinaisEId);
            sinaisEIdFinal = mediasDeValores(continuar, acabou, sinaisEId, sinaisEIdFinal);

            for (int i = LIMITE0; i < length; i++) {
                if (sinaisEIdFinal[i][LIMITE0] != LIMITE0) {
                    cont++;
                }
            }

            float[][] temp2 = new float[cont][LIMITE4];
            int v = LIMITE0;
            for (int i = LIMITE0; i < length; i++) {
                if (sinaisEIdFinal[i][LIMITE0] != LIMITE0) {
                    temp2[v][LIMITE0] = sinaisEIdFinal[i][LIMITE0];
                    temp2[v][LIMITE1] = sinaisEIdFinal[i][LIMITE1];
                    temp2[v][LIMITE2] = sinaisEIdFinal[i][LIMITE2];
                    temp2[v][LIMITE3] = sinaisEIdFinal[i][LIMITE3];
                    v++;
                }
            }
            sinaisEIdFinal = temp2;
            cont = LIMITE0;
            for (int i = LIMITE0; i < sinaisEIdFinal.length; i++) {
                if (sinaisEIdFinal[i][LIMITE1] < LIMITE60 || sinaisEIdFinal[i][LIMITE1] > LIMITE120 || sinaisEIdFinal[i][LIMITE2] < LIMITE36 || sinaisEIdFinal[i][LIMITE2] > LIMITE38_5 || sinaisEIdFinal[i][LIMITE3] < LIMITE90) {
                    cont++;
                }
            }
            System.out.printf("A percentagem de pacientes críticos é %.2f%% (%d/%d)\n", (((float) cont * LIMITE100) / (float) sinaisEIdFinal.length), cont, sinaisEIdFinal.length);
        } else {
            System.out.println("Não existem pacientes.");
        }
    }

    //ordena uma matriz pela ordem do código dos pacientes
    public static float[][] ordenaScoreEId(float[][] scoreGravidadeEId) {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2;
        boolean troca = true;
        int length = scoreGravidadeEId.length;
        for (int i = LIMITE0; i < length - LIMITE1 && troca; i++) {
            troca = false;
            for (int j = LIMITE0; j < length - i - LIMITE1; j++) {
                if (scoreGravidadeEId[j][LIMITE0] > scoreGravidadeEId[j + LIMITE1][LIMITE0]) {
                    float[] temp1 = new float[LIMITE2];
                    temp1[LIMITE0] = scoreGravidadeEId[j][LIMITE0];
                    temp1[LIMITE1] = scoreGravidadeEId[j][LIMITE1];
                    scoreGravidadeEId[j][LIMITE0] = scoreGravidadeEId[j + LIMITE1][LIMITE0];
                    scoreGravidadeEId[j][LIMITE1] = scoreGravidadeEId[j + LIMITE1][LIMITE1];
                    scoreGravidadeEId[j + LIMITE1][LIMITE0] = temp1[LIMITE0];
                    scoreGravidadeEId[j + LIMITE1][LIMITE1] = temp1[LIMITE1];
                    troca = true;
                }
            }
        }
        return scoreGravidadeEId;
    }

    //recebe o valor do bpm de um paciente e devolve a sua nota para o cálculo do valor de risco
    public static int getBpmNota(int bpm) {
        final int LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE4 = 4, LIMITE5 = 5;
        final int LIMITE30 = 30, LIMITE39 = 39;
        final int LIMITE40 = 40, LIMITE49 = 49;
        final int LIMITE50 = 50, LIMITE59 = 59;
        final int LIMITE60 = 60;
        final int LIMITE100 = 100, LIMITE101 = 101;
        final int LIMITE120 = 120, LIMITE121 = 121;
        final int LIMITE140 = 140, LIMITE141 = 141;
        final int LIMITE160 = 160;
        if (bpm >= LIMITE60 && bpm <= LIMITE100) return LIMITE1;
        if ((bpm >= LIMITE50 && bpm <= LIMITE59) || (bpm >= LIMITE101 && bpm <= LIMITE120)) return LIMITE2;
        if ((bpm >= LIMITE40 && bpm <= LIMITE49) || (bpm >= LIMITE121 && bpm <= LIMITE140)) return LIMITE3;
        if ((bpm >= LIMITE30 && bpm <= LIMITE39) || (bpm >= LIMITE141 && bpm <= LIMITE160)) return LIMITE4;
        return LIMITE5;
    }

    //recebe o valor da temperatura de um paciente e devolve a sua nota para o cálculo do valor de risco
    public static int getTempNota(float temp) {
        final int LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE4 = 4, LIMITE5 = 5;
        final int LIMITE35 = 35;
        final float LIMITE35_4 = 35.4f, LIMITE35_5 = 35.5f;
        final int LIMITE36 = 36;
        final float LIMITE36_1 = 36.1f;
        final float LIMITE37_5 = 37.5f, LIMITE37_6 = 37.6f;
        final int LIMITE38 = 38;
        final float LIMITE38_1 = 38.1f;
        final int LIMITE39 = 39;
        final float LIMITE39_1 = 39.1f;
        final int LIMITE40 = 40;
        if (temp >= LIMITE36_1 && temp <= LIMITE37_5) return LIMITE1;
        if (temp >= LIMITE37_6 && temp <= LIMITE38) return LIMITE2;
        if ((temp >= LIMITE38_1 && temp <= LIMITE39) || (temp >= LIMITE35_5 && temp <= LIMITE36)) return LIMITE3;
        if ((temp >= LIMITE39_1 && temp <= LIMITE40) || (temp >= LIMITE35 && temp <= LIMITE35_4)) return LIMITE4;
        return LIMITE5;
    }

    //recebe o valor da saturação de O2 de um paciente e devolve a sua nota para o cálculo do valor de risco
    public static int getSatNota(int sat) {
        final int LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE4 = 4, LIMITE5 = 5;
        final int LIMITE81 = 81, LIMITE86 = 86;
        final int LIMITE91 = 91, LIMITE95 = 95;
        if (sat > LIMITE95) return LIMITE1;
        if (sat >= LIMITE91) return LIMITE2;
        if (sat >= LIMITE86) return LIMITE3;
        if (sat >= LIMITE81) return LIMITE4;
        return LIMITE5;
    }

    //calcular o valor de risco usando as notas
    public static float[] scoreGravidade(float[][] sinais) {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2;
        final float LIMITE0_3 = 0.3f, LIMITE0_4 = 0.4f;
        int length = sinais.length, bpm, sat, bpmNota, tempNota, satNota;
        float temp;
        float[] scoreGravidade = new float[length];
        for (int i = LIMITE0; i < length; i++) {
            bpm = (int) sinais[i][LIMITE0];
            temp = sinais[i][LIMITE1];
            sat = (int) sinais[i][LIMITE2];
            bpmNota = getBpmNota(bpm);
            tempNota = getTempNota(temp);
            satNota = getSatNota(sat);
            scoreGravidade[i] = ((bpmNota * LIMITE0_3) + (tempNota * LIMITE0_4) + (satNota * LIMITE0_3));
        }
        return scoreGravidade;
    }

    //procura qual(ou quais) são os pacientes com maior valor de risco
    public static void maiorRisco(String[][] dados, float[][] sinais) {
        final int LIMITEM1 = -1, LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2;
        int cont = LIMITE0;
        boolean continuar = true, acabou = false;
        if (dados[LIMITE0][LIMITE0] != null) {
            float[][] scoreGravidadeEId = new float[dados.length][LIMITE2];
            float[][] scoreGravidadeEIdFinal = new float[dados.length][LIMITE2];
            int length = dados.length;
            for (int i = LIMITE0; i < length; i++) {
                scoreGravidadeEId[i][LIMITE0] = Float.parseFloat(dados[i][LIMITE0]);
                scoreGravidadeEId[i][LIMITE1] = scoreGravidade(sinais)[i];
            }
            scoreGravidadeEId = ordenaScoreEId(scoreGravidadeEId);
            scoreGravidadeEIdFinal = mediasDeRiscos(continuar, acabou, scoreGravidadeEId, scoreGravidadeEIdFinal);

            for (int i = LIMITE0; i < length; i++) {
                if (scoreGravidadeEIdFinal[i][LIMITE0] != LIMITE0) {
                    cont++;
                }
            }

            float[][] temp2 = new float[cont][LIMITE2];
            int v = LIMITE0;
            for (int i = LIMITE0; i < length; i++) {
                if (scoreGravidadeEIdFinal[i][LIMITE0] != LIMITE0) {
                    temp2[v][LIMITE0] = scoreGravidadeEIdFinal[i][LIMITE0];
                    temp2[v][LIMITE1] = scoreGravidadeEIdFinal[i][LIMITE1];
                    v++;
                }
            }
            scoreGravidadeEIdFinal = temp2;
            float[][] maiorRisco = {{LIMITE0, LIMITEM1}};
            float[][] temp;

            for (int i = LIMITE0; i < scoreGravidadeEIdFinal.length; i++) {
                if (scoreGravidadeEIdFinal[i][LIMITE1] > maiorRisco[LIMITE0][LIMITE1]) {
                    maiorRisco = new float[LIMITE1][LIMITE2];
                    maiorRisco[LIMITE0][LIMITE0] = scoreGravidadeEIdFinal[i][LIMITE0];
                    maiorRisco[LIMITE0][LIMITE1] = scoreGravidadeEIdFinal[i][LIMITE1];
                } else if (scoreGravidadeEIdFinal[i][LIMITE1] == maiorRisco[LIMITE0][LIMITE1]) {
                    temp = new float[maiorRisco.length + LIMITE1][LIMITE2];
                    for (int k = LIMITE0; k < maiorRisco.length; k++) {
                        for (int l = LIMITE0; l < LIMITE2; l++) {
                            temp[k][l] = maiorRisco[k][l];
                        }
                    }
                    temp[temp.length - LIMITE1][LIMITE0] = scoreGravidadeEIdFinal[i][LIMITE0];
                    temp[temp.length - LIMITE1][LIMITE1] = scoreGravidadeEIdFinal[i][LIMITE1];
                    maiorRisco = temp;
                }
            }
            String nome = null;
            for (int i = LIMITE0; i < dados.length && nome == null; i++) {
                for (int j = LIMITE0; j < maiorRisco.length; j++) {
                    if (maiorRisco[j][LIMITE0] == Integer.parseInt(dados[i][LIMITE0])) {
                        nome = dados[i][LIMITE1];
                    }
                }
            }
            for (int i = LIMITE0; i < maiorRisco.length; i++) {
                System.out.printf("Paciente(s) com o maior risco: %s;\n", nome);
                System.out.printf("E o seu respetivo risco: %.2f\n", maiorRisco[i][LIMITE1]);
            }
        } else {
            System.out.println("Não existem pacientes.");
        }
    }

    //calcula a média dos valores de risco de cada paciente quando esse paciente tem mais do que uma linha de valores
    public static float[][] mediasDeRiscos(boolean continuar, boolean acabou, float[][] scoreGravidadeEId, float[][] scoreGravidadeEIdFinal) {
        final int LIMITE0 = 0, LIMITE1 = 1;
        int j = LIMITE0;
        for (int i = LIMITE0; i < scoreGravidadeEId.length; i++) {
            int m = LIMITE0;
            float media = LIMITE0;
            while (continuar && !acabou) {
                scoreGravidadeEIdFinal[i][LIMITE0] = scoreGravidadeEId[j][LIMITE0];
                m++;
                media = scoreGravidadeEId[j][LIMITE1] + media;
                try {
                    if (scoreGravidadeEId[j][LIMITE0] == scoreGravidadeEId[j = j + LIMITE1][LIMITE0]) {
                        continuar = true;
                    } else {
                        continuar = false;
                    }
                } catch (Exception e) {
                    continuar = false;
                    acabou = true;
                    j--;
                }
                scoreGravidadeEIdFinal[i][LIMITE1] = media / m;
            }
            continuar = true;
        }
        return scoreGravidadeEIdFinal;
    }

    //pede ao utilizador um valor entre -100 e 100 e devolver uma simulação do que aconteceria caso todos os valores guardados subitamente sofreçem uma alteração a essa percentagem
    public static void alterarSinaisVitais(String[][] dados, float[][] sinais) {
        final int LIMITEM100 = -100;
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2;
        final int LIMITE100 = 100;
        boolean zero = false;
        int numero;
        if (dados[LIMITE0][LIMITE0] != null) {
            numero = lerInt("Para alterar o valor de percentagem dos sinais vitais, introduza um número de -100 a 100: ", LIMITEM100, LIMITE100, zero);
            for (int i = LIMITE0; i < sinais.length; i++) {
                // Alterar BPM
                sinais[i][LIMITE0] = alterarValor(sinais[i][LIMITE0], numero);
                // Alterar Temperatura
                sinais[i][LIMITE1] = alterarValor(sinais[i][LIMITE1], numero);
                // Alterar Saturação
                sinais[i][LIMITE2] = alterarValor(sinais[i][LIMITE2], numero);
            }
            // Exibe os novos valores de sinais vitais
            mostrarDados(dados, sinais);
        } else {
            System.out.println("Não existem pacientes.");
        }
    }

    // Função para alterar o valor de um sinal vital com base em um aumento ou diminuição em percentagem
    public static float alterarValor(float valorOriginal, int numero) {
        final float LIMITE0_01 = 0.01f;
        // Cálculo do valor de alteração percentual
        float percentagemAlteracao = numero * LIMITE0_01;  // converte o número para uma fração percentual
        // Cálculo do valor alterado
        float valorAlterado = valorOriginal + (valorOriginal * percentagemAlteracao);
        return valorAlterado;
    }

    //ordenar os dados (usando BubbleSort) e guarda os no ficheiro organizados
    public static void ordenarDados(String[][] dados, float[][] sinais) throws FileNotFoundException {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE4 = 4;
        if (dados[LIMITE0][LIMITE0] != null) {
            boolean troca = true;
            String texto;
            File file = new File(ficheiro);
            for (int i = LIMITE0; i < dados.length - LIMITE1 && troca; i++) {
                troca = false;
                for (int j = LIMITE0; j < dados.length - i - LIMITE1; j++) {
                    if (Integer.parseInt(dados[j][LIMITE0]) > Integer.parseInt(dados[j + LIMITE1][LIMITE0])) {
                        // troca os dados e sinais entre j e j+1
                        String[] tempDados = dados[j];
                        float[] tempSinais = sinais[j];
                        dados[j] = dados[j + LIMITE1];
                        sinais[j] = sinais[j + LIMITE1];
                        dados[j + LIMITE1] = tempDados;
                        sinais[j + LIMITE1] = tempSinais;
                        troca = true;
                    }
                }
            }
            String temp = "";
            for (int uso = LIMITE0; uso < dados.length; uso++) {
                texto = String.format("%s %s %s %s %s %.0f %.2f %.0f ", dados[uso][LIMITE0], dados[uso][LIMITE1], dados[uso][LIMITE2], dados[uso][LIMITE3], dados[uso][LIMITE4], sinais[uso][LIMITE0], sinais[uso][LIMITE1], sinais[uso][LIMITE2]);
                temp += String.format("%s\n", texto);
            }


            Formatter formatter = new Formatter(file);
            formatter.format(temp);
            formatter.close();
        }
    }

    //mostra os dados no ecrã
    public static void mostrarDados(String[][] dados, float[][] sinais) {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE4 = 4;
        if (dados[LIMITE0][LIMITE0] != null) {
            // cabeçalho no ecra
            System.out.printf(
                    "%-10s %-15s %-10s %-10s %-20s %10s %10s %10s%n",
                    "ID", "Nome", "Sexo", "Idade", "Data", "Bpm", "Temp", "Sat"
            );

            // exibe os dados formatados
            for (int i = LIMITE0; i < dados.length; i++) {
                String texto = String.format(
                        "%-10s %-15s %-10s %-10s %-20s %10.0f %10.2f %10.0f",
                        dados[i][LIMITE0], dados[i][LIMITE1], dados[i][LIMITE2], dados[i][LIMITE3], dados[i][LIMITE4],
                        sinais[i][LIMITE0], sinais[i][LIMITE1], sinais[i][LIMITE2]
                );
                System.out.println(texto);
            }
        } else {
            System.out.println("Não existem pacientes.");
        }
    }

    //chama inputInteiros e limitesInteiros
    public static int lerInt(String texto, int limiteInf, int limiteSup, boolean zero) {
        int valor;
        boolean teste;
        do {
            valor = inputInteiros(texto);
            teste = limitesInteiros(valor, limiteInf, limiteSup, zero);
        } while (!teste);
        return valor;
    }

    //chama inputReais e limitesReais
    public static float lerFloat(String texto, int limiteInf, int limiteSup, boolean zero) {
        boolean teste;
        float valor;
        do {
            valor = inputReais(texto);
            teste = limitesReais(valor, limiteInf, limiteSup, zero);
        } while (!teste);
        return valor;
    }

    //se o ficheiro não existir ainda é criado
    public static void criarFicheiro() throws IOException {
        File pacientes = new File(ficheiro);
        if (!pacientes.exists()) {
            pacientes.createNewFile();
        }
    }

    //garante que o valor introduzido é um número inteiro
    public static int inputInteiros(String texto) {
        final int LIMITEM1 = -1, LIMITE0 = 0, LIMITE1 = 1;
        System.out.print(texto);
        Scanner entrada = new Scanner(System.in);
        String temporario;
        int valor = LIMITEM1, falhou;

        do {
            temporario = entrada.nextLine();
            if (temporario.isEmpty()) {
                System.out.print("Valor não inserido. " + texto);
                falhou = LIMITE1;
            } else {
                try {
                    valor = Integer.parseInt(temporario);
                    falhou = LIMITE0;
                } catch (NumberFormatException e) {
                    System.out.print("Input inválido. " + texto);
                    falhou = LIMITE1;
                }
            }
        } while (falhou == LIMITE1);
        return valor;
    }

    //garante que o valor inteiro recebido está entre os dois limites dados
    public static boolean limitesInteiros(int valor, int limiteI, int limiteS, boolean zero) {
        final int LIMITE0 = 0;
        if (valor == LIMITE0 && zero) {
            return true;
        } else if (valor < limiteI || valor > limiteS) {
            System.out.println("Valor Inválido.");
            return false;
        } else return true;
    }

    //garante que o valor introduzido é um número real
    public static float inputReais(String texto) {
        final int LIMITEM1 = -1, LIMITE0 = 0, LIMITE1 = 1;
        System.out.print(texto);
        Scanner entrada = new Scanner(System.in);
        String temporario;
        float valor = LIMITEM1;
        int falhou;

        do {
            temporario = entrada.nextLine();
            if (temporario.isEmpty()) {
                System.out.print("Valor não inserido. " + texto);
                falhou = LIMITE1;
            } else {
                try {
                    valor = Float.parseFloat(temporario);
                    falhou = LIMITE0;
                } catch (NumberFormatException e) {
                    System.out.print("Input inválido. " + texto);
                    falhou = LIMITE1;
                }
            }
        } while (falhou == LIMITE1);
        return valor;
    }

    //garante que o valor real recebido está entre os dois limites dados
    public static boolean limitesReais(float valor, int limiteI, int limiteS, boolean zero) {
        final int LIMITE0 = 0;
        if (valor == LIMITE0 && zero) {
            return true;
        } else if (valor < limiteI || valor > limiteS) {
            System.out.println("Valor Inválido.");
            return false;
        } else return true;
    }

    //adiciona os novos valores recebidos ao Array dados e devolve o novo array com os novos valores (caso o array recebido fosse nulo cria um novo Array apenas com os novos dados)
    public static String[][] dadosPaciente(String[][] dados, int codigo, String nome, char sexo, int idade, String diaHora) {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE4 = 4, LIMITE5 = 5;
        String[][] novosDados;
        int length = dados.length, newLength = length + LIMITE1;
        if (dados[LIMITE0][LIMITE0] != null) {
            novosDados = new String[newLength][LIMITE5];
            for (int i = LIMITE0; i < length; i++) {
                for (int j = LIMITE0; j < LIMITE5; j++) {
                    novosDados[i][j] = dados[i][j];
                }
            }
            //System.out.println("novos dados antes: "+ Arrays.deepToString(novosDados));
            novosDados[length][LIMITE0] = String.format("%d", codigo);
            novosDados[length][LIMITE1] = nome;
            novosDados[length][LIMITE2] = String.format("%c", sexo);
            novosDados[length][LIMITE3] = String.format("%d", idade);
            novosDados[length][LIMITE4] = diaHora;
            //System.out.println("novos dados depois: "+ Arrays.deepToString(novosDados));
        } else {
            novosDados = new String[LIMITE1][LIMITE5];

            //System.out.println("novos dados antes: "+ Arrays.deepToString(novosDados));
            novosDados[LIMITE0][LIMITE0] = String.format("%d", codigo);
            novosDados[LIMITE0][LIMITE1] = nome;
            novosDados[LIMITE0][LIMITE2] = String.format("%c", sexo);
            novosDados[LIMITE0][LIMITE3] = String.format("%d", idade);
            novosDados[LIMITE0][LIMITE4] = diaHora;
            //System.out.println("novos dados depois: "+ Arrays.deepToString(novosDados));
        }

        return novosDados;
    }

    //adiciona os novos valores recebidos ao Array sinais e devolve o novo array com os novos valores (caso o array recebido fosse nulo cria um novo Array apenas com os novos dados)
    public static float[][] sinaisPaciente(float[][] sinais, int Bpm, float Temp, int Sat) {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3;
        float[][] novosDados;
        int length = sinais.length, newLength = length + LIMITE1;
        if (sinais[LIMITE0][LIMITE0] != LIMITE0) {
            novosDados = new float[newLength][LIMITE3];
            for (int i = LIMITE0; i < length; i++) {
                for (int j = LIMITE0; j < LIMITE3; j++) {
                    novosDados[i][j] = sinais[i][j];
                }
            }
            //System.out.println("novos sinais antes: "+ Arrays.deepToString(novosDados));
            novosDados[length][LIMITE0] = Bpm;
            novosDados[length][LIMITE1] = Temp;
            novosDados[length][LIMITE2] = Sat;
            //System.out.println("novos sinais depois: "+ Arrays.deepToString(novosDados));
        } else {
            novosDados = new float[LIMITE1][LIMITE3];

            System.out.println("novos sinais antes: " + Arrays.deepToString(novosDados));
            novosDados[LIMITE0][LIMITE0] = Bpm;
            novosDados[LIMITE0][LIMITE1] = Temp;
            novosDados[LIMITE0][LIMITE2] = Sat;
            System.out.println("novos sinais depois: " + Arrays.deepToString(novosDados));
        }
        return novosDados;
    }

    //cria um novo paciente e adiciona-o ao ficheiro
    public static void introduzirPaciente(String[][] dados, float[][] sinais) throws IOException {
        final int LIMITE18 = 18;
        final int LIMITE30 = 30;
        final int LIMITE45 = 45;
        final int LIMITE70 = 70;
        final int LIMITE100 = 100, LIMITE130 = 130;
        final int LIMITE250 = 250;
        final int LIMITE100000 = 100000;
        final int LIMITE900000 = 900000;
        Scanner sc = new Scanner(System.in);
        int codigo;
        boolean zero = false;
        do {
            codigo = (int) ((Math.random() * LIMITE900000) + LIMITE100000);//[0,1[, [0,900000[
        } while (!codigoDifferente(codigo, dados));
        System.out.print("Introduza o nome do paciente: ");
        String nome = sc.nextLine();
        char sexo = genero();
        int idade = lerInt("Digite a idade do paciente (18+): ", LIMITE18, LIMITE130, zero);
        int Bpm = lerInt("Introduza o valor de bpm : ", LIMITE30, LIMITE250, zero);
        float Temp = lerFloat("Introduza o valor da temperatura: ", LIMITE30, LIMITE45, zero);
        int Sat = lerInt("Introduza o valor de saturação de O2: ", LIMITE70, LIMITE100, zero);

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);
        dados = dadosPaciente(dados, codigo, nome, sexo, idade, formattedDateTime);
        sinais = sinaisPaciente(sinais, Bpm, Temp, Sat);
        guardarFicheiro(dados, sinais);
    }

    //garante que o valore introduzido é 'm' ou 'f'
    public static char genero() {
        final int LIMITE0 = 0, LIMITE1 = 1;
        String texto = "Digite o género do paciente (m/f): ";
        System.out.print(texto);
        Scanner entrada = new Scanner(System.in);
        String temporario;
        char sexo = 'n';
        int verificaGenero = LIMITE0;
        do {
            temporario = entrada.nextLine().toLowerCase();
            if (temporario.length() == LIMITE1) {
                sexo = temporario.charAt(LIMITE0);
                if (sexo == 'f') {
                    verificaGenero++;
                } else if (sexo == 'm') {
                    verificaGenero++;
                }
                if (verificaGenero == LIMITE0) {
                    System.out.print("Aviso género inválido. " + texto);
                }
            } else System.out.print("Aviso género inválido. " + texto);
        } while (verificaGenero == LIMITE0);
        return sexo;
    }

    //adiciona os últimos valores dos arrays fornecidos ao ficheiro
    public static void guardarFicheiro(String[][] dados, float[][] sinais) throws IOException {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE4 = 4;
        FileWriter escreve = new FileWriter(ficheiro, true);
        String texto;
        int uso = dados.length - LIMITE1;
        texto = String.format("%s %s %s %s %s %.0f %.2f %.0f \n", dados[uso][LIMITE0], dados[uso][LIMITE1], dados[uso][LIMITE2], dados[uso][LIMITE3], dados[uso][LIMITE4], sinais[uso][LIMITE0], sinais[uso][LIMITE1], sinais[uso][LIMITE2]);
        escreve.write(texto);
        escreve.close();
        System.out.printf("O seu código é %s\n", dados[uso][LIMITE0]);
    }

    //lê o ficheiro e guarda os 5 primeiros dados no Array dados
    public static String[][] lerFicheiroDados() throws FileNotFoundException {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE4 = 4, LIMITE5 = 5;
        File file = new File(ficheiro);
        Scanner ler = new Scanner(file);
        int linhas = LIMITE0;
        while (ler.hasNextLine()) {
            linhas++;
            ler.nextLine();
        }
        if (linhas != LIMITE0) {
            String[][] dados = new String[linhas][LIMITE5];
            ler.close();
            ler = new Scanner(file);
            for (int i = LIMITE0; i < linhas; i++) {
                dados[i][LIMITE0] = ler.next();
                dados[i][LIMITE1] = ler.next();
                dados[i][LIMITE2] = ler.next();
                dados[i][LIMITE3] = ler.next();
                dados[i][LIMITE4] = String.format("%s %s", ler.next(), ler.next());
                for (int j = LIMITE0; j < LIMITE3; j++) {
                    ler.next();
                }
            }
            ler.close();
            return dados;
        } else {
            String[][] dados = new String[LIMITE1][LIMITE5];
            ler.close();
            return dados;
        }
    }

    //lê o ficheiro e guarda os últimos 3 dados no Array sinais
    public static float[][] lerFicheiroSinais() throws FileNotFoundException {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE6 = 6;
        File file = new File(ficheiro);
        Scanner ler = new Scanner(file);
        int linhas = LIMITE0;
        while (ler.hasNextLine()) {
            linhas++;
            ler.nextLine();
        }
        if (linhas != LIMITE0) {
            float[][] sinais = new float[linhas][LIMITE3];
            ler.close();
            ler = new Scanner(file);
            for (int i = LIMITE0; i < linhas; i++) {
                for (int j = LIMITE0; j < LIMITE6; j++) {
                    ler.next();
                }
                sinais[i][LIMITE0] = ler.nextFloat();
                sinais[i][LIMITE1] = ler.nextFloat();
                sinais[i][LIMITE2] = ler.nextFloat();
            }
            ler.close();
            return sinais;
        } else {
            float[][] sinais = new float[LIMITE1][LIMITE3];
            ler.close();
            return sinais;
        }
    }

    //garante que o código fornecido é diferente de todos os outros
    public static boolean codigoDifferente(int codigo, String[][] dados) {
        final int LIMITE0 = 0;
        for (int i = LIMITE0; i < dados.length; i++) {
            if (String.format("%d", codigo).equals(dados[i][LIMITE0])) {
                return false;
            }
        }
        return true;
    }

    //mostra ao utilizador todos os códigos e a quem eles pertencem mostrando os só uma vez caso esse paciente tenha mais do que uma linha
    public static void codigosENomes(String[][] dados) {
        final int LIMITE0 = 0, LIMITE1 = 1;
        int length = dados.length;
        int[] tempI = new int[length];
        String[] tempS = new String[length];
        if (dados[LIMITE0][LIMITE0] != null) {
            for (int i = LIMITE0; i < length; i++) {
                tempI[i] = Integer.parseInt(dados[i][LIMITE0]);
                tempS[i] = dados[i][LIMITE1];
            }
            String[] nomes = semRepetirStrings(tempS);
            int[] codigos = semRepetirInteiros(tempI);
            for (int i = LIMITE0; i < nomes.length; i++) {
                System.out.printf("Nome: %s Código: %d\n", nomes[i], codigos[i]);
            }
        } else {
            System.out.println("Não existem pacientes.");
        }
    }

    //chama a classe Gráfico para mostrar o gráfico do paciente e chama procurarPacientes para criar o mínimo, média e máximo do paciente requerido pelo utilizador
    public static void grafico(String[][] dados, float[][] sinais) {
        final int LIMITEM1 = -1, LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE4 = 4, LIMITE5 = 5, LIMITE6 = 6, LIMITE7 = 7, LIMITE8 = 8, LIMITE9 = 9;
        int usar;
        float[] valores;
        if (dados[LIMITE0][LIMITE0] != null) {
            valores = procurarPacientes(dados, sinais);
            if (valores[LIMITE9] != LIMITEM1) {
                usar = (int) valores[LIMITE9];
                Grafico.output(dados[usar][LIMITE1], Integer.parseInt(dados[usar][LIMITE3]), dados[usar][LIMITE2].charAt(LIMITE0), (int) valores[LIMITE0], (int) valores[LIMITE1], valores[LIMITE2], valores[LIMITE3], (int) valores[LIMITE4], (int) valores[LIMITE5], (int) valores[LIMITE6], valores[LIMITE7], (int) valores[LIMITE8]);
            }
        } else {
            System.out.println("Não existem pacientes.");
        }
    }

    //elimina todas as linhas do ficheiro que têm o código introduzido pelo utilizador
    public static void eliminarPaciente(String[][] dados) throws FileNotFoundException {
        final int LIMITE0 = 0, LIMITE1 = 1;
        final int LIMITE100000 = 100000;
        final int LIMITE999999 = 999999;
        int codigo, usar = LIMITE0, linhas = LIMITE0;
        boolean zero = false, fail = true;
        File file = new File(ficheiro);
        Scanner ler = new Scanner(file);
        if (dados[LIMITE0][LIMITE0] != null) {
            codigo = lerInt("Introduza o código do paciente: ", LIMITE100000, LIMITE999999, zero);
            int length = dados.length;
            for (int i = LIMITE0; i < length && fail; i++) {
                if (codigo == Integer.parseInt(dados[i][LIMITE0])) {
                    fail = false;
                } else {
                    fail = true;
                }
            }
            if (!fail) {
                for (int i = LIMITE0; i < length; i++) {
                    if (codigo == Integer.parseInt(dados[i][LIMITE0])) {
                        usar++;
                    }
                }
                int[] usos = new int[usar];
                int v = LIMITE0;
                for (int i = LIMITE0; i < length; i++) {
                    if (codigo == Integer.parseInt(dados[i][LIMITE0])) {
                        usos[v] = i + LIMITE1;
                        v++;
                    }
                }
                while (ler.hasNextLine()) {
                    linhas++;
                    ler.nextLine();
                }
                ler.close();
                ler = new Scanner(file);
                String temp = "";
                for (int i = LIMITE1; i <= linhas; i++) {
                    boolean flag = true;
                    for (int j = LIMITE0; j < usar && flag; j++) {
                        if (i == usos[j]) {
                            flag = false;
                        } else {
                            flag = true;
                        }
                    }
                    if (flag) {
                        temp += String.format("%s\n", ler.nextLine());
                    } else {
                        ler.nextLine();
                    }
                }

                Formatter formatter = new Formatter(file);
                formatter.format(temp);
                formatter.close();
                ler.close();
            } else {
                System.out.println("Código não encontrado.");
            }
        } else {
            System.out.println("Não existem pacientes.");
        }
    }

    //adiciona a um paciente existente novos valores usando o código introduzido pelo utilizador
    public static void adicionarValoresPacienteExistente(String[][] dados, float[][] sinais) throws IOException {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3;
        final int LIMITE30 = 30;
        final int LIMITE45 = 45;
        final int LIMITE70 = 70;
        final int LIMITE100 = 100;
        final int LIMITE250 = 250;
        final int LIMITE100000 = 100000;
        final int LIMITE999999 = 999999;
        int codigo, usar = LIMITE0;
        boolean zero = false, fail = true;
        if (dados[LIMITE0][LIMITE0] != null) {
            codigo = lerInt("Introduza o código do paciente: ", LIMITE100000, LIMITE999999, zero);
            int length = dados.length;
            for (int i = LIMITE0; i < length && fail; i++) {
                if (codigo == Integer.parseInt(dados[i][LIMITE0])) {
                    usar = i;
                    fail = false;
                } else {
                    fail = true;
                }
            }
            int Bpm = lerInt("Introduza o valor de bpm : ", LIMITE30, LIMITE250, zero);
            float Temp = lerFloat("Introduza o valor da temperatura: ", LIMITE30, LIMITE45, zero);
            int Sat = lerInt("Introduza o valor de saturação de O2: ", LIMITE70, LIMITE100, zero);
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            String formattedDateTime = currentDateTime.format(formatter);
            dados = dadosPaciente(dados, Integer.parseInt(dados[usar][LIMITE0]), dados[usar][LIMITE1], dados[usar][LIMITE2].charAt(LIMITE0), Integer.parseInt(dados[usar][LIMITE3]), formattedDateTime);
            sinais = sinaisPaciente(sinais, Bpm, Temp, Sat);
            guardarFicheiro(dados, sinais);
        } else {
            System.out.println("Não existem pacientes.");
        }
    }

    //cria o mínimo média e máximo dos valores dos sinais de um paciente
    public static float[] procurarPacientes(String[][] dados, float[][] sinais) {
        final int LIMITE0 = 0, LIMITE1 = 1, LIMITE2 = 2, LIMITE3 = 3, LIMITE4 = 4, LIMITE5 = 5, LIMITE6 = 6, LIMITE7 = 7, LIMITE8 = 8, LIMITE9 = 9, LIMITE10 = 10, LIMITEM1 = -1, LIMITE300 = 300;
        final int LIMITE100000 = 100000;
        final int LIMITE999999 = 999999;
        int codigo, verifica = LIMITE0, bpm, sat, bpmMax = LIMITEM1, bpmMin = LIMITE300, satMax = LIMITEM1, satMin = LIMITE300, bpmAcc = LIMITE0, satAcc = LIMITE0;
        float temp, tempMax = LIMITEM1, tempMin = LIMITE300, tempAcc = LIMITE0, usar = LIMITEM1;
        boolean zero = false, fail = true;
        codigo = lerInt("Introduza o código do paciente: ", LIMITE100000, LIMITE999999, zero);
        int length = dados.length;
        for (int i = LIMITE0; i < length && fail; i++) {
            if (codigo == Integer.parseInt(dados[i][LIMITE0])) {
                verifica++;
                bpm = (int) sinais[i][LIMITE0];
                bpmAcc += bpm;
                temp = sinais[i][LIMITE1];
                tempAcc += temp;
                sat = (int) sinais[i][LIMITE2];
                satAcc += sat;
                if (bpm > bpmMax) bpmMax = bpm;
                if (bpm < bpmMin) bpmMin = bpm;
                if (temp > tempMax) tempMax = temp;
                if (temp < tempMin) tempMin = temp;
                if (sat > satMax) satMax = sat;
                if (sat < satMin) satMin = sat;
                usar = i;
            }
        }
        float[] novosSinais;
        if (verifica != LIMITE0) {
            bpm = bpmAcc / verifica;
            temp = tempAcc / verifica;
            sat = satAcc / verifica;
            novosSinais = new float[LIMITE10];
            novosSinais[LIMITE0] = bpmMin;
            novosSinais[LIMITE1] = bpmMax;
            novosSinais[LIMITE2] = tempMin;
            novosSinais[LIMITE3] = tempMax;
            novosSinais[LIMITE4] = satMin;
            novosSinais[LIMITE5] = satMax;
            novosSinais[LIMITE6] = bpm;
            novosSinais[LIMITE7] = temp;
            novosSinais[LIMITE8] = sat;
            novosSinais[LIMITE9] = usar;
        } else {
            System.out.println("Código de paciente não encontrado! ");
            novosSinais = new float[LIMITE10];
            novosSinais[LIMITE9] = usar;
        }

        return novosSinais;
    }

    //recebe um Array de Strings e devolve um que não tem valores repetidos
    public static String[] semRepetirStrings(String[] valores) {
        final int LIMITE0 = 0, LIMITE1 = 1;
        int cont = LIMITE0, length = valores.length;
        String[] temp = valores;
        for (int i = LIMITE0; i < length; i++) {
            for (int j = i + LIMITE1; j < length; j++) {
                if (temp[i].equals(temp[j])) {
                    temp[j] = "";
                }
            }
        }

        for (int i = LIMITE0; i < length; i++) {
            if (!temp[i].isEmpty()) {
                cont++;
            }
        }

        String[] temp2 = new String[cont];
        int v = LIMITE0;
        for (int i = LIMITE0; i < length; i++) {
            if (!temp[i].isEmpty()) {
                temp2[v] = temp[i];
                v++;
            }
        }
        return temp2;
    }

    //recebe um Array de Inteiros e devolve um que não tem valores repetidos
    public static int[] semRepetirInteiros(int[] valores) {
        final int LIMITE0 = 0, LIMITE1 = 1;
        int cont = LIMITE0, length = valores.length;
        int[] temp = valores;
        for (int i = LIMITE0; i < length; i++) {
            for (int j = i + LIMITE1; j < length; j++) {
                if (temp[i] == temp[j]) {
                    temp[j] = LIMITE0;
                }
            }
        }

        for (int i = LIMITE0; i < length; i++) {
            if (temp[i] != LIMITE0) {
                cont++;
            }
        }

        int[] temp2 = new int[cont];
        int v = LIMITE0;
        for (int i = LIMITE0; i < length; i++) {
            if (temp[i] != LIMITE0) {
                temp2[v] = temp[i];
                v++;
            }
        }
        return temp2;
    }
}