package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();

    static {
        companies.add(new Company(1L, "Company A"));
        companies.add(new Company(2L, "Company B"));
        companies.add(new Company(3L, "Company C"));
    }

    public List<Company> listAllCompanies() {
        return companies;
    }

    public Company findCompanyById(Long id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> listCompaniesByPage(Long pageNumber, Long pageSize) {
        return companies.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company addCompany(Company company) {
        Long id = generateId();

        Company toBeSavedCompany = new Company(id, company.getName());
        companies.add(toBeSavedCompany);
        return toBeSavedCompany;
    }

    public Long generateId() {
        return companies.stream()
                .mapToLong(Company::getId)
                .max()
                .orElse(0L) + 1;
    }

    public Company updateCompany(Long id, Company updatedCompany) {
        Company companyToUpdate = companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);

        companyToUpdate.setName(updatedCompany.getName());

        return companyToUpdate;
    }

    public void deleteCompany(Long id) {
        Company companyToDelete = companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);

        companies.remove(companyToDelete);
    }
}
