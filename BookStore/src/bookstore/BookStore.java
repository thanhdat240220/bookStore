/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import controller.*;
import model.DBConnection;

import java.sql.Connection;

/**
 *
 * @author T440s
 */
public class BookStore {

    /**
     * @param args the command line arguments
     */
    static DBConnection connection = new DBConnection();
    
    public static void main(String[] args) {
        Connection conn = connection.Connecting();
        
//        BaseController _baseController = new BaseController(conn);
//        HomeController _homeController = new HomeController(conn);
//        ProductController _productController = new ProductController(conn);
//        UserController _userController = new UserController(conn);
//        OrderController _orderController = new OrderController(conn);
//        AdminManager _adminManager = new AdminManager(conn);

        AdminManager _adminManager = new AdminManager(conn);
        BaseController _baseController = new BaseController(conn);
        HomeController _homeController = new HomeController(conn);
        ProductController _productController = new ProductController(conn);
        UserController _userController = new UserController(conn);
        OrderController _orderController = new OrderController(conn);
        
        int selected;

        do {
            _baseController.showMainMenu();
            selected = _baseController.enterNumber("an option");

            switch (selected) {
                case 1:
                    _homeController.menuHome();
                    break;
                case 2:
                    _adminManager.login();
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Option is invalid!");
                    break;
            }
        } while (selected != 5);
        connection.close();
    }
    
}
