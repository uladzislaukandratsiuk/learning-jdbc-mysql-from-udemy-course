package blob;

import java.io.*;
import java.sql.*;

public class ReadBlobDemo {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    private static final String SAMPLE_RESUME_PDF_PATH = "Handling BLOBs and CLOBs/resume_from_db.pdf";

    private static final String SELECT_RESUME = "select resume from employees where email='john.doe@foo.com'";

    public static void main(String[] args) {

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             Statement myStmt = myConn.createStatement();
             ResultSet myRs = myStmt.executeQuery(SELECT_RESUME)) {

            // Set up a handle to the file
            File theFile = new File(SAMPLE_RESUME_PDF_PATH);

            if (myRs.next()) {

                try (FileOutputStream output = new FileOutputStream(theFile);
                     InputStream input = myRs.getBinaryStream("resume")) {

                    System.out.println("Reading resume from database...");
                    System.out.println(SELECT_RESUME);

                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        output.write(buffer);
                    }

                    System.out.println("\nSaved to file: " + theFile.getAbsolutePath());

                    System.out.println("\nCompleted successfully!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
