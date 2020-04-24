/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.DAOProduct;
import model.Book;

import java.sql.Connection;

/**
 * @author T440s
 */
public class ProductController extends BaseController {

    DAOProduct daoProduct;

    public ProductController(Connection connect) {
        super(connect);
        daoProduct = new DAOProduct(connect);
        this.connection = connect;
    }

    public void showProductEditor() {
        //makeMenuHeader("Products information Editor");
        //showAll();
        makeMenuRow("   1.Add Product");
        makeMenuRow("   2.Edit Product");
        makeMenuRow("   3.Delete Product");
        makeMenuRow("   4.Watch Product Detail By ID");
        makeMenuRow("   5.Show all Products");
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
                    System.out.println("    Add a New Book".toUpperCase());
                    Book book = new Book();
                    int categoryId = enterNumber("Category ID");

                    while (true) {
                        if (!daoProduct.checkBookCategory().contains(categoryId)) {
                            System.out.println("Category not Found! Please try again!");
                            categoryId = enterNumber("Category ID");
                        } else {
                            book.setCategory_id(categoryId);
                            int statusId = enterNumber("Status ID");
                            book.setStatus_id(statusId);
                            int authorId = enterNumber("Author ID");
                            book.setAuthor_id(authorId);
                            String name = enterString("Book Name");
                            book.setName(name);
                            String contentSummary = enterString("Content Summery");
                            book.setContent_summary(contentSummary);
                            int publishYear = enterNumber("Publish Year");
                            book.setPublish_year(publishYear);
                            double price = enterRealNumber("Price");
                            book.setPrice(price);
                            int quantity = enterNumber("Quantity");
                            book.setQuantity(quantity);
                            String size = enterString("Book Size");
                            book.setSize(size);
                            String weight = enterString("Book Weight");
                            book.setWeight(weight);

                            daoProduct.addProduct(book);
                            System.out.println("Added successfully!");
                            break;
                        }
                    }
                    break;
                case 2:
                    //edit();
                    System.out.println("    Edit a Book".toUpperCase());
                    Book bookToEdit = new Book();
                    int idToEdit = enterNumber("ID to Edit");

                    while (true) {
                        if (daoProduct.checkExistBook(idToEdit).equals("")) {
                            System.out.println("Book not Found! Please try again!");
                            idToEdit = enterNumber("ID to Edit");
                        } else {
                            bookToEdit.setId(idToEdit);
                            int categoryIdToEdit = enterNumber("New Category ID");

                            while (true) {
                                if (!daoProduct.checkBookCategory().contains(categoryIdToEdit)) {
                                    System.out.println("Category not Found! Please try again!");
                                    categoryIdToEdit = enterNumber("Category ID");
                                } else {
                                    bookToEdit.setCategory_id(categoryIdToEdit);
                                    int statusIdToEdit = enterNumber("New Status ID");
                                    bookToEdit.setStatus_id(statusIdToEdit);
                                    int authorIdToEdit = enterNumber("New Author ID");
                                    bookToEdit.setAuthor_id(authorIdToEdit);
                                    String nameToEdit = enterString("New Book Name");
                                    bookToEdit.setName(nameToEdit);
                                    String contentSummaryToEdit = enterString("New Content Summery");
                                    bookToEdit.setContent_summary(contentSummaryToEdit);
                                    int publishYearToEdit = enterNumber("New Publish Year");
                                    bookToEdit.setPublish_year(publishYearToEdit);
                                    double priceToEdit = enterRealNumber("New Price");
                                    bookToEdit.setPrice(priceToEdit);
                                    int quantityToEdit = enterNumber("New Quantity");
                                    bookToEdit.setQuantity(quantityToEdit);
                                    String sizeToEdit = enterString("New Book Size");
                                    bookToEdit.setSize(sizeToEdit);
                                    String weightToEdit = enterString("New Book Weight");
                                    bookToEdit.setWeight(weightToEdit);
                                    daoProduct.editProduct(bookToEdit);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                case 3:
                    //delete();
                    System.out.println("    Delete a Book".toUpperCase());
                    int idToDelete = enterNumber("ID to Delete");
                    while (true) {
                        if (daoProduct.checkExistBook(idToDelete).equals("")) {
                            System.out.println("Book not Found! Please try again!");
                            idToDelete = enterNumber("ID to Delete");
                        } else {
                            daoProduct.removeProduct(idToDelete);
                            break;
                        }
                    }
                    break;
                case 4:
                    //showDetailById();
                    System.out.println("    Show a Book by ID".toUpperCase());
                    int idToShow = enterNumber("ID to Show");
                    daoProduct.showABook(idToShow);
                    break;
                case 5:
                    //clearConsole();
                    System.out.println("    Show all Books".toUpperCase());
                    daoProduct.showAllBook();
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
