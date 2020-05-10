/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOCategory;
import model.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author T440s
 */
public class CategoryController extends BaseController {

    private Statement _statement;
    DAOCategory daoCategory;

    public CategoryController(Connection connect) {
        super(connect);
        daoCategory = new DAOCategory(connect);
        this.connection = connect;

        try {
            _statement = connect.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showProductEditor() {
        //makeMenuHeader("Products information Editor");
        //showAll();
        makeMenuRow("Options:");
        makeMenuRow("   1.Add Category");
        makeMenuRow("   2.Edit Category");
        makeMenuRow("   3.Delete Category");
        makeMenuRow("   4.Show A Category By ID");
        makeMenuRow("   5.Show All Categories");
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
                    addCategory();
                    break;
                case 2:
                    editCategory();
                    break;
                case 3:
                    deleteCategory();
                    break;
                case 4:
                    showACategory();
                    break;
                case 5:
                    showAllCategories();
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

    public void addCategory() {
        makeMenuHeader("Add a New Category");
        Category category = new Category();
        String name = enterString("Name");
        category.setName(name);
        if (daoCategory.checkDuplicateCategory().name.equals(name)) {
            System.out.println("Category already existed!");
        } else {
            daoCategory.addCategory(category);
            makeMenuFooter();
        }
    }

    public void editCategory() {
        makeMenuHeader("Edit a Category");
        Category category = new Category();
        int id = enterNumber("ID to Edit");

        while (true) {
            if (daoCategory.checkExistedCategory(id).equals("")) {
                System.out.println("Author not Found! Please try again!");
                System.out.print("Retry? (y/n): ");
                String choice = scanner.nextLine();

                if (!choice.equalsIgnoreCase("y")) {
                    break;
                } else {
                    id = enterNumber("ID to Edit");
                }
            } else {
                category.setId(id);
                String name = enterString("New Name");
                category.setName(name);

                daoCategory.editCategory(category);
                makeMenuFooter();
                break;
            }
        }
    }

    public void deleteCategory() {
        makeMenuHeader("Delete a Category");
        int id = enterNumber("ID to Delete");

        while (true) {
            if (daoCategory.checkExistedCategory(id).equals("")) {
                System.out.println("Category not Found! Please try again!");
                System.out.print("Retry? (y/n): ");
                String choice = scanner.nextLine();

                if (!choice.equalsIgnoreCase("y")) {
                    break;
                } else {
                    id = enterNumber("ID to Delete");
                }
            } else {
                daoCategory.removeCategory(id);
                makeMenuFooter();
                break;
            }
        }
    }

    public void showACategory() {
        makeMenuHeader("Show a Category by ID");
        int id = enterNumber("ID to Show");
//        daoCategory.showACategory(id);
//        makeMenuFooter();
        while (true) {
            if (daoCategory.checkExistedCategory(id).equals("")) {
                System.out.println("Category not Found! Please try again!");
                System.out.print("Retry? (y/n): ");
                String choice = scanner.nextLine();

                if (!choice.equalsIgnoreCase("y")) {
                    break;
                } else {
                    id = enterNumber("ID to Show");
                }
            } else {
                daoCategory.removeCategory(id);
                makeMenuFooter();
                break;
            }
        }
    }

    public void showAllCategories() {
        makeMenuHeader("Show all Categories");
        daoCategory.showAllCategories();
        makeMenuFooter();
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
