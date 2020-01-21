import java.sql.*;

public class JdbcTest {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    private static final String SELECT_FROM_EMPLOYEES = "select * from employees";
    private static final String INSERT_INTO_EMPLOYEES = "insert into employees (last_name, first_name, " +
            "email, department, salary) values ('Wright', 'Eric', 'eric.wright@foo.com', 'HR', 33000.00)";
    private static final String SELECT_FROM_EMPLOYEES_WHERE = "select last_name, first_name, email, " +
            "department from employees where last_name = 'Wright' and first_name = 'Eric'";

    public static void main(String[] args) {

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             Statement myStmt = myConn.createStatement()) {

            System.out.println("Database connection successful!\n");

            myStmt.executeUpdate(INSERT_INTO_EMPLOYEES);

            try (ResultSet myRs = myStmt.executeQuery(SELECT_FROM_EMPLOYEES_WHERE)) {
                while (myRs.next()) {
                    System.out.println(myRs.getString("last_name") + ", "
                            + myRs.getString("first_name") + ", "
                            + myRs.getString("email") + ", "
                            + myRs.getString("department"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}