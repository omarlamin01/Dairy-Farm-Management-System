package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Employee implements Model {
    private int id;
    private String first_name;
    private String last_name;
    private String gender;
    private String cin;
    private String email;
    private String phone;
    private String address;
    private float salary;
    private Date recruitment_date;
    private String contract_type;
    private Date updated_at;
    private Date created_at;

    public Employee() {
    }

    public Employee(int id, String first_name, String last_name, String gender, String cin, String email, String phone, String adresse, float salary, Date recruitment_date, String contract_type, Date updated_at, Date created_at) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.cin = cin;
        this.email = email;
        this.phone = phone;
        this.address = adresse;
        this.salary = salary;
        this.recruitment_date = recruitment_date;
        this.contract_type = contract_type;
        this.updated_at = updated_at;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public Date getRecruitmentDate() {
        return recruitment_date;
    }

    public void setRecruitmentDate(Date recruitment_date) {
        this.recruitment_date = recruitment_date;
    }

    public String getContractType() {
        return contract_type;
    }

    public void setContractType(String contract_type) {
        this.contract_type = contract_type;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean save() {
        
    }

    @Override
    public boolean update() {

    }

    @Override
    public Class read(String id) {

        return null;
    }

    @Override
    public boolean delete(String id) {

    }
}
