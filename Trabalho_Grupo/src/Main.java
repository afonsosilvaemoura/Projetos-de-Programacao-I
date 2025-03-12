import java.util.Scanner;

public class Main {

    //Cores usadas no gráfico
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    //Variáveis usadas no tratamento de valores e seu output
    public static int bpm, sat, bpmMax, bpmMin, satMax, satMin, bpmAvg, satAvg, limiteN, limiteA, limiteC;
    public static float temp, tempMax, tempMin, tempAvg, cont, acc;

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        //Declaração das variáveis
        int nPacientes, idade;
        String nome, mensagem;
        char sexo;
        boolean teste;
        //declaração das constantes
        final int LIMITE0 = 0, LIMITE1 = 1;
        final int LIMITE18 = 18;
        final int LIMITE130 = 130;
        final int LIMITE400 = 400;

        //Introdução do nº de pacientes
        mensagem = "Digite o número de pacientes: ";
        do {
            nPacientes = Verificacao.inteiro(mensagem);
            teste = Verificacao.limitesInteiros(nPacientes, LIMITE1, LIMITE400, false);
        } while (!teste);
        for (int i = LIMITE0; i < nPacientes; i++) {
            //Introdução da informação dos paicentes (nome, idade, género)
            System.out.print("Digite o nome do paciente: ");
            nome = teclado.next();
            mensagem = "Digite a idade do paciente (18+): ";
            do {
                idade = Verificacao.inteiro(mensagem);
                teste = Verificacao.limitesInteiros(idade, LIMITE18, LIMITE130, false);
            } while (!teste);
            mensagem = "Digite o género do paciente (m/f): ";
            sexo = Verificacao.genero(mensagem);
            //Introdução e tratamento dos valores de Bpm
            Verificacao.tratamentoVarBpm();
            //Introdução e tratamento dos valores de temperatura
            Verificacao.tratamentoVarTemp();
            //Introdução e tratamento dos valores de saturação de O2
            Verificacao.tratamentoVarSat();
            //Escolha dos limites de Bpm dependendo do género e idade
            Verificacao.limitesBpmIdade(sexo, idade);

            //Output dos resultados
            Grafico.output(nome, idade, sexo, bpmMin, bpmMax, tempMin, tempMax, satMin, satMax, bpmAvg, tempAvg, satAvg, limiteN, limiteA, limiteC);
        }
    }
}