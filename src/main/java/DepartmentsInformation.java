import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentsInformation {

    private final JDBC jdbc;

    public DepartmentsInformation(JDBC jdbc) {
        this.jdbc = jdbc;
    }


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


