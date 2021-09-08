import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String USER = "hr";
    private static final String PASSWORD = "hr";


    public Connection connect() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(URL, USER, PASSWORD);

        return connection;
    }

    public static String createQueryForFirstRequirement() {
        return "select EMP.first_name as fName, EMP.last_name as lName\n" +
                "from EMPLOYEES EMP left join DEPARTMENTS DEP on EMP.department_id=DEP.department_id\n" +
                "where DEP.department_name = ? order by first_name, last_name";
    }
}
