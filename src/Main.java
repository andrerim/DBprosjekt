import java.util.Scanner;

public class Main {


    public void user_input_controller(){
        Scanner bruker_input = new Scanner(System.in);
        nyInput();
        String input_from_user = bruker_input.nextLine();
        int test = 1;
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
                case "se resultatlogg" : seResultater();
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

    public void seResultater(){
        OvelseHandler ovelse = new OvelseHandler();
        ovelse.connect();
        ovelse.resultatlogg();
    }
    public void nyInput() {
        System.out.println("What do yo want to do?");
        System.out.println("Registrer økt / Hent økter / Registrer apparat / Registrer øvelse / Se resultatlogg(-1 to exit)");
    }
    public void user_input_interpeter(String user_input){

    }

    public static void main(String[] args){
        Main main = new Main();
       // PrintFromDB p = new PrintFromDB();
       // p.connect();
       // p.printResultFromQuery("SELECT * FROM økt");

        main.user_input_controller();
        System.out.println("Program terminated");
    }
}
