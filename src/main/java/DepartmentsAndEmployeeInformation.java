import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 The class combines the requirements from the DepartmentsInformation and EmployeesInformation classes.
 */
public class DepartmentsAndEmployeeInformation {

    private final JDBC jdbc;

    public DepartmentsAndEmployeeInformation(JDBC jdbc) {
        this.jdbc = jdbc;
    }

    /**
     @param departmentName  is the department name entered by the user in the console
     @param lastnames list of lastnames entered by the user in the console
     @param  percentage the percentage entered by the user in the console used to calculate the earnings for a given position in relation to the maximum earnings
     */
    public void getDepartmentAndEmployee(ArrayList<String> lastnames, int percentage, String departmentName) {
        try {

            double calculatedPercentage = 1 - (double) percentage / 100;
            Connection connection = jdbc.connect();
            PreparedStatement prst = connection.prepareStatement(JDBC.createQueryForThirdRequirement(lastnames.size()));

            for (int i = 1; i <= lastnames.size(); i++) {
                prst.setString(i, lastnames.get(i - 1));
                if (i == lastnames.size()) {
                    prst.setString(i + 1, departmentName);
                    prst.setDouble(i + 2, calculatedPercentage);
                }
            }

            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("FIRST_NAME") + " " + rs.getNString("LAST_NAME") + " " +
                        rs.getDouble("SALARY") + " " + rs.getString("DEPARTMENT_NAME")
                );
            }

            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}


