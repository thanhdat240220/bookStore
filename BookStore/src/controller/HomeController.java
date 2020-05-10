/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Author;
import model.Book;
import model.Customer;
import viewmodel.Cart;
import viewmodel.CartDetail;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * @author T440s
 */
public class HomeController extends BaseController {
    private ProductController _productController;
    private CategoryController _categoryController;
    private OrderController _orderController;
    private Cart _cartList;
    private Customer _user;

    public HomeController(Connection connect) {
        super(connect);
        _productController = new ProductController(connect);
        _categoryController = new CategoryController(connect);
        _orderController = new OrderController(connect);
        _cartList = new Cart();
    }


    public void menuHome() {
        int choice;
        do {
            makeMenuHeader("System management window");
            makeMenuRow("1. Login User.");
            makeMenuRow("2. Filter Books by Category");
            makeMenuRow("3. Quick Search Books.");
            makeMenuRow("4. Show Cart");
            makeMenuRow("5. Add Book to Cart.");
            makeMenuRow("6. Back to Main Menu");
            makeMenuFooter();
            choice = enterNumber("an option");
            switch (choice) {
                case 2:
                    menuKind();
                    break;
                case 3:
                    quickSearch();
                    break;
                case 4:
                    manageCart();
                    break;
                case 5:
                    addBookToCart();
                    break;
                case 6:
                    back();
                    break;
                default:
                    System.out.println(" Option is invalid!");
                    break;
            }
        } while (choice != 6);
    }

    public void menuKind() {
        _categoryController.showCategory("");
        makeRow("0. Back to previous menu.        ");
        System.out.println("");
        int category_id;
        do {
            category_id = enterNumber("an option category");

            switch (category_id) {
                case 0:
                    back();
                    break;
                default:
                    String wheres = "category_id =" + category_id;
                    _productController.showBooks(wheres);
                    break;
            }
        } while (category_id != 0);
    }

    public void quickSearch() {
        boolean retry = false;
        do {
            String sql = "(1=1)";
            String keyWord = enterString("Enter Key Search");
            sql += " and (LOWER(name) like '%" + keyWord.trim().toLowerCase() + "%' or LOWER(content_summary) like '%" + keyWord.trim().toLowerCase() + "%')";
            double startMoney = -1, endMoney = -1;
            System.out.println("Enter Start Money ? (y/n)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                startMoney = enterStartMoney("Start Money");
                sql += " and price >=" + startMoney;
            }
            System.out.println("Enter End Money ? (y/n)");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                endMoney = enterEndMoney("End Money", startMoney);
                sql += " and price <=" + endMoney;
            }
            //
            ArrayList<Book> books = _productController.getBooks(sql);
            if (books.size() > 0) {
                makeMenuHeader("Search Book");
                _productController.funcShowBook(books);
                System.out.println("Search again?");
            } else {
                System.out.println("Not Found !");
                System.out.println("Search Again ? (y/n)");
            }
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                retry = true;
            } else {
                retry = false;
            }
        } while (retry);
    }

    public void addBookToCart() {
        int book_id;
        boolean retry = false;
        do {
            book_id = enterNumber("Book Id");
            ArrayList<Book> books = _productController.getBooks(" b.id=" + book_id);
            if (books.size() > 0) {
                Book book = books.get(0);
                int quantity = enterInterger("Quantity");
                boolean checkbook = false;
                //
                for (CartDetail cart : _cartList.getDetails()) {
                    if (cart.getBook().getId() == book.getId()) {
                        cart.setQuantity(cart.getQuantity() + quantity);
                        checkbook = true;
                    }
                }
                if (!checkbook) {
                    CartDetail item = new CartDetail();
                    item.setBook(book);
                    item.setQuantity(quantity);
                    _cartList.getDetails().add(item);
                }
                updatePriceCart();
                //
                System.out.println("Book Added To Cart,Please Check Cart ");
                System.out.println("Add More ? (y/n): ");
            } else {
                System.out.println("Not Fount Book Has ID=" + book_id);
                System.out.println("Retry? (y/n): ");
            }
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                retry = true;
            } else {
                retry = false;
            }
        } while (retry);
        menuHome();
    }

    public void showCart() {
        double priceTotal = 0;
        makeMenuHeader("Cart List");
        if (_cartList.getDetails().size() > 0) {
            for (CartDetail cart : _cartList.getDetails()) {
                Book book = cart.getBook();
                makeRow("Book Id:" + book.getId());
                makeRow("Book Name:" + book.getName());
                makeRow("Price:" + book.getPrice());
                makeRow("Quantity Buy:" + cart.getQuantity());
                priceTotal += book.getPrice() * cart.getQuantity();
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
            makeRow("Cart Empty");
        }
        makeRow("Price Total :" + priceTotal + " VND");
    }

    public void manageCart() {
        int choice;
        showCart();
        do {
            makeMenuHeader("Cart List Option");
            makeMenuRow("1. Confirm Order.");
            makeMenuRow("2. Edit Quantity.");
            makeMenuRow("3. Remove From Cart.");
            makeMenuRow("4. Back to main menu");
            makeMenuFooter();
            choice = enterNumber("an option");
            switch (choice) {
                case 1:
                    OrderStart();
                    break;
                case 2:
                    EditQuantity();
                    break;
                case 3:
                    RemoveFromCart();
                    break;
                case 4:
                    back();
                    break;
                default:
                    System.out.println(" Option is invalid!");
                    break;
            }
        } while (choice != 4);
    }

    public void EditQuantity() {
        int book_id;
        boolean checkbook = false, retry = false;
        do {

            book_id = enterNumber("Book Id");
            for (CartDetail detail : _cartList.getDetails()) {
                if (detail.getBook().getId() == book_id) {
                    int quantity = enterInterger("Quantity");
                    detail.setQuantity(quantity);
                    updatePriceCart();
                    checkbook = true;
                    break;
                }
            }
            if (checkbook) {
                System.out.println("Successful Change!");
                System.out.println("Change ? (y/n): ");
            } else {
                System.out.println("Not Fount Book Has ID=" + book_id + " In Cart!");
                System.out.println("Retry? (y/n): ");
            }
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                retry = true;
            } else {
                retry = false;
            }
        } while (retry);
        manageCart();
    }

    public void RemoveFromCart() {
        int book_id;
        boolean checkbook = false, retry = false;
        do {

            book_id = enterNumber("Book Id");
            for (CartDetail detail : _cartList.getDetails()) {
                if (detail.getBook().getId() == book_id) {
                    _cartList.getDetails().remove(detail);
                    updatePriceCart();
                    checkbook = true;
                    break;
                }
            }
            if (checkbook) {
                System.out.println("Removed!");
                System.out.println("Removed Next ? (y/n): ");
            } else {
                System.out.println("Not Fount Book Has ID=" + book_id + " In Cart!");
                System.out.println("Retry? (y/n): ");
            }
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                retry = true;
            } else {
                retry = false;
            }
        } while (retry);
        manageCart();
    }

    public void OrderStart() {
        if (_cartList.getDetails().size() > 0) {
            if (_user != null) {

            } else {
                String name = enterString("Enter Your Name");
                String address = enterString("Enter Address");
                String phone = enterString("Enter Your Phone Number");
                int order_id = _orderController.saveOrderAnonymous(_cartList, name, address, phone);
                if (order_id > 0) {
                    makeRow("Order success");
                    _cartList = new Cart();
                    updatePriceCart();
                    _orderController.showOrder("id=" + order_id);
                }
            }
        } else {
            System.out.println("Cart Empty!!");
        }
    }

    public void updatePriceCart() {
        double priceTotal = 0;
        for (CartDetail detail : _cartList.getDetails()) {
            priceTotal = detail.getBook().getPrice() * detail.getQuantity();
        }
        _cartList.setTotalPrice(priceTotal);
    }

    public double enterEndMoney(String option, double startMoney) {
        String choiceStr = "";
        do {
            System.out.print("- Enter " + option + ":");
            choiceStr = scanner.nextLine();
            if (isDouble(choiceStr) || Integer.parseInt(choiceStr) == 0) {
                if (Double.parseDouble(choiceStr) > startMoney) {
                    break;
                } else {
                    System.out.println("- " + option + " must greater startMoney!");
                }
            } else {
                System.out.println("- " + option + " must be a positive number!");
            }
        } while (true);
        return Double.parseDouble(choiceStr);
    }

    public double enterStartMoney(String option) {
        String choiceStr = "";
        do {
            System.out.print("- Enter " + option + ":");
            choiceStr = scanner.nextLine();
            try {
                if (isDouble(choiceStr) || Integer.parseInt(choiceStr) == 0) {
                    break;
                } else {
                    System.out.println("- " + option + " must be a positive number!");
                }
            } catch (Exception e) {
                System.out.println("- " + option + " must be a positive number!");
            }
        } while (true);
        return (int) Double.parseDouble(choiceStr);
    }

}
