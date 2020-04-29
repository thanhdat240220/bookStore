/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.DAOAuthor;
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
        makeMenuRow("   5.Show All Author");
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
                    System.out.println("    Add a New Author".toUpperCase());
                    Author author = new Author();
                    String name = enterString("Name");
                    author.setName(name);
                    String birthDate = enterDate("Birth Date");
                    author.setBirth_date(birthDate);
                    if (daoAuthor.checkDuplicateAuthor().name.equals(name) && daoAuthor.checkDuplicateAuthor().birth_date.equals(birthDate)) {
                        System.out.println("Author already existed!");
                    } else {
                        daoAuthor.addAuthor(author);
                    }
                    break;
                case 2:
                    //edit();
                    System.out.println("    Edit an Author".toUpperCase());
                    Author authorToEdit = new Author();
                    int idToEdit = enterNumber("ID to Edit");

                    while (true) {
                        if (daoAuthor.checkExistedAuthor(idToEdit).equals("")) {
                            System.out.println("Author not Found! Please try again!");
                            idToEdit = enterNumber("ID to Edit");
                        } else {
                            authorToEdit.setId(idToEdit);
                            String nameToEdit = enterString("New Name");
                            authorToEdit.setName(nameToEdit);

                            String birthDateToEdit = enterDate("New Birth Date");
                            authorToEdit.setBirth_date(birthDateToEdit);

                            daoAuthor.editAuthor(authorToEdit);
                            break;
                        }
                    }
                    break;
                case 3:
                    //delete();
                    System.out.println("    Delete an Author".toUpperCase());
                    int idToDelete = enterNumber("ID to Delete");

                    while (true) {
                        if (daoAuthor.checkExistedAuthor(idToDelete).equals("")) {
                            System.out.println("Author not Found! Please try again!");
                            idToDelete = enterNumber("ID to Delete");
                        } else {
                            daoAuthor.removeAuthor(idToDelete);
                            break;
                        }
                    }
                    break;
                case 4:
                    //showDetailById();
                    System.out.println("    Show an Author by ID".toUpperCase());
                    int idToShow = enterNumber("ID to Show");
                    daoAuthor.showAnAuthor(idToShow);
                    break;
                case 5:
//                    Author authorToShowAll = new Author();
                    System.out.println("    Show all Authors".toUpperCase());
                    daoAuthor.showAllAuthor();
                    daoAuthor.dumb();
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
}
