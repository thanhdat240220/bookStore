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
import model.Book;
import model.Order;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Author;

/**
 *
 * @author T440s
 */
public class ProductController extends BaseController {

    private Statement _statement;

    public ProductController(Connection connect) {
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
        makeMenuRow("   1.Add Product");
        makeMenuRow("   2.Edit Product");
        makeMenuRow("   3.Delete Product");
        makeMenuRow("   4.Watch Product Detail");
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

    public void showBooks(String wheres) {
        ArrayList<Book> books = getBooks(wheres);
        makeMenuHeader("Book List");
        if (books.size() > 0) {
            for (Book book : books) {
                makeRow("Book Id:" + book.getId());
                makeRow("Book Name:" + book.getName());
                makeRow("Price:" + book.getPrice()+" VND");
                makeRow("Publish Year:" + book.getPublish_year());
                makeRow("Content Sumary:" + book.getContent_summary());
                makeRow("Quantity:" + book.getQuantity());
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
        } else {
            makeRow("Not Found");
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
                book.setAuthor_id(rs.getInt("Author_id"));
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
            if (books.size() > 1) {
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
                book.setAuthor_id(rs.getInt("Author_id"));
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
}
