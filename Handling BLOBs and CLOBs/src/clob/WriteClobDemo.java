package clob;

import java.io.*;
import java.sql.*;

public class WriteClobDemo {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    private static final String UPDATE_EMPLOYEES_SET_RESUME
            = "update employees set resume=? where email='john.doe@foo.com'";

    private static final String SAMPLE_RESUME_TXT_PATH = "Handling BLOBs and CLOBs/src/clob/sample_resume.txt";

    public static void main(String[] args) {

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             PreparedStatement myStmt = myConn.prepareStatement(UPDATE_EMPLOYEES_SET_RESUME)) {

            File theFile = new File(SAMPLE_RESUME_TXT_PATH);

            // Set parameter for resume file name
            try (FileReader input = new FileReader(theFile)) {

                myStmt.setCharacterStream(1, input);

                System.out.println("Reading input file: " + theFile.getAbsolutePath());

                // Execute statement
                System.out.println("\nStoring resume in database: " + theFile);
                System.out.println(UPDATE_EMPLOYEES_SET_RESUME);

                myStmt.executeUpdate();

                System.out.println("\nCompleted successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
