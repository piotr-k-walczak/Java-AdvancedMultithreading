package futures;

import epam.model.Employee;
import epam.service.EmployeeFakeRestService;
import epam.service.PrintSalariesService;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        EmployeeFakeRestService employeeFakeRestService = new EmployeeFakeRestService();
        PrintSalariesService printSalariesService = new PrintSalariesService(employeeFakeRestService);
        printSalariesService.printEmployeeSalaries();
    }
}
