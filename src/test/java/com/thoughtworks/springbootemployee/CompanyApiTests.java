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


}

