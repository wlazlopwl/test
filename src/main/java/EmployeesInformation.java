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

            while (rs.next()) {
                System.out.println(rs.getString("fName") + " " + rs.getString("lName"));
            }

            rs.close();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}


