/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Employee;

/**
 *
 * @author tvcpr
 */
public class BaseController {

    protected Connection connection;
    protected Scanner scanner;
    private int _rowLength;

    public BaseController(Connection connect) {
        connection = connect;
        scanner = new Scanner(System.in);
        _rowLength = 58;
    }

    public void showMainMenu() {
        makeMenuHeader("BOOK STORE");
        makeMenuRow("1. Home.");
        makeMenuRow("2. Admin System.");
        makeMenuRow("3. Exit.");
        makeMenuFooter();
    }

    public boolean isDouble(String str) {
        try {
            double d = Double.parseDouble(str);
            if (d <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public int enterNumber(String option) {
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

    public int enterInterger(String option) {
        String choiceStr = "";
        do {
            System.out.print("- Enter " + option + ":");
            choiceStr = scanner.nextLine();
            if (isDouble(choiceStr)) {
                break;
            } else {
                System.out.println("- " + option + " must be a positive number!");
            }
        } while (true);
        return (int) Double.parseDouble(choiceStr);
    }

    public Double enterRealNumber(String option) {
        String choiceStr = "";
        do {
            System.out.print("- Enter " + option + ":");
            choiceStr = scanner.nextLine();
            if (isDouble(choiceStr)) {
                break;
            } else {
                System.out.println("- " + option + " must be a positive number!");
            }
        } while (true);
        return Double.parseDouble(choiceStr);
    }

    public String enterEmail() {
        String email = "";
        do {
            email = enterString("Email");
            Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            //
            if (matcher.find()) {
                break;
            } else {
                makeRow("email is invalid! Please Re-enter!");
            }
        } while (true);
        return email;
    }

    public String enterPhoneNumber() {
        String phone = "";
        do {
            phone = enterString("Phone Number");
            Pattern pattern = Pattern.compile("^\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(phone);
            //
            if (matcher.find()) {
                break;
            } else {
                makeRow("Phone Number is invalid! Please Re-enter!");
            }
        } while (true);
        return phone;
    }

    public String enterString(String option) {
        System.out.print("- Enter " + option + ":");
        String choiceStr = "";
        do {
            choiceStr = scanner.nextLine();
            if (choiceStr.trim().isEmpty()) {
                System.out.println("- " + option + " must has infomation!");
                System.out.print("- Re-enter " + option + ": ");
            } else {
                break;
            }
        } while (true);
        return choiceStr;
    }

    public boolean enterBoolean(String option) {
        System.out.print("- Enter " + option + " (yes/no):");
        String choiceStr = scanner.nextLine();
        return choiceStr.contains("y") || choiceStr.contains("Y");
    }

    public String enterDate(String option) {
        boolean isValid = false;
        String date = "";
        do {
            try {
                System.out.print("- Enter " + option + " (dd/MM/yyyy HH:mm):");
                date = scanner.nextLine();
                SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                formatter1.parse(date);
                isValid = true;
            } catch (ParseException ex) {
                makeRow("Date is invalid! Please re-enter!");
            }
        } while (!isValid);

        return date;
    }

    public boolean Empty(String string) {
        return string == null || string.trim().equals("");
    }

    public int makeMenuHeader(String name) {
        System.out.println("");
        String header = " -------------------" + name.toUpperCase() + " ------------------";
        if (name.length() < 20) {
            header = " ---------------------------- " + name.toUpperCase() + " ----------------------------";
        }

        System.out.println(header);
        this._rowLength = header.length();
        makeRow("");
        return _rowLength;
    }

    public void makeMenuRow(String option) {
        int remainSpace = this._rowLength - option.length() - 4;
        String space = "";
        for (int i = 0; i <= remainSpace - 18; i++) {
            space += " ";
        }
        System.out.println("|                   " + option + space + " |");
    }

    public void makeMenuFooter() {
        String space = " ";
        for (int i = 0; i < this._rowLength; i++) {
            space += "-";
        }
        makeRow("");
        System.out.println(space);
        System.out.println("");
    }

    public void makeRow(String option) {
        int remainSpace = this._rowLength - option.length() - 4;
        String space = "";
        for (int i = 0; i <= remainSpace; i++) {
            space += " ";
        }
        System.out.println("| " + option + space + " |");
    }

    public void back() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
