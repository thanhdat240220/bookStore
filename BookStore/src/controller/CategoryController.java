/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Book;
import model.Category;
import java.sql.Statement;

/**
 *
 * @author T440s
 */
public class CategoryController extends BaseController {

    private Statement _statement;

    public CategoryController(Connection connect) {
        super(connect);
        try {
            _statement = connect.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            // showProductEditor();
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

    public void showCategory(String wheres) {
        ArrayList<Category> categories = getCategories(wheres);
        makeMenuHeader("Category List");
        if (categories.size() > 0) {
            for (Category category : categories) {
                makeRow(category.id + ". " + category.name);
            }
        }else{
            makeRow("Not Found");
        }
    }

    public ArrayList<Category> getCategories(String wheres) {
        ArrayList<Category> categories = new ArrayList<Category>();
        if (Empty(wheres)) {
            wheres = "(1=1)";
        }
        try {
            String sql = "SELECT * FROM category WHERE" + wheres;
            ResultSet rs = _statement.executeQuery(sql);
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("Id"));
                category.setName(rs.getString("Name"));
                categories.add(category);
            }
        } catch (SQLException ex) {
        }
        return categories;
    }
}
