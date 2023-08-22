package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class CompanyController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/companies")
    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    @GetMapping("/companies/{id}")
    public Company findCompanyById(@PathVariable Long id) {
        return companyRepository.findCompanyById(id);
    }

    @GetMapping("/companies/{id}/employees")
    public List<Employee> listEmployeesByCompanyId(@PathVariable Long id) {
        return employeeRepository.listEmployeesByCompanyId(id);
    }
}
