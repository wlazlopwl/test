import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String USER = "hr";
    private static final String PASSWORD = "hr";

    public static String createQueryForFirstRequirement() {
        return "select EMP.first_name as fName, EMP.last_name as lName\n" +
                "from EMPLOYEES EMP left join DEPARTMENTS DEP on EMP.department_id=DEP.department_id\n" +
                "where DEP.department_name = ? order by first_name, last_name";
    }

    public static String createQueryForSecondRequirement(int lastNameListSize) {
        String query = "select distinct DEPARTMENT_NAME from (" +
                "  EMP.LAST_NAME as LAST_NAME, EMP.SALARY as SALARY, EMP.DEPARTMENT_ID as DEPARTMENT_ID,\n" +
                "  JOB_H.START_DATE as START_DATE, JOBS.MAX_SALARY as MAX_SALARY,DEP.DEPARTMENT_NAME as DEPARTMENT_NAME,\n" +
                "  row_number() over (partition by EMP.EMPLOYEE_ID order by JOB_H.START_DATE DESC) as row_number\n" +
                "  FROM EMPLOYEES EMP \n" +
                "  left join JOB_HISTORY JOB_H on EMP.JOB_ID=JOB_H.JOB_ID\n" +
                "  left join JOBS on EMP.JOB_ID = JOBS.JOB_ID\n" +
                "  left join DEPARTMENTS DEP on EMP.DEPARTMENT_ID=DEP.DEPARTMENT_ID) where (row_number = 1 and LAST_NAME in (";

        StringBuilder queryBuilder = setSizeParametersFromList(lastNameListSize, query);
        queryBuilder.append(") and SALARY < MAX_SALARY * ? ) order by DEPARTMENT_NAME");

        return queryBuilder.toString();

    }

    private static StringBuilder setSizeParametersFromList(int lastNameListSize, String query) {
        StringBuilder queryBuilder = new StringBuilder(query);

        for (int i = 0; i < lastNameListSize; i++) {
            queryBuilder.append(" ?");
            if (i != lastNameListSize - 1) {
                queryBuilder.append(",");
            }
        }
        return queryBuilder;
    }

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

}
