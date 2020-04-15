/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import controller.AdminManager;
import controller.BaseController;
import controller.HomeController;
import controller.OrderController;
import controller.ProductController;
import controller.UserController;
import java.sql.Connection;
import model.DBConnection;

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
                   // _userController.login();
                    break;
                case 2:
                    //_homeController.manageMenu();
                    break;
                case 3:
                    //_quickSearchManager.showMenu();
                    break;
                case 4:
                    //_productController.manageFilterMenu();
                    break;
                case 5:
                    _adminManager.menu();
                case 6:
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
