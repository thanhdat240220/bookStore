/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Employee;
import model.Order;

/**
 *
 * @author T440s
 */
public class UserController extends BaseController {

    protected Statement _statement;

    public UserController(Connection connect) {
        super(connect);
        try {
            _statement = connect.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Employee getEmployee(String where) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        try {
            ResultSet rs = _statement.executeQuery("select * "
                    + "from employee Where (1=1) And " + where);
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setAccountName(rs.getString("AccountName"));
                employee.setAddress(rs.getString("_address"));
                employee.setDate_of_birth(rs.getDate("Date_of_birth"));
                employee.setName(rs.getString("name"));
                employee.setPhone(rs.getString("phone"));
                employee.setPassword(rs.getString("_password"));
                employee.setStatus(rs.getInt("status"));
                employees.add(employee);
            }
        } catch (SQLException ex) {
        }
        return employees.size() > 0 ? employees.get(0) : null;
    }

    public Customer getCustomer(String where) {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        try {
            ResultSet rs = _statement.executeQuery("select * "
                    + "from customer Where (1=1) And " + where);
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("Name"));
                customer.setAddress(rs.getString("_address"));
                customer.setPhone(rs.getString("phone"));
                customers.add(customer);
            }
        } catch (SQLException ex) {
        }
        return customers.size() > 0 ? customers.get(0) : null;
    }
}
