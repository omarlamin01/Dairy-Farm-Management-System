package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Employee extends Model {
    private int id_employee;
    private String  firstName;
    private String lastName;
    private char gender;
    private String cin;
    private String email;
    private int phone;
    private String adresse;
    private float salary;
    private Date recruitmentDate;
    private String contractType;
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

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
