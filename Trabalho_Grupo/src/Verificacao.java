import java.util.Scanner;

public class Verificacao {
    //verifica se o valor introduzido é inteiro
    public static int inteiro(String texto) {
        System.out.print(texto);
        Scanner entrada = new Scanner(System.in);
        String temporario;
        int valor = -1, falhou;

        do {
            temporario = entrada.nextLine();
            if (temporario.isEmpty()) {
                System.out.print("Valor não inserido. " + texto);
                falhou = 1;
            } else {
                try {
                    valor = Integer.parseInt(temporario);
                    falhou = 0;
                } catch (NumberFormatException e) {
                    System.out.print("Input inválido. " + texto);
                    falhou = 1;
                }
            }
        } while (falhou == 1);
        return valor;
    }

    //Verifica se o valor inteiro está delimitado entre um limite Inferior e Superior
    public static boolean limitesInteiros(int valor, int limiteI, int limiteS, boolean zero) {
        if (valor == 0 && zero) {
            return true;
        } else if (valor < limiteI || valor > limiteS) {
            System.out.println("Valor Inválido.");
            return false;
        } else return true;
    }

    //verifica se o String introduido é válido (m ou f)
    public static char genero(String texto) {
        System.out.print(texto);
        Scanner entrada = new Scanner(System.in);
        String temporario;
        char sexo = 'n';
        int verificaGenero = 0;
        do {
            temporario = entrada.nextLine().toLowerCase();
            if (temporario.length() == 1) {
                sexo = temporario.charAt(0);
                if (sexo == 'f') {
                    verificaGenero++;
                } else if (sexo == 'm') {
                    verificaGenero++;
                }
                if (verificaGenero == 0) {
                    System.out.print("Aviso género inválido. Digite a género do paciente (m/f): ");
                }
            } else System.out.print("Aviso género inválido. Digite a género do paciente (m/f): ");
        } while (verificaGenero == 0);
        return sexo;
    }

    //Introdução, delimitação e tratamento dos valores de Bpm (cálculo do valor mínimo, máximo e da média)
    public static void tratamentoVarBpm() {
        boolean teste;
        final int LIMITEm1 = -1;
        final int LIMITE0 = 0;
        final int LIMITE30 = 30;
        final int LIMITE250 = 250;
        final int LIMITE400 = 400;
        Main.bpm = LIMITE0;
        Main.cont = LIMITE0;
        Main.acc = LIMITE0;
        Main.bpmMin = LIMITE400;
        Main.bpmMax = LIMITEm1;
        String mensagem = "Digite o valor de bpm: ";
        do {
            do {
                do {
                    Main.bpm = Verificacao.inteiro(mensagem);
                    teste = Verificacao.limitesInteiros(Main.bpm, LIMITE30, LIMITE250, true);
                } while (!teste);
                if (Main.bpm != LIMITE0) {
                    Main.cont++;
                    Main.acc += Main.bpm;
                    if (Main.bpm > Main.bpmMax) Main.bpmMax = Main.bpm;
                    if (Main.bpm < Main.bpmMin) Main.bpmMin = Main.bpm;
                }
            } while (Main.bpm != LIMITE0);
            if (Main.cont == LIMITE0) {
                System.out.println("Nenhum valor inserido.");
            }
        } while (Main.cont == LIMITE0);
        Main.bpmAvg = (int) (Main.acc / Main.cont);
    }

    //Introdução, delimitação e tratamento dos valores de Temperatura (cálculo do valor mínimo, máximo e da média)
    public static void tratamentoVarTemp() {
        boolean teste;
        final int LIMITEm1 = -1;
        final int LIMITE0 = 0;
        final int LIMITE30 = 30;
        final int LIMITE45 = 45;
        final int LIMITE400 = 400;
        Main.temp = LIMITE0;
        Main.cont = LIMITE0;
        Main.acc = LIMITE0;
        Main.tempMin = LIMITE400;
        Main.tempMax = LIMITEm1;
        String mensagem = "Digite a temperatura (ºC): ";
        do {
            do {
                do {
                    Main.temp = Verificacao.Float(mensagem);
                    teste = Verificacao.limitesFloat(Main.temp, LIMITE30, LIMITE45, true);
                } while (!teste);
                if (Main.temp != LIMITE0) {
                    Main.cont++;
                    Main.acc += Main.temp;
                    if (Main.temp > Main.tempMax) Main.tempMax = Main.temp;
                    if (Main.temp < Main.tempMin) Main.tempMin = Main.temp;
                }
            } while (Main.temp != LIMITE0);
            if (Main.cont == LIMITE0) {
                System.out.println("Nenhum valor inserido.");
            }
        } while (Main.cont == LIMITE0);
        Main.tempAvg = (float) (Main.acc / Main.cont);
    }

    //verifica se o valor introduzido é real
    public static float Float(String texto) {
        System.out.print(texto);
        Scanner entrada = new Scanner(System.in);
        String temporario;
        float valor = -1;
        int falhou;

        do {
            temporario = entrada.nextLine();
            if (temporario.isEmpty()) {
                System.out.print("Valor não inserido. " + texto);
                falhou = 1;
            } else {
                try {
                    valor = Float.parseFloat(temporario);
                    falhou = 0;
                } catch (NumberFormatException e) {
                    System.out.print("Input inválido. " + texto);
                    falhou = 1;
                }
            }
        } while (falhou == 1);
        return valor;
    }

    //verifica se o valor real está delimitado entre um limite Inferior e Superior
    public static boolean limitesFloat(float valor, int limiteI, int limiteS, boolean zero) {
        if (valor == 0 && zero) {
            return true;
        } else if (valor < limiteI || valor > limiteS) {
            System.out.println("Valor Inválido.");
            return false;
        } else return true;
    }

    //Introdução, delimitação e tratamento dos valores de Saturação do O2 (cálculo do valor mínimo, máximo e da média)
    public static void tratamentoVarSat() {
        boolean teste;
        final int LIMITEm1 = -1;
        final int LIMITE0 = 0;
        final int LIMITE70 = 70;
        final int LIMITE100 = 100;
        final int LIMITE400 = 400;
        Main.sat = LIMITE0;
        Main.cont = LIMITE0;
        Main.acc = LIMITE0;
        Main.satMin = LIMITE400;
        Main.satMax = LIMITEm1;
        String mensagem = "Digite a saturação de O2 (%): ";
        do {
            do {
                do {
                    Main.sat = Verificacao.inteiro(mensagem);
                    teste = Verificacao.limitesInteiros(Main.sat, LIMITE70, LIMITE100, true);
                } while (!teste);
                if (Main.sat != LIMITE0) {
                    Main.cont++;
                    Main.acc += Main.sat;
                    if (Main.sat > Main.satMax) Main.satMax = Main.sat;
                    if (Main.sat < Main.satMin) Main.satMin = Main.sat;
                }
            } while (Main.sat != LIMITE0);
            if (Main.cont == LIMITE0) {
                System.out.println("Nenhum valor inserido.");
            }
        } while (Main.cont == LIMITE0);
        Main.satAvg = (int) (Main.acc / Main.cont);
    }

    //Escolha dos limites (Normal, Atenção e Crítico) a serem utilizados para os valores de Bpm dependendo da idade e do género do paciente
    public static void limitesBpmIdade(char sexo, int idade) {
        final int LIMITE25 = 25;
        final int LIMITE35 = 35;
        final int LIMITE45 = 45;
        final int LIMITE55 = 55, LIMITE56 = 56, LIMITE57 = 57, LIMITE58 = 58;
        final int LIMITE60 = 60, LIMITE61 = 61, LIMITE65 = 65;
        final int LIMITE73 = 73, LIMITE74 = 74, LIMITE75 = 75, LIMITE76 = 76, LIMITE77 = 77, LIMITE78 = 78;
        final int LIMITE80 = 80, LIMITE82 = 82, LIMITE83 = 83, LIMITE84 = 84, LIMITE85 = 85;
        if (sexo == 'm') {
            if (idade <= LIMITE25) {
                Main.limiteN = LIMITE56;
                Main.limiteA = LIMITE73;
                Main.limiteC = LIMITE82;
            } else if (idade <= LIMITE35) {
                Main.limiteN = LIMITE55;
                Main.limiteA = LIMITE74;
                Main.limiteC = LIMITE82;
            } else if (idade <= LIMITE45) {
                Main.limiteN = LIMITE57;
                Main.limiteA = LIMITE75;
                Main.limiteC = LIMITE83;
            } else if (idade <= LIMITE55) {
                Main.limiteN = LIMITE58;
                Main.limiteA = LIMITE76;
                Main.limiteC = LIMITE84;
            } else if (idade <= LIMITE65) {
                Main.limiteN = LIMITE57;
                Main.limiteA = LIMITE75;
                Main.limiteC = LIMITE82;
            } else {
                Main.limiteN = LIMITE56;
                Main.limiteA = LIMITE73;
                Main.limiteC = LIMITE80;
            }
        } else {
            if (idade <= LIMITE25) {
                Main.limiteN = LIMITE61;
                Main.limiteA = LIMITE78;
                Main.limiteC = LIMITE85;
            } else if (idade <= LIMITE35) {
                Main.limiteN = LIMITE60;
                Main.limiteA = LIMITE76;
                Main.limiteC = LIMITE83;
            } else if (idade <= LIMITE45) {
                Main.limiteN = LIMITE60;
                Main.limiteA = LIMITE78;
                Main.limiteC = LIMITE85;
            } else if (idade <= LIMITE55) {
                Main.limiteN = LIMITE61;
                Main.limiteA = LIMITE77;
                Main.limiteC = LIMITE84;
            } else if (idade <= LIMITE65) {
                Main.limiteN = LIMITE60;
                Main.limiteA = LIMITE77;
                Main.limiteC = LIMITE84;
            } else {
                Main.limiteN = LIMITE60;
                Main.limiteA = LIMITE76;
                Main.limiteC = LIMITE84;
            }
        }

    }
}