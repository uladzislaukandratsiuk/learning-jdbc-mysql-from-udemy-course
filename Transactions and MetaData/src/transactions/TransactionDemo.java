package transactions;

import java.sql.*;
import java.util.Scanner;

public class TransactionDemo {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    private static final String DELETE_HR_DEPARTMENT_EMPLOYEES
            = "delete from employees where department='HR'";
    private static final String UPDATE_SALARY_FOR_DEPARTMENT_ENGINEERING
            = "update employees set salary=300000 where department='Engineering'";
    private static final String PREPARED_STATEMENT_SELECT_EMPLOYEES
            = "select * from employees where department=?";

    public static void main(String[] args) {

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             Statement myStmt = myConn.createStatement()) {

            // Turn off auto commit
            myConn.setAutoCommit(false);

            // Show salaries BEFORE
            System.out.println("Salaries BEFORE\n");
            showSalaries(myConn, "HR");
            showSalaries(myConn, "Engineering");

            // Transaction Step 1: Delete all HR employees

            myStmt.executeUpdate(DELETE_HR_DEPARTMENT_EMPLOYEES);

            // Transaction Step 2: Set salaries to 300000 for all Engineering employees
            myStmt.executeUpdate(UPDATE_SALARY_FOR_DEPARTMENT_ENGINEERING);

            System.out.println("\n>> Transaction steps are ready.\n");

            // Ask user if it is okay to save
            boolean ok = askUserIfOkToSave();

            if (ok) {
                // store in database
                myConn.commit();
                System.out.println("\n>> Transaction COMMITTED.\n");
            } else {
                // discard
                myConn.rollback();
                System.out.println("\n>> Transaction ROLLED BACK.\n");
            }

            // Show salaries AFTER
            System.out.println("Salaries AFTER\n");
            showSalaries(myConn, "HR");
            showSalaries(myConn, "Engineering");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prompts the user. Return true if they enter "yes", false otherwise
     *
     * @return if input "yes" true, false otherwise
     */
    private static boolean askUserIfOkToSave() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Is it okay to save?  yes/no: ");
        String input = scanner.nextLine();

        scanner.close();

        return input.equalsIgnoreCase("yes");
    }

    private static void showSalaries(Connection myConn, String theDepartment) {

        System.out.println("Show Salaries for Department: " + theDepartment);

        // Prepare statement
        try (PreparedStatement myStmt = myConn
                .prepareStatement(PREPARED_STATEMENT_SELECT_EMPLOYEES)) {

            myStmt.setString(1, theDepartment);

            // Execute SQL query
            try (ResultSet myRs = myStmt.executeQuery()) {
                // Process result set
                while (myRs.next()) {
                    String lastName = myRs.getString("last_name");
                    String firstName = myRs.getString("first_name");
                    double salary = myRs.getDouble("salary");
                    String department = myRs.getString("department");

                    System.out.format("%s, %s, %s, %.2f\n", lastName, firstName,
                            department, salary);
                }
                System.out.println();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
