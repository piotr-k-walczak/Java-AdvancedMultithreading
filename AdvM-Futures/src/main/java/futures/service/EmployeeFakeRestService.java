package futures.service;

import epam.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class EmployeeFakeRestService {

    public final static List<String> FIRST_NAMES = List.of(
            "Piotr", "Michał", "Jakub", "Tomasz", "Bartosz", "Ignacy", "Krzesimir", "Doktor", "Mariusz"
    );

    public final static List<String> LAST_NAMES = List.of(
            "Walczak", "Kowalsky", "Zieliński", "Amadeusz", "Polak", "Kapela", "Stasko", "Hop", "Pop", "Chłop", "Szczęsny"
    );

    public final static int numberOfEmployees = LAST_NAMES.size() * FIRST_NAMES.size();

    public final static long MIN_SALARY = 9000;
    public final static long MAX_SALARY = 15000;

    public Future<List<Employee>> hiredEmployees() {
        CompletableFuture<List<Employee>> completableFuture = new CompletableFuture<>();
        completableFuture.completeAsync(this::generateEmployees);
        return completableFuture;
    }

    public Future<Long> getSalary(Long employeeId) {
        CompletableFuture<Long> completableFuture = new CompletableFuture<>();
        completableFuture.completeAsync(() -> {
            Random random = new Random();
            return random.nextLong(MIN_SALARY, MAX_SALARY);
        });
        return completableFuture;
    }

    private List<Employee> generateEmployees(){
        Random random = new Random();
        List<Employee> res = new ArrayList<>();
        for (int i = 0; i < numberOfEmployees; i++) {
            res.add(new Employee(i, FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size())), LAST_NAMES.get(random.nextInt(LAST_NAMES.size()))));
        }
        return res;
    }
}
