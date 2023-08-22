package com.thoughtworks.springbootemployee.controller;

public class Employee {
    private final Long employeeId;
    private final String employeeName;
    private Integer age;
    private final String gender;
    private Integer salary;
    private final Long companyId;

    public Employee(Long employeeId, String employeeName, Integer age, String gender, Integer salary, Long companyId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Long getCompanyId() {
        return companyId;
    }
}
