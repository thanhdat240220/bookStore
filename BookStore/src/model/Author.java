/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author T440s
 */
public class Author {
    public int id;
    public String name;
    public String date_of_birth;

    public Author() {

    }

    public Author(int id, String name, String date_of_birth) {
        this.id = id;
        this.name = name;
        this.date_of_birth = date_of_birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String year_birthday) {
        this.date_of_birth = year_birthday;
    }
}
