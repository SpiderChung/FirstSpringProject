package ru.schung.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.schung.project.models.Book;
import ru.schung.project.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> indexUntaken() {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id IS NULL",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",
                new BeanPropertyRowMapper<>(Book.class), id).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(person_id, name, author, release_year) " +
                "VALUES(?, ?, ?, ?)", null, book.getName(), book.getAuthor(), book.getReleaseYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET person_id=?, name=?, author=?, release_year=? WHERE book_id=?",
                null, book.getName(), book.getAuthor(), book.getReleaseYear(), id);
    }

    public void updatePerson(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",
                id, book.getBookId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", null, id);
    }

    public  void assing(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", selectedPerson.getPersonId(), id);
    }
    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.person_id WHERE Book.book_id = ?",
                new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
    }


}

