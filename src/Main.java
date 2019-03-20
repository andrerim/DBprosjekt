import java.util.Scanner;

public class Main {


    private void user_input_controller(){
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
                case "registrer gruppe": registrerGruppe();
                break;
                case "se resultatlogg" : seResultater();
                break;
                case "legg til øvelse i gruppe": leggTilOvelseIGruppe();
                break;
                case "hent gruppe": hentGruppe();
                break;
                case "hent økter med god prestasjon": hentGodPrestasjon();
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
    private void registrerØvelse() {
        OvelseHandler ovelse = new OvelseHandler();
        ovelse.connect();
        ovelse.registrerOvelse();
    }
    private void registrerGruppe() {
        GruppeHandler gruppe = new GruppeHandler();
        gruppe.connect();
        gruppe.registrerGruppe();
    }
    private void leggTilOvelseIGruppe() {
        GruppeHandler gruppe = new GruppeHandler();
        gruppe.connect();
        gruppe.leggTilOvelseIGruppe();
    }
    private void hentGruppe() {
        GruppeHandler gruppe = new GruppeHandler();
        gruppe.connect();
        gruppe.hentGruppe();
    }

    private void seResultater(){
        OvelseHandler ovelse = new OvelseHandler();
        ovelse.connect();
        ovelse.resultatlogg();
    }
    private void nyInput() {
        System.out.println("Hva ønsker du å gjøre?");
        System.out.println("Registrer økt / Hent økter / Registrer apparat / Registrer øvelse / " +
                "Se resultatlogg");
        System.out.println("/ Registrer gruppe / legg til øvelse i gruppe / hent gruppe / " +
                "hent økter med god prestasjon (-1 to exit)");
    }
    private void hentGodPrestasjon() {
        RegOekt regOekt = new RegOekt();
        regOekt.connect();
        regOekt.printOekt2();
    }

    public static void main(String[] args){
        Main main = new Main();
       //PrintFromDB p = new PrintFromDB();
       // p.connect();
        //p.printResultFromQuery("SELECT * FROM økt");


        main.user_input_controller();
        System.out.println("Applikasjonen er avsluttet");
    }
}
