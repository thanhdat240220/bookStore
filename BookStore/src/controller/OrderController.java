/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Employee;
import model.Order;
import model.OrderDetail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author T440s
 */
public class OrderController extends BaseController {

    protected Statement statement;
    protected Employee _employee;

    public OrderController(Connection connect) {
        super(connect);
        try {
            statement = connect.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void menu(Employee employee) {
        _employee = employee;
        int choice;
        do {
            makeMenuHeader("Bills infomation Management");
            makeMenuRow("1.Unconfimred orders");
            makeMenuRow("2.Delivering orders");
            makeMenuRow("3.Delivered orders");
            makeMenuRow("4.Back to previous menu");
            makeMenuFooter();
            choice = enterNumber("an option");
            switch (choice) {
                case 1:
                    MenuUnconfimred();
                    break;
                case 2:
                    MenuDelivering();
                    break;
                case 3:
                    MenuDelivered();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("¤ Option is invalid!");
                    break;
            }
        } while (choice != 4);
    }

    public void MenuUnconfimred() {
        int choice;
        do {
            makeMenuHeader("Undelivered Orders Management");
            makeMenuRow("1. Order Detail");
            makeMenuRow("2. Confirm And Delivering");
            makeMenuRow("3. Delete Order");
            makeMenuRow("4. Show All Unconfimred Orders");
            makeMenuRow("5. Back to previous menu");
            makeMenuFooter();
            choice = enterNumber("an option");
            switch (choice) {
                case 1:
                    showOrderDetail(1);
                    break;
                case 2:
                    ContinueOrder(1);
                    break;
                case 3:
                    DeleteOrder(1);
                    break;
                case 4:
                    ShowAllOrders(1);
                    break;
                case 5:
                    back();
                    break;
                default:
                    System.out.println("Option is invalid!");
                    break;
            }
        } while (choice != 5);
    }

    public void MenuDelivering() {
        int choice;
        do {
            makeMenuHeader("Undelivered Orders Management");
            makeMenuRow("1. Order Detail");
            makeMenuRow("2. Delivered");
            makeMenuRow("3. Delete Order");
            makeMenuRow("4. Show All Unconfimred Orders");
            makeMenuRow("5. Back to previous menu");
            makeMenuFooter();
            choice = enterNumber("an option");
            switch (choice) {
                case 1:
                    showOrderDetail(2);
                    break;
                case 2:
                    ContinueOrder(2);
                    break;
                case 3:
                    DeleteOrder(2);
                    break;
                case 4:
                    ShowAllOrders(2);
                    break;
                case 5:
                    back();
                    break;
                default:
                    System.out.println("Option is invalid!");
                    break;
            }
        } while (choice != 5);
    }

    public void MenuDelivered() {
        int choice;
        do {
            makeMenuHeader("Undelivered Orders Management");
            makeMenuRow("1. Order Detail");
            makeMenuRow("2. Delete Order");
            makeMenuRow("3. Show All Unconfimred Orders");
            makeMenuRow("4. Back to previous menu");
            makeMenuFooter();
            choice = enterNumber("an option");
            switch (choice) {
                case 1:
                    showOrderDetail(3);
                    break;
                case 2:
                    DeleteOrder(3);
                    break;
                case 3:
                    ShowAllOrders(3);
                    break;
                case 4:
                    back();
                    break;
                default:
                    System.out.println("Option is invalid!");
                    break;
            }
        } while (choice != 4);
    }

    public void showOrderDetail(int StatusId) {
        int OrderID = enterNumber("Order ID");
        ArrayList<Order> orders = getOrders("Id=" + OrderID + " and status_id=" + StatusId);
        if (orders.size() > 0) {
            Order order = orders.get(0);
            ArrayList<OrderDetail> orderDetails = getOrderDetails(OrderID);

            makeRow("Order Id: " + order.getId());
            makeRow("Status Name: " + statusName(StatusId));
            makeRow("Received Address: " + (!Empty(order.getAddress_recieved()) ? order.getAddress_recieved() : ""));
            makeRow("Created Date: " + (order.getCreate_date() != null ? order.getCreate_date() : ""));
            makeRow("Price: " + order.getOrder_price() + " VND");

            for (OrderDetail orderDetail : orderDetails) {
                makeRow("- Book Name: " + orderDetail.book_name);
                makeRow("- Quantity: " + orderDetail.quantity);
            }
        } else {
            makeRow("Not found order has ID=" + OrderID);
        }
    }

    public void ContinueOrder(int StatusId) {
        int OrderID = enterNumber("Order ID");
        ArrayList<Order> orders = getOrders("Id=" + OrderID + " and status_id=" + StatusId);
        if (orders.size() > 0) {
            try {
                String sql = "update orders set status_id = " + ++StatusId + " and employee_id=" + _employee.getId() + " where id=" + OrderID;
                statement.executeUpdate(sql);
                makeRow("Update Success");
            } catch (SQLException ex) {
                makeRow("Error System");
            }

        } else {
            makeRow("Not found order has ID=" + OrderID);
        }
    }

    public void ShowAllOrders(int StatusId) {
        ArrayList<Order> Orders = getOrders(" status_id=" + StatusId);
        if (Orders.size() > 0) {
            for (Order item : Orders) {
                makeMenuRow("----------------------");
                makeRow("Order Id: " + item.getId());
                makeRow("Status Name: " + statusName(StatusId));
                makeRow("Received Address: " + (!Empty(item.getAddress_recieved()) ? item.getAddress_recieved() : ""));
                makeRow("Created Date: " + (item.getCreate_date() != null ? item.getCreate_date() : ""));
                makeRow("Price: " + item.getOrder_price() + " VND");
            }
        } else {
            makeRow("Empty Order");
        }
    }

    public void DeleteOrder(int StatusId) {
        int OrderID = enterNumber("Order ID");
        ArrayList<Order> orders = getOrders("Id=" + OrderID + " and status_id=" + StatusId);
        if (orders.size() > 0) {
            try {
                statement.executeUpdate("delete from Orders where Id=" + OrderID);
                makeRow("Delete order successful!");
            } catch (SQLException ex) {
                makeRow("Delete order fail!");
            }
        } else {
            makeRow("Not found order has ID=" + OrderID);
        }
    }

    public ArrayList<Order> getOrders(String where) {
        ArrayList<Order> orders = new ArrayList<Order>();
        try {
            ResultSet rs = statement.executeQuery("select * "
                    + "from orders Where (1=1) And " + where);
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("Id"));
                order.setEmployee_id(rs.getInt("Employee_id"));
                order.setCustomer_id(rs.getInt("Customer_id"));
                order.setAddress_recieved(rs.getString("Address_recieved"));
                order.setOrder_price(rs.getDouble("Order_price"));
                order.setCreate_date(rs.getDate("create_date"));
                order.setStatus_id(rs.getInt("Status_id"));
                orders.add(order);
            }
        } catch (SQLException ex) {
        }
        return orders;
    }

    public ArrayList<OrderDetail> getOrderDetails(int OrderId) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
        try {
            ResultSet rs = statement.executeQuery("select * "
                    + "from orderDetail od left join book b on b.id =od.book_id  Where order_id=" + OrderId);
            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(rs.getInt("id"));
                orderDetail.setOrder_id(rs.getInt("order_id"));
                orderDetail.setPrice(rs.getDouble("price"));
                orderDetail.setBook_id(rs.getInt("book_id"));
                orderDetail.setBook_name(rs.getString("name"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetails.add(orderDetail);
            }
        } catch (SQLException ex) {
        }
        return orderDetails;
    }

    public String statusName(int StatusId) {
        switch (StatusId) {
            case 1:
                return "Unconfimred";
            case 2:
                return "Delivering";
            case 3:
                return "Delivered";
            default:
                return "";
        }
    }
}
