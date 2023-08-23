package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeUpdateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if (employee.hasInvalidAge(employee)) {
            throw new EmployeeCreateException();
        }

        employee.setActive(true);
        return employeeRepository.addEmployee(employee);
    }

    public void delete(Long id) {
        Employee matchedEmployee = employeeRepository.findEmployeeById(id);
        matchedEmployee.setActive(Boolean.FALSE);
        employeeRepository.update(id, matchedEmployee);
    }

    public Employee update(Long id, Employee updateEmployee) {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (!employee.isActive()){
            throw new EmployeeUpdateException();
        }
        return employeeRepository.update(id, updateEmployee);
    }
}
