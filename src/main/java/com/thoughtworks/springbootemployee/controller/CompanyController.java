package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/companies")
@RestController
@SuppressWarnings("all")
public class CompanyController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    @GetMapping("/{companyId}")
    public Company findCompanyById(@PathVariable Long companyId) {
        return companyRepository.findCompanyById(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> listEmployeesByCompanyId(@PathVariable Long companyId) {
        return employeeRepository.listEmployeesByCompanyId(companyId);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listCompaniesByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return companyRepository.listCompaniesByPage(pageNumber, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        return companyRepository.addCompany(company);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable Long companyId, @RequestBody Company companyName) {
        return companyRepository.updateCompany(companyId, companyName);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable Long companyId) {
        companyRepository.deleteCompany(companyId);
    }
}
