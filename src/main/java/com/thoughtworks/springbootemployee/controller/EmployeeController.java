package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/employees")
@RestController
@SuppressWarnings("all")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @GetMapping
    public List<Employee> listAllEmployees(){
        return employeeRepository.listAll();
    }

    @GetMapping("/{employeeId}")
    public Employee findByEmployeeId(@PathVariable Long employeeId) {
        return employeeRepository.findEmployeeById(employeeId);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findByGender (@RequestParam String gender){
        return employeeRepository.findByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.addEmployee(employee);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> listEmployeesByPage(@RequestParam Long pageNumber, Long pageSize){
        return employeeRepository.listEmployeeByPage(pageNumber, pageSize);
    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable Long employeeId, @RequestParam Integer newAge, @RequestParam Integer newSalary) {
        return employeeRepository.updateEmployee(employeeId, newAge, newSalary);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long employeeId) {
        employeeRepository.deleteEmployee(employeeId);
    }
}
