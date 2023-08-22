package com.thoughtworks.springbootemployee.controller;

import java.util.ArrayList;
import java.util.List;

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
}
