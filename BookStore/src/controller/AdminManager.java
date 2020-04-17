/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;

/**
 *
 * @author T440s
 */
public class AdminManager extends BaseController {

    private ProductController _ProductController;
    private AuthorController _AuthorController;
    private OrderController _OrderController;

    public AdminManager(Connection connect) {
        super(connect);
        _ProductController = new ProductController(connect);
        _AuthorController = new AuthorController(connect);
        _OrderController = new OrderController(connect);
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
                    _OrderController.menu();
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
