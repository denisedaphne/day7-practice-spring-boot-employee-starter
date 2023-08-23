package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyApiTests {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvcClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUpCompanyData() {
        companyRepository.cleanAll();
    }

    @Test
    void should_return_all_given_companies_when_performed_get_companies() throws Exception {
        //given
        Company company = companyRepository.addCompany(new Company(1L, "Example Company"));

        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].companyId").value(company.getCompanyId()))
                .andExpect(jsonPath("$[0].companyName").value(company.getCompanyName()));
    }

    @Test
    void should_return_company_when_perform_post_company_given_a_new_company_with_JSON_format() throws Exception {
        //given
        Company newCompany = new Company(1L, "New Company");

        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCompany)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyId").exists())
                .andExpect(jsonPath("$.companyName").value(newCompany.getCompanyName()));
    }

    @Test
    void should_return_page_companies_when_perform_get_given_companies_with_page() throws Exception {
        //given
        Company company1 = companyRepository.addCompany(new Company(1L, "Company 1"));
        Company company2 = companyRepository.addCompany(new Company(2L, "Company 2"));
        Company company3 = companyRepository.addCompany(new Company(3L, "Company 3"));

        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies")
                        .param("pageNumber", "1")
                        .param("pageSize", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].companyId").value(company1.getCompanyId()))
                .andExpect(jsonPath("$[1].companyId").value(company2.getCompanyId()))
                .andExpect(jsonPath("$[2].companyId").value(company3.getCompanyId()));
    }
}

