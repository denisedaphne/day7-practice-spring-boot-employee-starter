package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
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
public class EmployeeApiTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvcClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUpEmployeeData(){
        employeeRepository.cleanAll();
    }

    @Test
    void should_return_all_given_employees_when_performed_get_employees() throws Exception {
        //given
        Employee alice = employeeRepository.addEmployee(new Employee("Alice", 24, "Female", 9800));
        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(alice.getId()))
                .andExpect(jsonPath("$[0].name").value(alice.getName()))
                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("$[0].salary").value(alice.getSalary()));
    }

    @Test
    void should_return_employee_when_performed_get_employee_given_an_employee_id() throws Exception {
        //given
        Employee alice = employeeRepository.addEmployee(new Employee(1L,"Alice", 24, "Female", 9800, 1L));
        employeeRepository.addEmployee(new Employee(2L,"Bob", 28, "Male", 8000, 1L));

        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + alice.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(alice.getId()))
                .andExpect(jsonPath("$.name").value(alice.getName()))
                .andExpect(jsonPath("$.age").value(alice.getAge()))
                .andExpect(jsonPath("$.gender").value(alice.getGender()))
                .andExpect(jsonPath("$.salary").value(alice.getSalary()));
    }

    @Test
    void should_return_404_not_found_when_perform_get_employee_given_a_not_existing_id() throws Exception {
        //given
        long notExistEmployeeId = 99L;

        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + notExistEmployeeId))
                .andExpect(status().isNotFound());

    }

    @Test
    void should_return_employees_by_given_gender_when_perform_get_employees() throws Exception {
        //given
        Employee alice = employeeRepository.addEmployee(new Employee(1L,"Alice", 24, "Female", 9800, 1L));
        employeeRepository.addEmployee(new Employee(2L,"Bob", 28, "Male", 8000, 1L));

        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees").param("gender", "Female"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(alice.getId()))
                .andExpect(jsonPath("$[0].name").value(alice.getName()))
                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("$[0].salary").value(alice.getSalary()));
    }

    @Test
    void should_return_employee_when_perform_post_employee_given_a_new_employee_with_JSON_format() throws Exception {
        //given
        Employee newEmployee = new Employee(1L,"Alice", 24, "Female", 9000, 1L);

        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(newEmployee.getName()))
                .andExpect(jsonPath("$.age").value(newEmployee.getAge()))
                .andExpect(jsonPath("$.gender").value(newEmployee.getGender()))
                .andExpect(jsonPath("$.salary").value(newEmployee.getSalary()));
    }

    @Test
    void should_return_update_employee_age_and_salary_when_perform_put_given_employee() throws Exception {
        // Given
        Employee alice = employeeRepository.addEmployee(new Employee(1L,"Alice", 24, "Female", 9800, 1L));
        alice.setActive(true);
        Employee updateAlice = new Employee(alice.getId(), "Alice", 25, "Female", 10000, 1L);

        // When // Then
        mockMvcClient.perform(MockMvcRequestBuilders.put("/employees/" + alice.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateAlice)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updateAlice.getId()))
                .andExpect(jsonPath("$.name").value(updateAlice.getName()))
                .andExpect(jsonPath("$.age").value(updateAlice.getAge()))
                .andExpect(jsonPath("$.gender").value(updateAlice.getGender()))
                .andExpect(jsonPath("$.salary").value(updateAlice.getSalary()));
    }

    @Test
    void should_delete_employee_when_perform_delete_given_an_employee_id() throws Exception {
        //given
        Employee alice = employeeRepository.addEmployee(new Employee(1L, "Alice", 24, "Female", 9800, 1L));
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.delete("/employees/" + alice.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_page_employees_when_perform_get_given_employees_with_page() throws Exception {
        //given
        Employee alice = employeeRepository.addEmployee(new Employee(1L,"Alice", 24, "Female", 9800, 1L));
        Employee bob = employeeRepository.addEmployee(new Employee(1L,"Bob", 26, "Male", 8000, 1L));
        Employee charles = employeeRepository.addEmployee(new Employee(1L, "Charles", 28, "Male", 9000, 1L));

        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees")
                        .param("pageNumber", "1")
                        .param("pageSize", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(alice.getId()))
                .andExpect(jsonPath("$[1].id").value(bob.getId()))
                .andExpect(jsonPath("$[2].id").value(charles.getId()));

    }
}
