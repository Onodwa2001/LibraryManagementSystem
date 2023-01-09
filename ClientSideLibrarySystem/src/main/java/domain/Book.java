/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author Numata
 */
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String author;
    private String isbn;
    private String category;
    private String shelfNumber;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;
    private boolean isAvailableForLoan;
    
    public Book() {
        
    }

    public Book(String name, String author, String isbn, String category, String shelfNumber) {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.shelfNumber = shelfNumber;
        this.isAvailableForLoan  = true;
    }

    public Book(String name, String author, String isbn, String category, String shelfNumber, String image) {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.shelfNumber = shelfNumber;
        this.image = image;
        this.isAvailableForLoan  = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(String shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public boolean isAvailableForLoan() {
        return isAvailableForLoan;
    }

    public void setIsAvailableForLoan(boolean isAvailableForLoan) {
        this.isAvailableForLoan = isAvailableForLoan;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", category='" + category + '\'' +
                ", shelfNumber='" + shelfNumber + '\'' +
                ", image='" + image + '\'' +
                ", isAvailableForLoan=" + isAvailableForLoan +
                '}';
    }
}
