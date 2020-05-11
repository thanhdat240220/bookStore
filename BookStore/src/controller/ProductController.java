/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOAuthor;
import dao.DAOProduct;
import model.Author;
import model.Book;

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
public class ProductController extends BaseController {

    DAOProduct daoProduct;
    DAOAuthor daoAuthor;
    private Statement _statement;
    private CategoryController _categoryController;
    private OrderController _orderController;
    private AuthorController _authorController;

    public ProductController(Connection connect) {
        super(connect);
        daoProduct = new DAOProduct(connect);
        daoAuthor = new DAOAuthor(connect);
        _categoryController = new CategoryController(connect);
        _orderController = new OrderController(connect);
        _authorController = new AuthorController(connect);
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
        makeMenuHeader("Manage Book");
        makeMenuRow("   1.Add Product");
        makeMenuRow("   2.Edit Product");
        makeMenuRow("   3.Delete Product");
        makeMenuRow("   4.Watch Product Detail By ID");
        makeMenuRow("   5.Show all Products");
        makeMenuRow("   6.Add Author To Product");
        makeMenuRow("   7.Back to previous page");
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
                    addBook();
                    break;
                case 2:
                    //edit();
                    editBook();
                    break;
                case 3:
                    //delete();
                    deleteBook();
                    break;
                case 4:
                    //showDetailById();
                    showABook();
                    break;
                case 5:
                    //clearConsole();
                    showBooks("");
                    break;
                case 6:
                    addAuthorToBook();
                    break;
                case 7:
                    back();
                    break;
                default:
                    makeRow("Option is invalid!");
                    break;
            }
        } while (choice != 6);
    }

    public void addBook() {
        makeMenuRow("Add a New Book");
        Book book = new Book();
        _categoryController.showAllCategories();
        int categoryId = enterNumber("Category ID");

        while (true) {
            if (!daoProduct.checkBookCategory().contains(categoryId)) {
                System.out.println("Category not Found! Please try again!");
                categoryId = enterNumber("Category ID");
            } else {
                book.setCategory_id(categoryId);
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
                makeMenuFooter();
                break;
            }
        }
    }

    public void editBook() {
        makeMenuRow("Edit a Book");
        Book book = new Book();
        int id = enterNumber("Book ID to Edit");

        while (true) {
            if (daoProduct.checkExistBook(id).equals("")) {
                System.out.println("Book not Found! Please try again!");
                System.out.print("Retry? (y/n): ");
                String choice = scanner.nextLine();

                if (!choice.equalsIgnoreCase("y")) {
                    break;
                } else {
                    id = enterNumber("Book ID to Edit");
                }
            } else {
                book.setId(id);
                _categoryController.showAllCategories();
                int categoryId = enterNumber("New Category ID");

                while (true) {
                    if (!daoProduct.checkBookCategory().contains(categoryId)) {
                        System.out.println("Category not Found! Please try again!");
                        categoryId = enterNumber("Category ID");
                    } else {
                        book.setCategory_id(categoryId);
                        String name = enterString("New Book Name");
                        book.setName(name);
                        String contentSummary = enterString("New Content Summery");
                        book.setContent_summary(contentSummary);
                        int publishYear = enterNumber("New Publish Year");
                        book.setPublish_year(publishYear);
                        double price = enterRealNumber("New Price");
                        book.setPrice(price);
                        int quantity = enterNumber("New Quantity");
                        book.setQuantity(quantity);
                        String size = enterString("New Book Size");
                        book.setSize(size);
                        String weight = enterString("New Book Weight");
                        book.setWeight(weight);

                        daoProduct.editProduct(book);
                        System.out.println("Edited successfully!");

                        makeMenuFooter();
                        break;
                    }
                }
                break;
            }
        }
    }

    public void deleteBook() {
        makeMenuHeader("Delete a Book");
        int id = enterNumber("Book ID to Delete");

        while (true) {
            if (daoProduct.checkExistBook(id).equals("")) {
                System.out.println("Book not Found! Please try again!");
            } else if (_orderController.checkBookInOrder(id)) {
                System.out.println("Book In Order! Please try again!");
            } else {
                daoProduct.removeProduct(id);
                System.out.println("Delete successfully!");
                break;
            }

            System.out.print("Retry? (y/n): ");
            String choice = scanner.nextLine();

            if (!choice.equalsIgnoreCase("y")) {
                break;
            } else {
                id = enterNumber("Book ID to Delete");
            }
        }
    }

    public void addAuthorToBook() {

        int book_id = enterNumber("Book Id To Add Authors");
        while (true) {
            if (daoProduct.checkExistBook(book_id).equals("")) {
                System.out.println("Book not Found! Please try again!");
                System.out.print("Retry? (y/n): ");
            } else {

                _authorController.showAllAuthors();
                int author_id = enterNumber("Author Id Add");

                while (true) {

                    if (daoAuthor.checkExistedAuthor(author_id).equals("")) {
                        System.out.println("Author not Found! Please try again!");
                        System.out.print("Retry? (y/n): ");
                    } else if (daoAuthor.checkAuthorBook(book_id, author_id)) {
                        System.out.println("Author added after! Please try again!");
                        System.out.print("Retry? (y/n): ");
                    } else {
                        addAuthorToBookSql(book_id, author_id);
                        System.out.println(" More Add?(y/n)!");
                    }
                    String choice = scanner.nextLine();
                    if (!choice.equalsIgnoreCase("y")) {
                        break;
                    } else {
                        author_id = enterNumber("Author ID Add");
                    }
                }
            }

            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("y")) {
                break;
            } else {
                book_id = enterNumber("Book Id To Add Authors");
            }
        }
    }

    public void addAuthorToBookSql(int book_id, int author_id) {
        try {
            _statement.executeUpdate("INSERT INTO book_store.dbo.book_author\n"
                    + "(book_id, author_id)\n"
                    + "VALUES(" + book_id + ", " + author_id + ");");
            makeRow("Insert Author To Book successful!");
        } catch (SQLException ex) {
            makeRow("Insert Author To Book Fail!");
        }
    }

    public void showABook() {

        int id = enterNumber("Book ID to Show");

        while (true) {
            if (daoProduct.checkExistBook(id).equals("")) {
                System.out.println("Book not Found! Please try again!");
                System.out.print("Retry? (y/n): ");
                String choice = scanner.nextLine();

                if (!choice.equalsIgnoreCase("y")) {
                    break;
                } else {
                    id = enterNumber("Book ID to Show");
                }
            } else {
                daoProduct.showABook(id);
                break;
            }
        }
    }

    public void showAllBooks() {
        makeMenuHeader("Show all Books");
        daoProduct.showAllBook();
        makeMenuFooter();
    }

    public void showBooks(String wheres) {
        ArrayList<Book> books = getBooks(wheres);
        makeMenuHeader("Book List");
        if (books.size() > 0) {
            funcShowBook(books);
        } else {
            makeRow("Not Found");
        }
    }

    public void funcShowBook(ArrayList<Book> books) {
        for (Book book : books) {
            makeRow("Book Id:" + book.getId());
            makeRow("Book Name:" + book.getName());
            makeRow("Price:" + book.getPrice() + " VND");
            makeRow("Publish Year:" + book.getPublish_year());
            makeRow("Content Summary:" + book.getContent_summary());
            makeRow("Quantity:" + book.getQuantity());
            makeRow("Size:" + book.getSize());
            makeRow("Weight:" + book.getWeight());
            if (book.authors != null) {
                makeRow("Author: ");
                for (Author author : book.authors) {
                    makeRow("-" + author.getName());
                }
            } else {
                makeRow("Author: Unauthenticated");
            }
            makeRow("--------------------------");
        }
    }

    public ArrayList<Book> getBooks(String wheres) {
        ArrayList<Book> books = new ArrayList<Book>();
        ArrayList<Book> bookHasAuthor = getBooksHasAuthor(wheres);
        ArrayList<Book> bookHasNotAuthor = getBookHasNotAuthor(wheres);

        books.addAll(bookHasAuthor);
        books.addAll(bookHasNotAuthor);
        return books;
    }

    public ArrayList<Book> getBooksHasAuthor(String wheres) {
        ArrayList<Book> books = new ArrayList<Book>();
        if (Empty(wheres)) {
            wheres = "(1=1)";
        }
        try {
            ResultSet rs = _statement.executeQuery("select b.*,a.id as author_id,a.name as author_name from book b "
                    + "inner join book_author ba on ba.book_id = b.id "
                    + "left join author a on a.id = ba.author_id where " + wheres);
            ArrayList<Author> authors = new ArrayList<Author>();

            while (rs.next()) {
                Book book = new Book();
                Author author = new Author();
                book.setId(rs.getInt("Id"));
                book.setName(rs.getString("Name"));
                book.setCategory_id(rs.getInt("Category_id"));
                book.setContent_summary(rs.getString("Content_summary"));
                book.setPrice(rs.getDouble("Price"));
                book.setPublish_year(rs.getInt("Publish_year"));
                book.setQuantity(rs.getInt("Quantity"));
                book.setWeight(rs.getString("Weight"));
                book.setSize(rs.getString("Size"));

                if (books.size() > 0 && books.get(books.size() - 1).Id == book.Id) {
                    author.setId(rs.getInt("author_id"));
                    author.setName(rs.getString("author_name"));
                    authors.add(author);
                } else {
                    if (books.size() > 0) {
                        books.get(books.size() - 1).authors = authors;
                        authors = new ArrayList<Author>();
                    }
                    books.add(book);
                    authors = new ArrayList<Author>();
                    author.setId(rs.getInt("author_id"));
                    author.setName(rs.getString("author_name"));
                    authors.add(author);
                }
            }
            if (books.size() > 0) {
                books.get(books.size() - 1).authors = authors;
            }
        } catch (SQLException ex) {
        }
        return books;
    }

    public ArrayList<Book> getBookHasNotAuthor(String wheres) {
        ArrayList<Book> books = new ArrayList<Book>();
        if (Empty(wheres)) {
            wheres = "(1=1)";
        }
        try {
            ResultSet rs = _statement.executeQuery("select * from book b "
                    + "where b.id not in(select b2.id from book b2 "
                    + "inner join book_author ba on ba.book_id = b2.id "
                    + "inner join author a on a.id = ba.author_id) and " + wheres);
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("Id"));
                book.setName(rs.getString("Name"));
                book.setCategory_id(rs.getInt("Category_id"));
                book.setContent_summary(rs.getString("Content_summary"));
                book.setPrice(rs.getDouble("Price"));
                book.setPublish_year(rs.getInt("Publish_year"));
                book.setQuantity(rs.getInt("Quantity"));
                book.setWeight(rs.getString("Weight"));
                book.setSize(rs.getString("Size"));
                books.add(book);
            }
        } catch (SQLException ex) {
        }
        return books;
    }

    public int getQuantityBook(int book_id) {
        try {
            ResultSet rs = _statement.executeQuery("select * from book b where id=" + book_id);
            while (rs.next()) {
                return rs.getInt("Quantity");
            }
        } catch (SQLException ex) {
        }
        return -1;
    }

}
