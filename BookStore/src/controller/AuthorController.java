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
public class AuthorController extends BaseController {

    public AuthorController(Connection connect) {
        super(connect);
    }

    public void showProductEditor() {
        //makeMenuHeader("Products infomation Editor");
        //showAll();
        makeMenuRow("Options:");
        makeMenuRow("   1.Add Author");
        makeMenuRow("   2.Edit Author");
        makeMenuRow("   3.Delete Author");
        makeMenuRow("   4.Watch Author");
        makeMenuRow("   5.Show All Author");
        makeMenuRow("   6.Back to previous page");
        makeMenuFooter();
    }

    public void menu() {
        int choice;
        do {
            showProductEditor();
            choice = enterNumber("an option");
            switch (choice) {
                case 1:
                    //add();
                    break;
                case 2:
                    //edit();
                    break;
                case 3:
                    //delete();
                    break;
                case 4:
                    //showDetailById();
                    break;
                case 5:
                    //clearConsole();
                    break;
                case 6:
                    back();
                    break;
                default:
                    makeRow("Option is invalid!");
                    break;
            }
        } while (choice != 6);
    }
}
