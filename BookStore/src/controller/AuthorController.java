/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOAuthor;
import model.Author;

import java.sql.Connection;

/**
 * @author T440s
 */
public class AuthorController extends BaseController {

    DAOAuthor daoAuthor;

    public AuthorController(Connection connect) {
        super(connect);
        daoAuthor = new DAOAuthor(connect);
        this.connection = connect;
    }

    public void showProductEditor() {
        //makeMenuHeader("Products information Editor");
        //showAll();
        makeMenuRow("Options:");
        makeMenuRow("   1.Add Author");
        makeMenuRow("   2.Edit Author");
        makeMenuRow("   3.Delete Author");
        makeMenuRow("   4.Show An Author By ID");
        makeMenuRow("   5.Show All Authors");
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
                    addAuthor();
                    break;
                case 2:
                    editAuthor();
                    break;
                case 3:
                    deleteAuthor();
                    break;
                case 4:
                    showAnAuthor();
                    break;
                case 5:
                    showAllAuthors();
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

    public void addAuthor() {
        makeMenuHeader("Add a New Author");
        Author author = new Author();
        String name = enterString("Name");
        author.setName(name);
        String date_of_birth = enterDate("Date of Birth");
        author.setDate_of_birth(date_of_birth);
        if (daoAuthor.checkDuplicateAuthor().name.equals(name) && daoAuthor.checkDuplicateAuthor().date_of_birth.equals(date_of_birth)) {
            System.out.println("Author already existed!");
        } else {
            daoAuthor.addAuthor(author);
            makeMenuFooter();
        }
    }

    public void editAuthor() {
        makeMenuHeader("Edit an Author");
        Author author = new Author();
        int id = enterNumber("ID to Edit");

        while (true) {
            if (daoAuthor.checkExistedAuthor(id).equals("")) {
                System.out.println("Author not Found! Please try again!");
                System.out.print("Retry? (y/n): ");
                String choice = scanner.nextLine();

                if (!choice.equalsIgnoreCase("y")) {
                    break;
                } else {
                    id = enterNumber("ID to Edit");
                }
            } else {
                author.setId(id);
                String name = enterString("New Name");
                author.setName(name);

                String date_of_birth = enterDate("New Date of Birth");
                author.setDate_of_birth(date_of_birth);

                daoAuthor.editAuthor(author);
                makeMenuFooter();
                break;
            }
        }
    }

    public void deleteAuthor() {
        makeMenuHeader("Delete an Author");
        int id = enterNumber("ID to Delete");

        while (true) {
            if (daoAuthor.checkExistedAuthor(id).equals("")) {
                System.out.println("Author not Found! Please try again!");
                System.out.print("Retry? (y/n): ");
                String choice = scanner.nextLine();

                if (!choice.equalsIgnoreCase("y")) {
                    break;
                } else {
                    id = enterNumber("ID to Edit");
                }
            } else {
                daoAuthor.removeAuthor(id);
                makeMenuFooter();
                break;
            }
        }
    }

    public void showAnAuthor() {
        makeMenuHeader("Show an Author by ID");
        int id = enterNumber("ID to Show");

        while (true) {
            if (daoAuthor.checkExistedAuthor(id).equals("")) {
                System.out.println("Author not Found! Please try again!");
                System.out.print("Retry? (y/n): ");
                String choice = scanner.nextLine();

                if (!choice.equalsIgnoreCase("y")) {
                    break;
                } else {
                    id = enterNumber("ID to Edit");
                }
            } else {
                daoAuthor.showAnAuthor(id);
                makeMenuFooter();
                break;
            }
        }
    }

    public void showAllAuthors() {
        daoAuthor.showAllAuthor();
    }
    
}
