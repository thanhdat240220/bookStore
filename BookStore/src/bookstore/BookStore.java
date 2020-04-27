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
        
        BaseController _baseController = new BaseController(conn);
        HomeController _homeController = new HomeController(conn);
        ProductController _productController = new ProductController(conn);
        UserController _userController = new UserController(conn);
//        OrderController _orderController = new OrderController(conn);
        AdminManager _adminManager = new AdminManager(conn);
        
        int selected;
        do {
            _baseController.showMainMenu();
            selected = _baseController.enterNumber("an option");
            
            switch (selected) {
                case 1:
                   // _userController.login();
                    break;
                case 2:
                    //_homeController.manageMenu();
                    // search by name and category
                    _homeController.menu();
                    break;
                case 3:
                    //_quickSearchManager.showMenu();
                    break;
                case 4:
                    //_productController.manageFilterMenu();
                    break;
                case 5:
                    _adminManager.menu();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Option is invalid!");
                    break;
            }
        } while (selected != 6);
        connection.close();
    }
    
}
