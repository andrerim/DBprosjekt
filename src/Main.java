import java.util.Scanner;

public class Main {


    public void user_input_controller(){
        Scanner bruker_input = new Scanner(System.in);

        System.out.println("What do yo want to do?: (-1 to exit) ");
        String input_from_user = bruker_input.nextLine();

        while (!input_from_user.equals("-1")){
            if (input_from_user.equals("Registrer oekt")){
                RegOekt regOekt = new RegOekt();
                regOekt.connect();
                regOekt.registrerOekt();
            } else if (input_from_user.equals("Read oekt")){
                RegOekt regOekt = new RegOekt();
                regOekt.connect();
                regOekt.printOekt();
            } else if (input_from_user.equals("Registrer apparat")){
                ApparatHandler apparat = new ApparatHandler();
                apparat.connect();
                apparat.registerApparat();
            }

            System.out.println("What do yo want to do?: ");
            input_from_user = bruker_input.nextLine();
        }
    }

    public void user_input_interpeter(String user_input){

    }

    public static void main(String[] args){
        Main main = new Main();
        main.user_input_controller();
        System.out.println("Program terminated");

    }
}
