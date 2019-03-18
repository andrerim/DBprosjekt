import java.util.Scanner;

public class Main {


    public void user_input_controller(){
        Scanner bruker_input = new Scanner(System.in);
        nyInput();
        String input_from_user = bruker_input.nextLine();

        while (!input_from_user.equals("-1")){
            switch (input_from_user.toLowerCase()) {
                case "registrer økt": registrerØkt();
                break;
                case "hent økter": readØkt();
                break;
                case "registrer apparat": registrerApparat();
                break;
                case "registrer øvelse": registrerØvelse();
                break;
            }
            nyInput();
            input_from_user = bruker_input.nextLine();
        }
    }

    private void registrerØkt() {
        RegOekt regOekt = new RegOekt();
        regOekt.connect();
        regOekt.registrerOekt();
    }
    private void readØkt() {
        RegOekt regOekt = new RegOekt();
        regOekt.connect();
        regOekt.printOekt();
    }
    private void registrerApparat() {
        ApparatHandler apparat = new ApparatHandler();
        apparat.connect();
        apparat.registerApparat();
    }
    public void registrerØvelse() {
        OvelseHandler ovelse = new OvelseHandler();
        ovelse.connect();
        ovelse.registrerOvelse();
    }

    public void nyInput() {
        System.out.println("What do yo want to do?");
        System.out.println("Registrer økt / Hent økter / Registrer apparat / Registrer øvelse (-1 to exit)");
    }
    public void user_input_interpeter(String user_input){

    }

    public static void main(String[] args){
        Main main = new Main();
        main.user_input_controller();
        System.out.println("Program terminated");
    }
}
