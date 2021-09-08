import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * the class is responsible for displaying the names of employees who have worked at least once in a given department
 */
public class EmployeesInformation {

    private final JDBC jdbc;

    public EmployeesInformation(JDBC jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * The method executes SQL queries with the given parameter.
     * Displays the first and last name of the employee
     *
     * @param departmentName size of the list with lastnames
     */
    public void getEmployeesByDepartment(String departmentName) {
        try {
            Connection connection = jdbc.connect();
            PreparedStatement prst = connection.prepareStatement(JDBC.createQueryForFirstRequirement());
            prst.setString(1, departmentName);
            ResultSet rs = prst.executeQuery();

            int resultSize = 0;
            while (rs.next()) {
                resultSize++;
                System.out.println(rs.getString("fName") + " " + rs.getString("lName"));
            }

            if (resultSize == 0) System.out.println("No result. Check the parameters.");
            else if (resultSize < 100)
                System.out.println("The number of results is low. Check the parameters.");


            rs.close();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}


