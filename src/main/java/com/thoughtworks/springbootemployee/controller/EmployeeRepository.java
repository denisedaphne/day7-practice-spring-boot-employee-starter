package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee(1L, "Daphne", 23, "Female", 1000, 1L));
        employees.add(new Employee(2L, "Red", 25, "Male", 1500, 2L));
        employees.add(new Employee(3L, "Denise", 20, "Female", 2000, 1L));
        employees.add(new Employee(4L, "Raymond", 21, "Male", 3000, 3L));
        employees.add(new Employee(5L, "Kara", 28, "Female", 2500, 4L));
        employees.add(new Employee(6L, "Chewie", 26, "Female", 3500, 3L));
        employees.add(new Employee(7L, "Diana Prince", 22, "Female", 4000, 6L));
        employees.add(new Employee(8L, "Barry Allen", 29, "Male", 4500, 5L));
    }

    public List<Employee> listAll() {
        return employees;
    }

    public Employee findEmployeeById(Long employeeId) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee addEmployee(Employee employee) {
        Long employeeId = generateEmployeeId();

        Employee toBeSavedEmployee = new Employee(employeeId, employee.getEmployeeName(), employee.getAge(), employee.getGender(), employee.getSalary(), employee.getCompanyId());
        employees.add(toBeSavedEmployee);
        return toBeSavedEmployee;
    }

    public Long generateEmployeeId(){
        return employees.stream()
                .mapToLong(Employee::getEmployeeId)
                .max()
                .orElse(Const.START_ID_MINUS_ONE) + Const.ID_INCREMENT;
    }

    public List<Employee> listEmployeeByPage(Long pageNumber, Long pageSize) {
        return employees.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee updateEmployee(Long employeeId, Integer newAge, Integer newSalary) {
        Employee employeeToUpdate = employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);

        employeeToUpdate.setAge(newAge);
        employeeToUpdate.setSalary(newSalary);

        return employeeToUpdate;
    }

    public void deleteEmployee(Long employeeId) {
        Employee employeeToDelete = employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);

        employees.remove(employeeToDelete);
    }

    public List<Employee> listEmployeesByCompanyId(Long companyId) {
        return employees.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }
}
