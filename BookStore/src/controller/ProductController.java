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
public class ProductController extends BaseController{

    public ProductController(Connection connect) {
        super(connect);
    }
    
    public void showProductEditor() {
        //makeMenuHeader("Products infomation Editor");
        //showAll();
        makeMenuRow("   1.Add Product");
        makeMenuRow("   2.Edit Product");
        makeMenuRow("   3.Delete Product");
        makeMenuRow("   4.Watch Product Detail");
        makeMenuRow("   5.Show all Products");
        makeMenuRow("   6.Back to previous page");
        makeMenuFooter();
    }
    
    public void menu(){
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
                    break;
                default:
                    makeRow("Option is invalid!");
                    break;
            }
        } while (choice != 6);
    }
    
    
}
