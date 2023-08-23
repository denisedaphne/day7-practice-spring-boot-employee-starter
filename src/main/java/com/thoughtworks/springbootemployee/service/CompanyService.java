package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public Company create(Company company) {
        return companyRepository.addCompany(company);
    }

    public void delete(Long companyId) {
        companyRepository.deleteCompany(companyId);
    }

    public Company update(Long companyId, Company updatedCompany) {
        Company company = companyRepository.findCompanyById(companyId);
        if (company == null) {
            throw new CompanyNotFoundException();
        }

        return companyRepository.updateCompany(companyId, updatedCompany);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    public Company findCompanyById(Long companyId) {
        return companyRepository.findCompanyById(companyId);
    }

    public List<Employee> listEmployeesByCompanyId(Long companyId) {
        return employeeRepository.listEmployeesByCompanyId(companyId);
    }

    public List<Company> listCompaniesByPage(Long pageNumber, Long pageSize) {
        return companyRepository.listCompaniesByPage(pageNumber, pageSize);
    }

    public Company addCompany(Company company) {
        return companyRepository.addCompany(company);
    }

    public Company updateCompany(Long companyId, Company updatedCompany) {
        return companyRepository.updateCompany(companyId, updatedCompany);
    }

    public void deleteCompany(Long companyId) {
        companyRepository.deleteCompany(companyId);
    }
}
