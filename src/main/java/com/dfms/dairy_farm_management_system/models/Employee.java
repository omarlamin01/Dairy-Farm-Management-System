package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Employee extends Personne{
    private int id_employee;
    private float salary;
    private Date recruitmentDate;
    private String contractType;

    @Override
    public void setId(int id) {
        this. id_employee = id;
    }

    @Override
    public int getId() {
        return  id_employee;
    }

    public Employee(int id, String firstName, String lastName, char gender, String cin, String email, int phone, String adresse, int id1, float salary, Date recruitmentDate, String contractType) {
        super(id, firstName, lastName, gender, cin, email, phone, adresse);
        this. id_employee = id1;
        this.salary = salary;
        this.recruitmentDate = recruitmentDate;
        this.contractType = contractType;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public Date getRecruitmentDate() {
        return recruitmentDate;
    }

    public void setRecruitmentDate(Date recruitmentDate) {
        this.recruitmentDate = recruitmentDate;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
}
