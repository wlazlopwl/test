import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * the class is responsible for displaying the names of departments the names of the departments
 * where the employee with the given name worked and his earnings in the last position held are
 * lower by more than the percentage specified in the parameter, compared to the maximum earnings for this position.
 */
public class DepartmentsInformation {

    private final JDBC jdbc;

    public DepartmentsInformation(JDBC jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * The method executes SQL queries with the given parameter.
     * Displays the name of departments
     *
     * @param lastnames List of employee lastnames provided in the console
     * @param percentageValue the percentage given to the console by the user
     */
    public void getDepartmentNameByParameter(ArrayList<String> lastnames, int percentageValue) {
        try {

            double calculatedPercentage = 1 - (double) percentageValue / 100;
            Connection connection = jdbc.connect();
            PreparedStatement prst = connection.prepareStatement(JDBC.createQueryForSecondRequirement(lastnames.size()));

            for (int i = 1; i <= lastnames.size(); i++) {
                prst.setString(i, lastnames.get(i - 1));
                if (i == lastnames.size()) {
                    prst.setDouble(i + 1, calculatedPercentage);
                }
            }

            ResultSet rs = prst.executeQuery();

            int resultSize = 0;
            while (rs.next()) {
                resultSize++;
                System.out.println(rs.getString("DEPARTMENT_NAME"));
            }

            if (resultSize == 0) System.out.println("No result. Check the parameters");
            else if (resultSize > 100)
                System.out.println("The number of results is high. Check the parameters.");

            rs.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}


