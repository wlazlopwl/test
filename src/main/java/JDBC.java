import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The class provides a connection to the database and functions that return SQL queries
 */
public class JDBC {

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String USER = "hr";
    private static final String PASSWORD = "hr";

    /**
     * The method provides SQL query for first requirement
     *
     * @return returns String with SQL query for first requirement
     */
    public static String createQueryForFirstRequirement() {
        return "select EMP.first_name as fName, EMP.last_name as lName\n" +
                "from EMPLOYEES EMP left join DEPARTMENTS DEP on EMP.department_id=DEP.department_id\n" +
                "where DEP.department_name = ? order by first_name, last_name";
    }

    /**
     * The method provides SQL query for second requirement
     *
     * @param lastNameListSize size of the list with lastnames
     * @return returns String with SQL query for second requirement
     */
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

    /**
     * The method provides SQL query for third requirement
     *
     * @param lastNameListSize size of the list with lastnames
     * @return returns String with SQL query for third requirement
     */
    public static String createQueryForThirdRequirement(int lastNameListSize) {
        String query = "select distinct DEPARTMENT_NAME from (" +
                " select EMP.FIRST_NAME as FIRST_NAME, EMP.LAST_NAME as LAST_NAME, EMP.SALARY as SALARY, EMP.DEPARTMENT_ID as DEPARTMENT_ID,\n" +
                "  JOB_H.START_DATE as START_DATE, JOBS.MAX_SALARY as MAX_SALARY,DEP.DEPARTMENT_NAME as DEPARTMENT_NAME,\n" +
                "  row_number() over (partition by EMP.EMPLOYEE_ID order by JOB_H.START_DATE DESC) as row_number\n" +
                "  FROM EMPLOYEES EMP \n" +
                "  left join JOB_HISTORY JOB_H on EMP.JOB_ID=JOB_H.JOB_ID\n" +
                "  left join JOBS on EMP.JOB_ID = JOBS.JOB_ID\n" +
                "  left join DEPARTMENTS DEP on EMP.DEPARTMENT_ID=DEP.DEPARTMENT_ID) where (row_number = 1 and LAST_NAME in (";

        StringBuilder queryBuilder = setSizeParametersFromList(lastNameListSize, query);
        queryBuilder.append(") and DEPARTMENT_NAME = ? and SALARY< MAX_SALARY * ?) order by DEPARTMENT_NAME");

        return queryBuilder.toString();
    }


    /**
     * The method sets the number of parameters in SQL query
     *
     * @param lastNameListSize size of the list with lastnames
     * @param query            SQL query
     * @return returns StringBuilder with part SQL query for selected requirement
     */
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

    /**
     * The method connect to the database
     *
     * @return returns an object of the Connection
     */
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
