package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();

    private static final String FEMALE = "Female";

    private static final String MALE = "Male";

    static {
        employees.add(new Employee(1L, "Daphne", 23, FEMALE, 1000, 1));
        employees.add(new Employee(2L, "Red", 25, MALE, 1500, 1));
        employees.add(new Employee(3L, "Sandra", 66, FEMALE, 788, 2));
        employees.add(new Employee(4L, "Sam", 34, MALE, 4566, 3));
        employees.add(new Employee(5L, "Jane", 46, FEMALE, 5555, 4));
    }

    public List<Employee> listAll() {
        return employees;
    }

    public Employee findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee addEmployee(Employee employee) {
        Long generatedId = generateId();
        Employee toBeAddedEmployee = new Employee(generatedId, employee.getName(), employee.getAge(), employee.getGender(), employee.getSalary(),employee.getCompanyId());
        employees.add(toBeAddedEmployee);
        return toBeAddedEmployee;

    }

    public Long generateId() {
        return employees.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElse(0L) + 1;
    }

    public void deleteEmployee(Employee employee) {
        employees.remove(employee);
    }

    public List<Employee> listByPage(Long pageNumber, Long pageSize) {
        return employees.stream()
                .skip((pageNumber -1)* pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> findByCompanyId(Long employeesCompanyId) {
        return employees.stream()
                .filter(employee -> employee.getCompanyId().equals(employeesCompanyId))
                .collect(Collectors.toList());
    }
}
