package futures.service;

import epam.model.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PrintSalariesService {

    private EmployeeFakeRestService employeeFakeRestService;

    public PrintSalariesService(EmployeeFakeRestService employeeFakeRestService){
        this.employeeFakeRestService = employeeFakeRestService;
    }

    public void printEmployeeSalary(Employee employee){
        System.out.println(employee.getId() + ". " + employee.getFirstName() + " " + employee.getLastName() + " makes " + employee.getSalary() + "PLN");
    }

    public void printEmployeeSalaries() {
        try {
            CompletableFuture.supplyAsync(() -> {
                try {
                    return employeeFakeRestService.hiredEmployees().get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }).thenApplyAsync(employees -> employees.stream().peek(emp -> {
                try {
                    emp.setSalary(employeeFakeRestService.getSalary(emp.getId()).get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            })).thenAccept(employeeStream -> employeeStream.forEach(this::printEmployeeSalary))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
