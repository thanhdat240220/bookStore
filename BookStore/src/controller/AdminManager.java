/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import model.Employee;

/**
 *
 * @author T440s
 */
public class AdminManager extends BaseController {

    private ProductController _ProductController;
    private AuthorController _AuthorController;
    private OrderController _OrderController;
    private UserController _UserController;
    protected Employee _employee;
    protected Statement statement;

    public AdminManager(Connection connect) {
        super(connect);
        _ProductController = new ProductController(connect);
        _AuthorController = new AuthorController(connect);
        _OrderController = new OrderController(connect);
        _UserController = new UserController(connect);
        try {
            statement = connect.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void login() {
        makeMenuHeader("Require account admin");
        boolean isValid = false;
        do {
            String name = enterString("AccountName");
            String pass = enterString("Password");
            _employee = _UserController.getEmployee("accountName = '" + name.trim() + "' and _password='" + pass.trim() + "'");
            if (_employee != null) {
                makeMenuRow("Welcome Admin: " + _employee.getName());
                menu();
                isValid = true;
            } else {
                System.out.println("Wrong user name or password!");
                System.out.print("Retry? (y/n): ");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("y")) {
                    break;
                }
            }
        } while (!isValid);
    }


    public void menu() {
        int choice;
        do {
            makeMenuHeader("System management window");
            makeMenuRow("1.Manage Products");
            makeMenuRow("2.Manage Author");
            makeMenuRow("3.Manage Order");
            makeMenuRow("4.Sign out and back to main menu");
            makeMenuFooter();
            choice = enterNumber("an option");
            switch (choice) {
                case 1:
                    _ProductController.menu();
                    break;
                case 2:
                    _AuthorController.menu();
                    break;
                case 3:
                    _OrderController.menu(_employee);
                    break;
                case 4:
                    //logout();
                    back();
                    break;
                default:
                    System.out.println("¤ Option is invalid!");
                    break;
            }
        } while (choice != 4);
    }
}
