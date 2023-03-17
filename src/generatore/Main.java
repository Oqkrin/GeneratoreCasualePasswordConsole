package generatore;


import java.security.SecureRandom;
import java.util.*;

public class Main {

    static final char[] alfabeto = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'w', 'x', 'y', 'z'};
    static final char[] simboli = new char[]{')', '~', '`', '!', '@', '#', '$', '^', '&', '*', '(', ')', '_', '-', '+', '=', '{', '[', '}', ']', '|', ':', ';', '"', '<', ',', '>', '.', '?'};


    public static void main(String[] args) {

        int nPassword = inputInt("\nDigita quantità password richieste");

        int percentualeLettere = restringiTra(inputInt("\nDigita, in percentuale, la possibilità  di uscita di lettere  (0-100)"), 0, 100);
        int percentualeSimboli = restringiTra(inputInt("Digita, in percentuale, la possibilità di uscita di simboli (0-" + (100-percentualeLettere) + ")"),0,100-percentualeLettere);
        System.out.println("Rimanente possibilità uscita numeri = " + (100-(percentualeLettere+percentualeSimboli)));

        int lunghezzaPassword = inputInt("\nDigita lunghezza passwords");

        System.out.println("\nLista Password :\n");

        String[] passwords = new String[nPassword];
        for (int i = 0; i < nPassword; i++) {
            passwords[i] = generaPassword(percentualeLettere, percentualeSimboli, lunghezzaPassword);
        }
        for(String output : controlloDuplicati(passwords, percentualeLettere, percentualeSimboli))
            System.out.println(output);

        if(inputInt("\nDigita 0 per interrompere") != 0)
            main(null);
    }

    private static ArrayList<String> controlloDuplicati(String[] arrayDaControllare, int percentualeLettere, int percentualeSimboli) {

        ArrayList<String> passwordsDistinte = new ArrayList<>(Arrays.stream(arrayDaControllare).distinct().toList());

        if (arrayDaControllare.length != passwordsDistinte.size()) {
            for (int i = 0; i < arrayDaControllare.length - passwordsDistinte.size(); i++) {
                passwordsDistinte.add(generaPassword(percentualeLettere, percentualeSimboli, arrayDaControllare.length));
            }
            controlloDuplicati(passwordsDistinte.toArray(new String[0]), percentualeLettere, percentualeSimboli);
        }
        return passwordsDistinte;
    }

    private static String generaPassword(int percentualeLettere, int percentualeSimboli, int lunghezzaPassword) {
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < lunghezzaPassword; i++)
            carattereCasuale(passwordBuilder, percentualeLettere, percentualeLettere + percentualeSimboli);

        return passwordBuilder.toString();
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

    public static int restringiTra(int value, int min, int max) {
        return Math.min(max,Math.max(value, min));
    }

    private static int inputInt(String prefisso) {

        System.out.print((!prefisso.equals("") ? prefisso + ": " : ""));

        int output;

        try {
            output = new Scanner(System.in).nextInt();
        } catch (InputMismatchException e) {
            output = inputInt("Tipo input errato riprovare" );
        }

        return output;
    }

}
