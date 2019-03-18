import java.util.Scanner;

public class Main {


    public void user_input_controller(){
        Scanner bruker_input = new Scanner(System.in);
        nyInput();
        String input_from_user = bruker_input.nextLine();

        while (!input_from_user.equals("-1")){
            if (input_from_user.equals("Registrer økt")){
                RegOekt regOekt = new RegOekt();
                regOekt.connect();
                regOekt.registrerOekt();
            } else if (input_from_user.equals("Read økt")){
                RegOekt regOekt = new RegOekt();
                regOekt.connect();
                regOekt.printOekt();
            } else if (input_from_user.equals("Registrer apparat")){
                ApparatHandler apparat = new ApparatHandler();
                apparat.connect();
                apparat.registerApparat();
            } else if (input_from_user.equals("Registrer øvelse")){
                OvelseHandler ovelse = new OvelseHandler();
                ovelse.connect();
                ovelse.registrerOvelse();
            }


            nyInput();
            input_from_user = bruker_input.nextLine();
        }
    }
    
    public void nyInput() {
        System.out.println("What do yo want to do?");
        System.out.println("Registrer økt / Read økt / Registrer apparat / Registrer øvelse (-1 to exit)");
    }
    public void user_input_interpeter(String user_input){

    }

    public static void main(String[] args){
        Main main = new Main();
        main.user_input_controller();
        System.out.println("Program terminated");

    }
}
