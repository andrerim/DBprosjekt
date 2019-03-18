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
            sqlstmt = "SELECT * from økt WHERE Økt.ØktID = (select max(ØktID) from øktID);";
            p.printResultFromQuery(sqlstmt);

            System.out.println("Ønsker du å registrere øvelser for økta? (ja/nei");
            if (bruker_input.nextLine().toLowerCase().equals("ja")){
                try {
                    sqlstmt = "SELECT last_insert_id()";
                    System.out.println("qerror1");
                    PreparedStatement getLastIdInserted = conn.prepareStatement(sqlstmt);
                    System.out.println("qerror2");
                    ResultSet rs = getLastIdInserted.executeQuery();
                    System.out.println("qerror3");
                    int oktId = 0;
                    while (rs.next()){
                        oktId= rs.getInt(1);
                    }
                    if (oktId!=0){
                        registrerOvelseIOkt(oktId);
                    }
                } catch (Exception e){
                    System.out.println("DB error while gettin latest ID inserted");
                }
            }
        } catch (Exception e) {
            System.out.println("DB error during registration of Økt" + e);
        }
    }

    public void registrerOvelseIOkt(int oktId){
        String sqlstmt = "SELECT * FROM ØVELSE";
        PrintFromDB pdb = new PrintFromDB();
        pdb.connect();
        pdb.printResultFromQuery(sqlstmt);
        Scanner bruker_input = new Scanner(System.in);
        System.out.println("Skriv in ØvelseID du ønsker å registrere");
        try {
            int ovelseId = bruker_input.nextInt();
            sqlstmt = "INSERT INTO øktharøvelse (ØktID, ØvelseID) VALUES (?, ?)";

            PreparedStatement insert_stmt = conn.prepareStatement(sqlstmt);

            insert_stmt.setInt(1, oktId);
            insert_stmt.setInt(2, ovelseId);


            insert_stmt.executeUpdate();
            PrintFromDB p = new PrintFromDB();
            p.connect();
            p.printLatestInsert("øktharøvelse");
        } catch (Exception e){
            System.out.println("DB error while registrating øvelse in økt");
        }

    }

    public void printOekt(){
        try{
            System.out.println("Hvor mange økter vil du hente?");
            Scanner antallØkterInput = new Scanner(System.in);
            int antallØkter = Integer.parseInt(antallØkterInput.nextLine());
            PreparedStatement stmt = conn.prepareStatement("select Dato, Tidspunkt, Varighet, Form, Prestasjon, Notat from Økt order by Dato, Tidspunkt limit " + antallØkter );
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print("  ");
                    String columnValue = rs.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue) ;
                }
                System.out.println(" ");
            }
        } catch (Exception e){
            System.out.println("DB error during retriving Økt" + e);
        }
    }
}
