package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/employees")
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @GetMapping
    public List<Employee> listAll(){
        return employeeRepository.listAll();
    }

    @GetMapping(path = "/{id}")
    public Employee findById(@PathVariable Long employeeId) {
        return employeeRepository.findById(employeeId);
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
    public List<Employee> listByPage(@RequestParam Long pageNumber, Long pageSize){
        return employeeRepository.listByPage(pageNumber, pageSize);
    }

    @PutMapping(path = "/{id}")
    public Employee updateEmployee(@PathVariable Long employeeId, @RequestParam Integer newAge, @RequestParam Integer newSalary) {
        return employeeRepository.updateEmployee(employeeId, newAge, newSalary);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long employeeId) {
        employeeRepository.deleteEmployee(employeeId);
    }
}
