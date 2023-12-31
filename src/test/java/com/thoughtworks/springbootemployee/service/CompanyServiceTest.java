package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void cleanUpCompanyData() {
        when(companyRepository.generateCompanyId()).thenReturn(1L);
    }

    @Test
    void should_return_company_when_create_given_company() {
        // given
        Company newCompany = new Company(null, "New Company");
        Company companyWithId = new Company(1L, "New Company");
        when(companyRepository.addCompany(any(Company.class))).thenReturn(companyWithId);

        // when
        Company createdCompany = companyService.create(newCompany);

        // then
        assertEquals(companyWithId, createdCompany);
        verify(companyRepository, times(1)).addCompany(any(Company.class));
    }

    @Test
    void should_invoke_delete_company_when_delete_given_company_id() {
        // given
        Long companyId = 1L;

        // when
        companyService.delete(companyId);

        // then
        verify(companyRepository, times(1)).deleteCompany(companyId);
    }

    @Test
    void should_return_updated_company_when_update_given_company_id_updated_company() {
        // given
        Long companyId = 1L;
        Company updatedCompany = new Company(1L, "Updated Company");

        when(companyRepository.findCompanyById(companyId)).thenReturn(new Company(companyId, "Old Company"));
        when(companyRepository.updateCompany(companyId, updatedCompany)).thenReturn(updatedCompany);

        // when
        Company result = companyService.update(companyId, updatedCompany);

        // then
        assertEquals(updatedCompany, result);
        verify(companyRepository, times(1)).findCompanyById(companyId);
        verify(companyRepository, times(1)).updateCompany(companyId, updatedCompany);
    }

    @Test
    void should_throw_exception_when_update_given_invalid_company_id() {
        // given
        Long companyId = 1L;
        Company updatedCompany = new Company(1L, "Updated Company");

        when(companyRepository.findCompanyById(companyId)).thenReturn(null);

        // when then
        assertThrows(CompanyNotFoundException.class, () -> companyService.update(companyId, updatedCompany));
    }

    @Test
    void should_return_list_companies_when_get_all_companies() {
        // given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1L, "Company 1"));
        companies.add(new Company(2L, "Company 2"));

        when(companyRepository.listAllCompanies()).thenReturn(companies);

        // when
        List<Company> result = companyService.getAllCompanies();

        // then
        assertEquals(companies, result);
        verify(companyRepository, times(1)).listAllCompanies();
    }
}
