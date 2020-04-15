/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author T440s
 */
public class Employee {
    public String accountName;
    public String password;
    public String name;
    public Date date_of_birth;
    public String address;
    
    public String getAccountName() {
        return accountName;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getStatus() {
        return status;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public String phone;
    public int status;
}
