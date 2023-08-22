package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
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

    @GetMapping("/companies")
    public List<Company> listCompaniesByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return companyRepository.listCompaniesByPage(pageNumber, pageSize);
    }

    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        return companyRepository.addCompany(company);
    }

    @PutMapping("/companies/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company company) {
        return companyRepository.updateCompany(id, company);
    }

    @DeleteMapping("/companies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable Long id) {
        companyRepository.deleteCompany(id);
    }
}