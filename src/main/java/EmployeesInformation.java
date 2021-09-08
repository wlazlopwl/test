import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class EmployeesInformation {

    private final JDBC jdbc;

    public EmployeesInformation(JDBC jdbc) {
        this.jdbc = jdbc;
    }

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


