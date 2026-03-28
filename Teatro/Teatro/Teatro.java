import java.util.ArrayList;
import java.util.Scanner;

class Sala {
    String nome;
    String espetaculo;
    double precoInteiro;
    char[][] cadeiras = new char[12][12];

    Sala(String nome, String espetaculo, double precoInteiro){
        this.nome = nome;
        this.espetaculo = espetaculo;
        this.precoInteiro = precoInteiro;
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){
                cadeiras[i][j] = 'L';
            }
        }
    }

    void mostrar(){
        System.out.println("\nSALA: " + nome);
        System.out.println("ESPETÁCULO: " + espetaculo);

        System.out.print("   ");
        for(int i = 1; i <= 12; i++)
            System.out.printf("%4d", i);
        System.out.println();

        for(int i = 0; i < 12; i++){
            char linha = (char)('A' + i);
            System.out.print(linha + " ");
            for(int j = 0; j < 12; j++){
                char c = cadeiras[i][j];
                String s = c == 'L' ? "[ ]" : c == 'R' ? "[R]" : "[X]";
                System.out.print(" " + s);
            }
            System.out.println();
        }
    }
 
    void reservar(int lin, int col){
        if(cadeiras[lin][col] == 'L'){
            cadeiras[lin][col] = 'R';
            System.out.println("Reserva realizada!");
        } else {
            System.out.println("Cadeira indisponível!");
        }
    }

    void comprar(int lin, int col){
        if(cadeiras[lin][col] == 'X'){
            System.out.println("Cadeira já ocupada!");
            return;
        }
        cadeiras[lin][col] = 'X';
        System.out.println("Compra realizada!");
    }

    void cancelar(int lin, int col){
        if(cadeiras[lin][col] == 'R'){
            cadeiras[lin][col] = 'L';
            System.out.println("Reserva cancelada!");
        } else {
            System.out.println("Não há reserva nessa cadeira!");
        }
    }

    int contarLivres(){
        int c = 0;
        for(int i = 0; i < 12; i++)
            for(int j = 0; j < 12 ; j++)
                if(cadeiras[i][j] == 'L') c++;
        return c;
    }

    int contarReservadas(){
        int c = 0;
        for(int i = 0; i < 12; i++)
            for(int j = 0; j < 12; j++)
                if(cadeiras[i][j] == 'R') c++;
        return c;
    }

    int contarOcupadas(){
        int c = 0;
        for(int i = 0; i < 12; i++)
            for(int j = 0; j < 12; j++)
                if(cadeiras[i][j] == 'X') c++;
        return c;
    }

    double calcularArrecadado(){
        return contarOcupadas() * precoInteiro;
    }

    double calcularReservas(){
        return contarReservadas() * (precoInteiro / 2);
    }
}

public class Teatro {
    static Scanner teclado = new Scanner(System.in);
    static ArrayList<Sala> salas = new ArrayList<>();

    public static void main(String[] args) {
        int op;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Cadastrar Sala");
            System.out.println("2 - Mostrar Sala");
            System.out.println("3 - Reservar Cadeira");
            System.out.println("4 - Comprar Cadeira");
            System.out.println("5 - Cancelar Reserva");
            System.out.println("6 - Relatório Financeiro");
            System.out.println("7 - Menu de Exercícios");
            System.out.println("0 - Sair");
            System.out.print("Digite sua opcao: ");
            op = teclado.nextInt();
            teclado.nextLine(); // limpar buffer

            switch(op){
                case 1: cadastrarSala(); break;
                case 2: mostrarSala(); break;
                case 3: operarCadeira("reservar"); break;
                case 4: operarCadeira("comprar"); break;
                case 5: operarCadeira("cancelar"); break;
                case 6: relatorio(); break;
                case 7: menuExercicios(); break;
                case 0: System.out.println("Saindo..."); break;
                default: System.out.println("Opção inválida!");
            }

        } while(op != 0);
    }

    static void cadastrarSala(){
        System.out.print("Nome da sala: ");
        String nome = teclado.nextLine();
        System.out.print("Espetáculo: ");
        String esp = teclado.nextLine();
        System.out.print("Preço inteiro: ");
        double preco = teclado.nextDouble();
        teclado.nextLine();

        salas.add(new Sala(nome, esp, preco));
        System.out.println("Sala cadastrada com sucesso!");
    }

    static Sala selecionarSala(){
        if(salas.isEmpty()){
            System.out.println("Nenhuma sala cadastrada!");
            return null;
        }
        System.out.println("Salas disponíveis:");
        for(int i = 0; i < salas.size(); i++){
            System.out.println(i + " - " + salas.get(i).nome);
        }
        System.out.print("Escolha a sala (número): ");
        int idx = teclado.nextInt();
        teclado.nextLine();
        if(idx < 0 || idx >= salas.size()){
            System.out.println("Sala inválida!");
            return null;
        }
        return salas.get(idx);
    }

    static void mostrarSala(){
        Sala s = selecionarSala();
        if(s != null) s.mostrar();
    }

    static void operarCadeira(String acao){
        Sala s = selecionarSala();
        if(s == null) return;

        System.out.print("Linha A-L: ");
        char l = teclado.next().toUpperCase().charAt(0);
        System.out.print("Coluna 1-12: ");
        int c = teclado.nextInt();
        teclado.nextLine();
        int lin = l - 'A';
        int col = c - 1;
        if(lin < 0 || lin > 11 || col < 0 || col > 11){
            System.out.println("Cadeira inválida!");
            return;
        }

        switch(acao){
            case "reservar": s.reservar(lin, col); break;
            case "comprar": s.comprar(lin, col); break;
            case "cancelar": s.cancelar(lin, col); break;
        }
    }

    static void relatorio(){
        Sala s = selecionarSala();
        if(s == null) return;

        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        System.out.println("Livres: " + s.contarLivres());
        System.out.println("Reservadas: " + s.contarReservadas());
        System.out.println("Ocupadas: " + s.contarOcupadas());
        System.out.println("Total arrecadado: R$ " + s.calcularArrecadado());
        System.out.println("Total em reservas: R$ " + s.calcularReservas());
        System.out.println("Receita potencial se reservas confirmadas: R$ " + 
                            (s.calcularArrecadado() + s.calcularReservas()));
        System.out.println("Receita potencial máxima se tudo vendido: R$ " + 
                            (s.calcularArrecadado() + s.calcularReservas() + s.contarLivres()*s.precoInteiro));
    }

static void menuExercicios(){

    String[] titulos = new String[20];
    String[] enunciados = new String[20];

for(int i = 0; i < 20; i++){
    titulos[i] = "";
    enunciados[i] = "";
}

titulos[0] = "Par ou Impar";
enunciados[0] = "Leia um numero inteiro e informe se ele e par ou impar. Obrigatorio: use o operador ternario para exibir a mensagem.";

titulos[1] = "Maior de Dois Numeros";
enunciados[1] = "Leia dois numeros inteiros e exiba qual e o maior. Obrigatorio: use o operador ternario para determinar e exibir o maior.";

titulos[2] = "Aprovacao Escolar";
enunciados[2] = "Leia a media de um aluno (0 a 10) e exiba:\nAprovado se media >= 7\nRecuperacao se media >= 5 e < 7\nReprovado se media < 5";

titulos[3] = "Classificacao de Temperatura";
enunciados[3] = "Leia a temperatura em graus Celsius e classifique:\nAbaixo de 15C -> Frio\nEntre 15C e 25C -> Agradavel\nAcima de 25C -> Quente\nDesafio: tente usar o ternario aninhado para classificar.";

titulos[4] = "Calculadora Simples com Switch";
enunciados[4] = "Leia dois numeros e um operador (+, -, *, /) e realize a operacao correspondente usando switch. Trate a divisao por zero.";

titulos[5] = "Ano Bissexto";
enunciados[5] = "Leia um ano e informe se ele e bissexto ou nao.\nUm ano e bissexto se divisivel por 4, exceto centenarios, salvo os divisiveis por 400.";

titulos[6] = "Triangulo Valido";
enunciados[6] = "Leia tres lados e verifique se formam um triangulo valido. Se sim, classifique como equilatero, isosceles ou escaleno.";

titulos[7] = "IMC";
enunciados[7] = "Leia o peso (kg) e a altura (m) de uma pessoa, calcule o IMC (peso / altura²) e classifique:\nIMC < 18.5 -> Abaixo do peso\n18.5 <= IMC < 25 -> Peso normal\n25 <= IMC < 30 -> Sobrepeso\nIMC >= 30 -> Obesidade\nDesafio: use o ternario para exibir se o IMC esta dentro ou fora da faixa saudavel.";

titulos[8] = "Dia da Semana com Switch";
enunciados[8] = "Leia um numero de 1 a 7 e exiba o nome do dia da semana correspondente usando switch. Para qualquer outro numero, exiba Dia invalido.";

titulos[9] = "Positivo Negativo ou Zero";
enunciados[9] = "Leia um numero e informe se e positivo, negativo ou zero. Obrigatorio: use o operador ternario aninhado.";

titulos[10] = "Ingresso de Cinema";
enunciados[10] = "Uma sala de cinema cobra:\nMenores de 12 anos: R$ 10,00\nEntre 12 e 60 anos: R$ 20,00\nAcima de 60 anos: R$ 10,00 (meia)\nLeia a idade e exiba o valor do ingresso.";

titulos[11] = "Nota por Conceito";
enunciados[11] = "Leia uma nota (0-10) e exiba o conceito:\n9 a 10 -> A\n7 a 8 -> B\n5 a 6 -> C\n0 a 4 -> D";

titulos[12] = "Estacao do Ano com Switch";
enunciados[12] = "Leia o numero do mes (1-12) e exiba a estacao do ano correspondente (considere o hemisferio sul).";

titulos[13] = "Maior de Tres Numeros";
enunciados[13] = "Leia tres numeros inteiros e exiba qual e o maior entre os tres. Trate o caso de empate.";

titulos[14] = "Verificador de Login";
enunciados[14] = "Defina um usuario e senha fixos no codigo. Leia o usuario e a senha digitados e exiba Acesso permitido ou Acesso negado. Obrigatorio: use o operador ternario para exibir a mensagem.";

titulos[15] = "Multiplo de 3 e 5";
enunciados[15] = "Leia um numero inteiro e informe:\nSe e multiplo de 3 e de 5 simultaneamente\nSe e multiplo apenas de 3\nSe e multiplo apenas de 5\nSe nao e multiplo de nenhum\nDesafio: use o ternario onde for possivel.";

titulos[16] = "Desconto em Compra";
enunciados[16] = "Uma loja da desconto conforme o valor da compra:\nAcima de R$ 500 -> 20 por cento de desconto\nEntre R$ 200 e R$ 500 -> 10 por cento de desconto\nAbaixo de R$ 200 -> sem desconto\nLeia o valor e exiba o valor final apos o desconto.";

titulos[17] = "Numero Romano com Switch";
enunciados[17] = "Leia um numero de 1 a 10 e exiba seu equivalente em algarismo romano usando switch.";

titulos[18] = "Velocidade e Multa";
enunciados[18] = "Leia a velocidade de um veiculo e o limite da via. Classifique:\nDentro do limite -> Sem multa\nAte 20 por cento acima -> Multa leve\nEntre 20 por cento e 50 por cento acima -> Multa grave\nAcima de 50 por cento -> Multa gravissima + suspensao";

titulos[19] = "Jogo Pedra Papel e Tesoura";
enunciados[19] = "Leia a escolha de dois jogadores (pedra, papel ou tesoura) e determine quem venceu ou se houve empate usando if else encadeado. Desafio: use o operador ternario para exibir o resultado final (Jogador 1 venceu, Jogador 2 venceu ou Empate).";

    int paginaAtual = 0;
    int itensPorPagina = 7;
    int totalExercicios = 20;
    int totalPaginas = (int)Math.ceil((double) totalExercicios / itensPorPagina);

    String op;

    do{

        int inicio = paginaAtual * itensPorPagina;
        int fim = Math.min(inicio + itensPorPagina, totalExercicios);

        System.out.println("\n=== LISTA DE EXERCÍCIOS ===");
        System.out.println("Página " + (paginaAtual+1) + "/" + totalPaginas);

        for(int i = inicio; i < fim; i++){
            System.out.println((i+1) + " - " + titulos[i]);
        }

        if(paginaAtual > 0)
            System.out.println("A - Página anterior");

        if(paginaAtual < totalPaginas - 1)
            System.out.println("P - Próxima página");

        System.out.println("V - Voltar");
        System.out.print("Escolha: ");

        op = teclado.nextLine().toUpperCase();

        if(op.equals("A") && paginaAtual > 0){
            paginaAtual--;
        }
        else if(op.equals("P") && paginaAtual < totalPaginas - 1){
            paginaAtual++;
        }
        else if(op.equals("V")){
            break;
        }
        else{
            try{
                int num = Integer.parseInt(op);

                if(num >= 1 && num <= totalExercicios){
                    System.out.println("\n=== EXERCÍCIO " + num + " ===");
                    System.out.println(titulos[num - 1]);
                    System.out.println(enunciados[num - 1]);
                    System.out.println("\nPressione ENTER para voltar...");
                    teclado.nextLine();
                }

            }catch(Exception e){
                System.out.println("Opção inválida!");
            }
        }

    }while(true);
}
}