import java.sql.*;
import java.util.Scanner;


public class RegOekt extends DBConn {

    private PreparedStatement register_stmt;


    public void registrerOekt(){
        Scanner bruker_input = new Scanner(System.in);

        String sqlstmt = "INSERT INTO Økt(Dato,Tidspunkt, Varighet, Form, Prestasjon, Notat) "
                + "VALUES(?,?,?,?,?,?)";
        long system_time = System.currentTimeMillis();
        Date current_date = new Date(system_time);
        Time current_time = new Time(system_time);
        try {
            register_stmt = conn.prepareStatement(sqlstmt);
            // Antagelse: Bruker bare nåværende dato, ikke mulig for bruker å skrive inn dato manuelt
            register_stmt.setDate(1, current_date);
            register_stmt.setTime(2, current_time);

            System.out.println("Hvor lenge varte økta? (oppgi antall minutt) ");
            int varighet_oekt = Integer.parseInt(bruker_input.nextLine());
            register_stmt.setInt(3, varighet_oekt);

            System.out.println("Hvordan var formen din? (1-10) ");
            int form_oekt = Integer.parseInt(bruker_input.nextLine());
            register_stmt.setInt(4, form_oekt);

            System.out.println("Hvordan presterte du? (1-10) ");
            int prestasjon_oekt = Integer.parseInt(bruker_input.nextLine());
            register_stmt.setInt(5, prestasjon_oekt);

            System.out.println("Notat om økta (maks 500karakterer) ");
            String notat_oekt = bruker_input.nextLine();
            register_stmt.setString(6, notat_oekt);


            register_stmt.executeUpdate();
            PrintFromDB p = new PrintFromDB();
            p.connect();
            sqlstmt = "SELECT Dato, Tidspunkt, Varighet, Form, Prestasjon, Notat from økt WHERE Økt.ØktID = (select max(ØktID) from økt);";
            System.out.println("Økt registrert!");
            p.printResultFromQuery(sqlstmt);

            System.out.println("Ønsker du å registrere øvelser for økta? (ja/nei)");
            if (bruker_input.nextLine().toLowerCase().equals("ja")){
                try {
                    sqlstmt = "SELECT last_insert_id()";
                    PreparedStatement getLastIdInserted = conn.prepareStatement(sqlstmt);
                    ResultSet rs = getLastIdInserted.executeQuery();
                    int oktId = 0;
                    while (rs.next()){
                        oktId= rs.getInt(1);
                    }
                    if (oktId!=0){
                        registrerOvelseIOkt(oktId);
                    } else {
                        System.out.println("Ingen øvelser er registrert! Du må først registrere en øvelse");
                    }
                } catch (Exception e){
                    System.out.println("DB error while gettin latest ID inserted " + e);
                }
            }
        } catch (Exception e) {
            System.out.println("DB error during registration of Økt " + e);
        }
    }

    public void registrerOvelseIOkt(int oktId){
        String sqlstmt = "SELECT * FROM ØVELSE";
        PrintFromDB pdb = new PrintFromDB();
        pdb.connect();
        System.out.println("Disse øvelsene er registrert:");
        pdb.printResultFromQuery(sqlstmt);
        Scanner bruker_input = new Scanner(System.in);
        try {
            int ovelseId;
            String registrerFlere;

            do {
                System.out.println("Skriv in ØvelseID du ønsker å registrere i økta: ");
                ovelseId = bruker_input.nextInt();
                sqlstmt = "INSERT INTO øktharøvelse (ØktID, ØvelseID) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(sqlstmt);

                insertStmt.setInt(1, oktId);
                insertStmt.setInt(2, ovelseId);

                insertStmt.executeUpdate();
                System.out.println("Ønsker du å registrere flere øvelser? (ja/nei)");
                Scanner badCode = new Scanner(System.in);

                registrerFlere = badCode.nextLine();
            } while(registrerFlere.equals("ja"));

            PrintFromDB p = new PrintFromDB();
            p.connect();
            String latestInsertStmt = "SELECT * FROM øktharøvelse ";
            System.out.println("Registrete øvelser i økter: ");
            p.printResultFromQuery(latestInsertStmt);
        } catch (Exception e){
            System.out.println("DB error while registrating øvelse in økt" + e);
        }

    }

    public void printOekt(){
        try{
            System.out.println("Hvor mange økter vil du hente?");
            Scanner antallOkterInput = new Scanner(System.in);
            int antallOkter = Integer.parseInt(antallOkterInput.nextLine());
            String sqlstmt = "select Dato, Tidspunkt, Varighet, Form, Prestasjon, Notat from Økt order by Dato, Tidspunkt limit " + antallOkter;
            PrintFromDB p = new PrintFromDB();
            p.connect();
            p.printResultFromQuery(sqlstmt);

        } catch (Exception e){
            System.out.println("DB error during retriving Økt" + e);
        }
    }
}
