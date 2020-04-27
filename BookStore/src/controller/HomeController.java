/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.DAOProduct;
import model.Book;

import java.sql.Connection;
import java.util.Vector;

/**
 * @author T440s
 */
public class HomeController extends BaseController {
    DAOProduct daoProduct;

    public HomeController(Connection connect) {
        super(connect);
        daoProduct = new DAOProduct(connect);
        this.connection = connect;
    }


    public void menu() {
        int choice;
        do {
            makeMenuHeader("System management window");
            makeMenuRow("1. Find Book by Name.");
            makeMenuRow("2. Filter Book by Category.");
            makeMenuRow("3. Show Cart");
            makeMenuRow("4. Add product to cart.");
            makeMenuRow("5. Start Order.");
            makeMenuRow("6. Back to main menu");
            makeMenuFooter();
            choice = enterNumber("an option");
            switch (choice) {
                case 1:
                    // find book by name
                    System.out.println("    Find Book by Name".toUpperCase());
                    String nameToSearch = enterString("name to search");
                    Vector<Book> vector = daoProduct.searchByName(nameToSearch);

                    for (Book book : vector) {
                        String name = book.getName();
                        String category = daoProduct.getCategory(book.getCategory_id());
                        String author = daoProduct.getAuthorName(book.getAuthor_id());
                        String contentSummary = book.getContent_summary();
                        int publishYear = book.getPublish_year();
                        double price = book.getPrice();
                        int quantity = book.getQuantity();
                        String size = book.getSize();
                        String weight = book.getWeight();

                        System.out.println("Name: " + name + "\t | \t Type: " + category + "\t | \t Author: " + author + "\t | \t Content Summary: " + contentSummary + "\t | \t Publish Year: " + publishYear + "\t | \t Price: " + price + "\t | \t Quantity: " + quantity + "\t | \t Size: " + size + "\t | \t Weight: " + weight);
                        System.out.println("                -----------------------");

                    }
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
        } while (choice != 6);
    }

}
