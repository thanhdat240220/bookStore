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
public class Book {
    public int Id;
    public int category_id;
    public int author_id;
    public String name;
    public String content_summary;
    public int status_id;
    public int publish_year;
    public double price;
    public int quantity;
    public String size;

    public Book() {

    }


    public void setName(String name) {
        this.name = name;
    }

    public void setContent_summary(String content_summary) {
        this.content_summary = content_summary;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public void setPublish_year(int publish_year) {
        this.publish_year = publish_year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getContent_summary() {
        return content_summary;
    }

    public int getStatus_id() {
        return status_id;
    }

    public int getPublish_year() {
        return publish_year;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSize() {
        return size;
    }

    public String getWeight() {
        return weight;
    }
    public String weight;
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getBook_name() {
        return name;
    }

    public void setBook_name(String book_name) {
        this.name = book_name;
    }

    public String getDescription() {
        return content_summary;
    }

    public void setDescription(String description) {
        this.content_summary = description;
    }
}
