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
    public Employee findById(@PathVariable Long id) {
        return employeeRepository.findById(id);
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
    public Employee updateEmployee(@PathVariable Long id, @RequestParam Integer newAge, @RequestParam Integer newSalary) {
        return employeeRepository.updateEmployee(id, newAge, newSalary);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteEmployee(id);
    }

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/companies")
    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }
}
