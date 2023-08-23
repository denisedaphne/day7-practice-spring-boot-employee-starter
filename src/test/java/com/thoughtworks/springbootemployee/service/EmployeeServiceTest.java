package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeUpdateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {
    private EmployeeService employeeService;
    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setUp(){
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }
    @Test
    void should_return_created_employee_when_create_given_employee_service_and_employee_wit_valid_age() {
        //given
        Employee employee = new Employee(null, "Lucy", 20, "Female", 3000);
        Employee savedEmployee = new Employee(1L, "Lucy", 20, "Female", 3000);
        when(mockedEmployeeRepository.addEmployee(employee)).thenReturn(savedEmployee);
        //when
        Employee employeeResponse = employeeService.create(employee);
        //then
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals("Lucy", employeeResponse.getName());
        assertEquals(20, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(3000, employeeResponse.getSalary());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_whose_age_is_less_than_18() {
        //given
        Employee employee = new Employee(null, "Lucy", 17, "Female", 3000);
        //when
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });
        //then
        assertEquals("Employee must be 18-65 years old", employeeCreateException.getMessage());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_whose_age_is_greater_than_65() {
        //given
        Employee employee = new Employee(null, "Lucy", 70, "Female", 5000);
        //when
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });
        //then
        assertEquals("Employee must be 18-65 years old", employeeCreateException.getMessage());
    }

    @Test
    void should_create_employee_with_default_active_status() {
        //given
        Employee employee = new Employee(null, "Lucy", 20, "Female", 5000, true);
        //when
        when(mockedEmployeeRepository.addEmployee(any(Employee.class))).thenReturn(employee);
        Employee createEmployee = employeeService.create(employee);
        //then
        assertTrue(createEmployee.isActive());
    }

    @Test
    void should_delete_employee_by_setting_active_status_to_false() {
        //given
        Employee employee = new Employee(1L, "Lucy", 25, "Female", 5000);
        when(mockedEmployeeRepository.findEmployeeById(1L)).thenReturn(employee);
        when(mockedEmployeeRepository.update(any(Long.class), any(Employee.class))).thenReturn(employee);
        //when
        employeeService.delete(1L);
        //then
        assertFalse(employee.isActive());
        verify(mockedEmployeeRepository).update(eq(1L), any(Employee.class));
    }

    @Test
    void should_throw_exception_when_updating_given_inactive_employee() {
        //given
        Employee employee = new Employee(1L, "Lucy", 25, "Female", 5000);
        employee.setActive(false);
        when(mockedEmployeeRepository.findEmployeeById(1L)).thenReturn(employee);
        //when
        EmployeeUpdateException employeeUpdateException = assertThrows(EmployeeUpdateException.class, () -> {
            employeeService.update(1L, employee);
        });
        //then

        assertEquals("Employee is inactive", employeeUpdateException.getMessage());
    }

    @Test
    void should_return_employee_when_creating_given_valid_input() {
        Employee newEmployee = new Employee("Alice", 25, "Female", 8000);
        when(mockedEmployeeRepository.addEmployee(newEmployee)).thenReturn(newEmployee);

        Employee createdEmployee = employeeService.create(newEmployee);

        assertNotNull(createdEmployee);
        assertEquals(newEmployee.getName(), createdEmployee.getName());
        assertEquals(newEmployee.getAge(), createdEmployee.getAge());
        assertEquals(newEmployee.getGender(), createdEmployee.getGender());
        assertEquals(newEmployee.getSalary(), createdEmployee.getSalary());
    }

    @Test
    void should_throw_employee_exception_when_create_given_invalid_age() {
        Employee invalidEmployee = new Employee("John", 17, "Male", 6000);

        assertThrows(EmployeeCreateException.class, () -> employeeService.create(invalidEmployee));
    }
}
