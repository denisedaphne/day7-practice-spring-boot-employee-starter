package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();
    static{
        companies.add(new Company(1L, "Spring"));
        companies.add(new Company(2L, "OOCL"));
        companies.add(new Company(3L, "Google"));
        companies.add(new Company(4L, "COSCO"));
        companies.add(new Company(5L, "ITA"));
    }


    public List<Company> listAllCompanies() {
        return companies;
    }

    public Company findById(Long id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> listByPage(Long pageNumber, Long pageSize) {
        return companies.stream()
                .skip((pageNumber-1)*pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company addCompany(Company company) {
        Long companyId = generateCompanyId();
        Company companyToBeAdded = new Company(companyId, company.getName());
        companies.add(companyToBeAdded);
        return companyToBeAdded;
    }

    private Long generateCompanyId() {
        return companies.stream()
                .mapToLong(Company::getId)
                .max()
                .orElse(0L) + 1L;
    }

    public void deleteCompany(Company company) {
        companies.remove(company);
    }
}
