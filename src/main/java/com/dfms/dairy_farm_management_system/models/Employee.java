package com.dfms.dairy_farm_management_system.Models;

import java.util.Date;

public class Employee {
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

    public Employee(String firstName, String lastName, char gender, String cin, String email, int phone, String adresse, float salary, Date recruitmentDate, String contractType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.cin = cin;
        this.email = email;
        this.phone = phone;
        this.adresse = adresse;
        this.salary = salary;
        this.recruitmentDate = recruitmentDate;
        this.contractType = contractType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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
