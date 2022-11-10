package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Employee extends Model {
    private int id_employee;
    private String firstName;
    private String lastName;
    private char gender;
    private String cin;
    private String email;
    private String phone;
    private String adresse;
    private float salary;
    private Date recruitmentDate;
    private String contractType;
    private Date updated_at;
    private Date created_at;

    public Employee(int id_employee, String firstName, String lastName, char gender, String cin, String email, String phone, String adresse, float salary, Date recruitmentDate, String contractType, Date updated_at, Date created_at) {
        this.id_employee = id_employee;
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
        this.updated_at = updated_at;
        this.created_at = created_at;
    }

    public Employee() { }

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
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

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
