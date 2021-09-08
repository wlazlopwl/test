import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The main class with menu for selecting an action
 *
 *The class requires the implementation of methods that validate the entered data.
 * @author Pawel Wlazlo
 */
public class App {

    public static void main(String[] args) throws IOException {

        String selectedOption = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do {
            System.out.println("Please select a number for your requirement");
            System.out.println(" 1 - for first requirement ");
            System.out.println(" 2 - for second requirement ");
            System.out.println(" 3 - for third requirement ");
            System.out.println(" 4 - if you want to quit the app ");
            selectedOption = reader.readLine();


            switch (selectedOption) {
                case "1":

                    String typedDepartment = "";
                    System.out.println("The first requirement was selected ");
                    System.out.println("Enter name of department ");
                    typedDepartment = reader.readLine();
                    EmployeesInformation employeesInformation = new EmployeesInformation(new JDBC());
                    employeesInformation.getEmployeesByDepartment(typedDepartment);
                    System.exit(0);
                    break;

                case "2":

                    String percentageValue = "";
                    String numberLastnames = "";
                    System.out.println("The second requirement was selected ");
                    System.out.println("Enter percentage: ");
                    percentageValue = reader.readLine();
                    System.out.println("Enter the number of lastnames:  ");
                    numberLastnames = reader.readLine();

                    ArrayList<String> lastNamesList = new ArrayList<>();
                    int number = Integer.parseInt(numberLastnames);
                    for (int i = 1; i <= number; i++) {
                        System.out.println("Lastname number: " + i);
                        lastNamesList.add(reader.readLine());
                    }

                    DepartmentsInformation departmentsInformation = new DepartmentsInformation(new JDBC());
                    departmentsInformation.getDepartmentNameByParameter(lastNamesList, Integer.parseInt(percentageValue));

                    System.exit(0);
                    break;


                case "3":

                    String departmentName = "";
                    String percentageValue1 = "";
                    String numberLastnames1 = "";

                    System.out.println("The third requirement was selected ");
                    System.out.println("Enter department name: ");
                    departmentName = reader.readLine();
                    System.out.println("Enter percentage: ");
                    percentageValue1 = reader.readLine();
                    System.out.println("Enter the number of lastnames:  ");
                    numberLastnames1 = reader.readLine();

                    ArrayList<String> lastNamesList1 = new ArrayList<>();
                    int number2 = Integer.parseInt(numberLastnames1);
                    for (int i = 1; i <= number2; i++) {
                        System.out.println("Lastname number: " + i);
                        lastNamesList1.add(reader.readLine());
                    }

                    DepartmentsAndEmployeeInformation departmentsAndEmployeeInformation = new DepartmentsAndEmployeeInformation(new JDBC());
                    departmentsAndEmployeeInformation.getDepartmentAndEmployee(lastNamesList1, Integer.parseInt(percentageValue1), departmentName);

                    System.exit(0);
                    break;


                case "4":

                    System.exit(0);
                    break;

                default:
                    System.out.println("Error - please select number from 1 to 4");
                    break;

            }
        }
        while (selectedOption != "4");
    }
}
                         