package generatore;


import java.security.SecureRandom;
import java.util.InputMismatchException;
import java.util.Scanner;

public class generaPassword {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";


    static final char[] alfabeto = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'w', 'x', 'y', 'z'
    };
    static final char[] simboli = new char[]{
            ')', '~', '`', '!', '@', '#', '$', '^', '&', '*', '(', ')', '_', '-', '+', '=', '{', '[', '}', ']', '|', ':', ';', '"', '<', ',', '>', '.', '?'
    };

    public static void main(String[] args) {
    	int nPassword = inputIntero(ANSI_GREEN + "Digita quantità password richieste" + ANSI_RESET);
        caratteristichePassword(inputIntero(ANSI_GREEN + "Digita lunghezza password"), nPassword);
    }

    private static void caratteristichePassword(int lunghezzaPassword, int nPassword) {
        System.out.println(ANSI_GREEN + "Digita prima la percentuale delle lettere poi la percentuale dei simboli e il rimarente sarà la percentuale dei numeri.");
        int percentualeLettere = restringiTra(inputIntero(ANSI_GREEN + "Digita percentuale possibilità uscita lettere  (0-100)"), 0, 100);
        int percentualeSimoboli = restringiTra(inputIntero(ANSI_GREEN + "Digita percentuale possibilità uscita simboli (0-" + (100-percentualeLettere) + ")"),percentualeLettere,100);
        System.out.println(ANSI_GREEN + "Rimanente possibilità uscita numeri = " + (100-(percentualeLettere+percentualeSimoboli)));
        System.out.println(ANSI_YELLOW + "Password : ");
        for (int i = 0; i < nPassword; i++) {
            generaPassword(percentualeLettere, percentualeSimoboli, lunghezzaPassword);
        }
    }
    
    private static void generaPassword(int percentualeLettere, int percentualeSimoboli, int lunghezzaPassword) {
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < lunghezzaPassword; i++)
            carattereCasuale(passwordBuilder, percentualeLettere, percentualeLettere + percentualeSimoboli);

        System.out.println(ANSI_PURPLE + passwordBuilder + ANSI_RESET);
    }
    
    private static void carattereCasuale(StringBuilder passwordBuilder, int percentualeLettere, int percentualeSimboli) {
        int percentuale = new SecureRandom().nextInt(101);

        if (percentuale < percentualeLettere + 1)
            passwordBuilder.append(percentuale < (percentualeLettere / 2) + 1 ? alfabeto[new SecureRandom().nextInt(alfabeto.length)] : Character.toUpperCase(alfabeto[new SecureRandom().nextInt(alfabeto.length)]));
        else if (percentuale < percentualeSimboli + 1)
            passwordBuilder.append(simboli[new SecureRandom().nextInt(simboli.length)]);
        else
            passwordBuilder.append(new SecureRandom().nextInt(10));

    }

    private static int inputIntero(String prefisso) {

        System.out.print((!prefisso.equals("") ? prefisso + ": " : "") + ANSI_RESET);

        int output;

        Scanner input = new Scanner(System.in);

        try {
            output = input.nextInt();
        } catch (InputMismatchException e) {
            output = inputIntero(ANSI_RED + "Tipo input errato riprovare" );
        }
        input.close();
        return output;
    }

    public static int restringiTra(int value, int min, int max)
    {
        return (value < min) ? min : (value > max) ? max : value ;
    }

}
