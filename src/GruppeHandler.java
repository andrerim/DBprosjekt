import java.sql.*;
        import java.util.Scanner;

public class GruppeHandler extends DBConn {
    private PreparedStatement registrer_stmt;
    private int gruppeID = 0;
    private int øvelseID = 0;
    private Scanner bruker_input = new Scanner(System.in);

    public void registrerGruppe() {
        try {
            registrer_stmt = conn.prepareStatement("INSERT INTO Øvelsesgruppe(Gruppenavn) VALUES (?) ");

            System.out.println("Skriv navn på gruppen");
            String gruppeNavn = bruker_input.nextLine();
            registrer_stmt.setString(1, gruppeNavn);
            registrer_stmt.executeUpdate();
        }catch (Exception e) {
            System.out.println("Error" + e);
        }
    }
    public void leggTilOvelseIGruppe() {
        try {
            stillSpørsmål();
            String nei = bruker_input.nextLine();
            while(!nei.toLowerCase().equals("nei")) {
                System.out.println("Hvilken gruppe vil du legge til øvelser i? (velg med tall)");
                PreparedStatement hentGrupperStmt = conn.prepareStatement(
                        "SELECT * FROM Øvelsesgruppe ORDER BY ØvelsesgruppeID");
                ResultSet rs = hentGrupperStmt.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString(1) + ": " + rs.getString(2));
                }
                gruppeID = Integer.parseInt(bruker_input.nextLine());
                PreparedStatement hentØvelseStmt = conn.prepareStatement(
                        "SELECT ØvelseID, Navn FROM Øvelse ORDER BY ØvelseID");
                System.out.println("Hvilken øvelse vil du legge til i gruppen? (velg med tall)");
                rs = hentØvelseStmt.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString(1) + ": " + rs.getString(2));
                }
                øvelseID = Integer.parseInt(bruker_input.nextLine());

                PreparedStatement lagØvelseIGruppe = conn.prepareStatement(
                        "INSERT INTO ØvelseIGruppe(ØvelsesgruppeID, ØvelseId) VALUES (?,?)");
                lagØvelseIGruppe.setInt(1, gruppeID);
                lagØvelseIGruppe.setInt(2, øvelseID);
                lagØvelseIGruppe.executeUpdate();

                System.out.println("Øvelse lagt til");
                stillSpørsmål();
                nei = bruker_input.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Error during leggTilØvelseIGruppe: " + e);

        }
    }
    public void hentGruppe() {
        try {
            System.out.println("Hvilken gruppe vil du vise? (velg med tall)");
            PreparedStatement hentGrupperStmt = conn.prepareStatement(
                    "SELECT * FROM Øvelsesgruppe ORDER BY ØvelsesgruppeID");
            ResultSet rs = hentGrupperStmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1) + ": " + rs.getString(2));
            }
            int gruppeID = Integer.parseInt(bruker_input.nextLine());

            PreparedStatement finnØvelserIGruppe = conn.prepareStatement(
                    "SELECT Gruppenavn, Navn FROM ØvelseIGruppe NATURAL JOIN Øvelse NATURAL JOIN Øvelsesgruppe WHERE ØvelsesgruppeID="+gruppeID);
            rs = finnØvelserIGruppe.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(2));
            }
        }catch (Exception e) {
            System.out.println("Error i henting av gruppe: " + e);
        }

    }
    private void stillSpørsmål() {
        System.out.println("Vil du legge til en øvelse? (ja/nei)");
    }

}
/*
            stillSpørsmål();
            String svar = bruker_input.nextLine();
            while (!svar.toLowerCase().equals("n")) {
                if (svar.toLowerCase().equals("y")) {
                    System.out.println("Skriv inn navn på øvelsen");
                    String ovelseNavn = bruker_input.nextLine();
                    PreparedStatement stmt = conn.prepareStatement(
                            "SELECT * FROM Øvelse where Navn='Benkpress'");

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        øvelseID = rs.getInt(1);
                    }

                    PreparedStatement gruppeStmt = conn.prepareStatement(
                            "INSERT INTO ØvelseIGruppe(ØvelsesgruppeID, ØvelseID ) VALUES(?, ?)");

                    gruppeStmt.setInt(1, gruppeID);
                    gruppeStmt.setInt(2, øvelseID);
                    gruppeStmt.executeUpdate();
                }
                stillSpørsmål();
                svar = bruker_input.nextLine();
            }
*/
 /*
 import java.sql.*;
import java.util.Scanner;

public class OvelseHandler extends DBConn {

    private PreparedStatement register_stmt;
    int ovelseKey;
    public void registrerOvelse(){
        Scanner bruker_input = new Scanner(System.in);

        String sqlstmt = "INSERT INTO øvelse (Navn) VALUES (?)";

        try {

            register_stmt = conn.prepareStatement(sqlstmt, Statement.RETURN_GENERATED_KEYS);


            System.out.println("Navn på øvelsen: ");
            String ovelseNavn = bruker_input.nextLine();
            register_stmt.setString(1, ovelseNavn);
            register_stmt.executeUpdate();
            ResultSet rs = register_stmt.getGeneratedKeys();
            if (rs.next()) {
                ovelseKey = rs.getInt(1);
            }

        }  catch (Exception e){
            System.out.println("DB error during registration of Øvelse");
        }

        System.out.println("Utføres øvelsen med apparat? (ja/nei)");
        String ovelse_type = bruker_input.nextLine();

        if (ovelse_type.equals("ja")){
            registrerOvelseMedApparat(ovelseKey);
        } else {
            ovelse_type = "øvelseutenapparat";
        }
    }

    public void registrerOvelseMedApparat(int ovelseKey){
        String sqlstmt = "INSERT INTO øvelsemedapparat (ØvelseID, ApparatID, AntallKilo, AntallSet) VALUES (?, ?, ?, ?)";

        Scanner bruker_input = new Scanner(System.in);

        try {
            register_stmt = conn.prepareStatement(sqlstmt);

            register_stmt.setInt(1, ovelseKey);

            System.out.println("Navn på apparat:  ");
            String apparatNavn = bruker_input.nextLine();

            ApparatHandler apparat = new ApparatHandler();
            apparat.connect();
            int apparatID = apparat.getApparatID(apparatNavn);

            register_stmt.setInt(2, apparatID);

            System.out.println("Hvor mange kilo? ");
            int antallKilo = Integer.parseInt(bruker_input.nextLine());
            register_stmt.setInt(3, antallKilo);

            System.out.println("Hvor mange set? ");
            int antallSet = Integer.parseInt(bruker_input.nextLine());
            register_stmt.setInt(4, antallSet);


        } catch (Exception e) {
            System.out.println("DB error during registration of ovelse");
        }
    }

}

  */