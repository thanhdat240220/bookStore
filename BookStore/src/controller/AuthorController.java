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
 *
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
        makeMenuRow("   4.Watch Author");
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
                    System.out.println("Add A New Author".toUpperCase());
                    Author author = new Author();
                    String name = enterString("Name");
                    author.setName(name);
                    int yearOfBirth = enterNumber("Year Of Birth");
                    author.setYear_birthday(yearOfBirth);
                    daoAuthor.addAuthor(author);
                    break;
                case 2:
                    //edit();
                    System.out.println("Edit An Author".toUpperCase());
                    Author authorToEdit = new Author();
                    int idToEdit = enterNumber("ID To Edit");
                    authorToEdit.setId(idToEdit);
                    String nameToEdit = enterString("New Name");
                    authorToEdit.setName(nameToEdit);
                    int yearOfBirthToEdit = enterNumber("New Year Of Birth");
                    authorToEdit.setYear_birthday(yearOfBirthToEdit);
                    daoAuthor.editAuthor(authorToEdit);
                    break;
                case 3:
                    //delete();
                    System.out.println("Delete An Author".toUpperCase());
                    int idToDelete = enterNumber("ID To Delete");
                    daoAuthor.removeAuthor(idToDelete);
                    break;
                case 4:
                    //showDetailById();
                    break;
                case 5:
//                    Author authorToShowAll = new Author();
                    daoAuthor.showAllAuthor();
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
