package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.controller.Const;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();

    static {
        companies.add(new Company(1L, "Orient Overseas Container Line"));
        companies.add(new Company(2L, "ThoughtWorks"));
        companies.add(new Company(3L, "Wells Fargo"));
        companies.add(new Company(4L, "Google"));
        companies.add(new Company(5L, "Procter & Gamble"));
        companies.add(new Company(6L, "Unilever"));
    }

    public List<Company> listAllCompanies() {
        return companies;
    }

    public Company findCompanyById(Long companyId) {
        return companies.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> listCompaniesByPage(Long pageNumber, Long pageSize) {
        return companies.stream()
                .skip((pageNumber - Const.PAGE_ONE) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company addCompany(Company company) {
        Long companyId = generateCompanyId();

        Company toBeSavedCompany = new Company(companyId, company.getCompanyName());
        companies.add(toBeSavedCompany);
        return toBeSavedCompany;
    }

    public Long generateCompanyId() {
        return companies.stream()
                .mapToLong(Company::getCompanyId)
                .max()
                .orElse(Const.START_ID_MINUS_ONE) + Const.ID_INCREMENT;
    }

    public Company updateCompany(Long companyId, Company updatedCompany) {
        Company companyToUpdate = companies.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);

        companyToUpdate.setCompanyName(updatedCompany.getCompanyName());

        return companyToUpdate;
    }

    public void deleteCompany(Long id) {
        Company companyToDelete = companies.stream()
                .filter(company -> company.getCompanyId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);

        companies.remove(companyToDelete);
    }
}
