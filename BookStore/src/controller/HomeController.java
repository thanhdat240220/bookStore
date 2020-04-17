/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author T440s
 */
public class HomeController extends BaseController {

    public HomeController(Connection connect) {
        super(connect);

    }

    public void menu() {
        int choice;
        do {
            makeMenuHeader("System management window");
            makeMenuRow("1.Manage Products");
            makeMenuRow("2.Manage Author");
            makeMenuRow("3.Manage Order");
            makeMenuRow("4.Back to main menu");
            makeMenuFooter();
            choice = enterNumber("an option");
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    //_billManager.manageMenu();
                    break;
                case 4: {
                    
                }
                break;
                default:
                    System.out.println("¤ Option is invalid!");
                    break;
            }
        } while (choice != 4);
    }

}
