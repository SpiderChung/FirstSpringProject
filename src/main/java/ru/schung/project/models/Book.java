package ru.schung.project.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    public int getId() {
        return book_id;
    }

    public void setId(int id) {
        this.book_id = id;
    }

    public int getBookId() {
        return book_id;
    }

    public void setBookId(int book_id) {
        this.book_id = book_id;
    }

    private int book_id;
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов")
    private String name;
    @NotEmpty(message = "Имя автора не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно быть от 2 до 100 символов")
    private String author;
    @Min(value = 1500, message = "Год издания не может быть ранее 1500")
    private int release_year;

    public Book() {
    }

    public Book(int book_id, String name, String author, int release_year) {
        this.book_id = book_id;
        this.name = name;
        this.author = author;
        this.release_year = release_year;
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

    public int getReleaseYear() {
        return release_year;
    }

    public void setReleaseYear(int release_year) {
        this.release_year = release_year;
    }
}
