package blob;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class WriteBlobDemo {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    private static final String UPDATE_EMPLOYEES_SET_RESUME
            = "update employees set resume=? where email='john.doe@foo.com'";

    private static final String SAMPLE_RESUME_PDF_PATH = "Handling BLOBs and CLOBs/sample_resume.pdf";

    public static void main(String[] args) {

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             PreparedStatement myStmt = myConn.prepareStatement(UPDATE_EMPLOYEES_SET_RESUME)) {

            File theFile = new File(SAMPLE_RESUME_PDF_PATH);

            // Set parameter for resume file name
            try (FileInputStream input = new FileInputStream(theFile)) {

                myStmt.setBinaryStream(1, input);

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
