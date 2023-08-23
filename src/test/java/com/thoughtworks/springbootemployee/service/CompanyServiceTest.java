package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private EmployeeRepository employeeRepository;

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

}
