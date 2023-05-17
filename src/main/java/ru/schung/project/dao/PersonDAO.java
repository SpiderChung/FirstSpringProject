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
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?",
                new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().orElse(null);
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, birth_year) VALUES(?, ?)",
                person.getName(), person.getBirthYear());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, birth_year=? WHERE person_id=?",
                person.getName(), person.getBirthYear(), id);
    }

    public Optional<Person> getPersonByFullName(String name) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?",
                new BeanPropertyRowMapper<>(Person.class), name).stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?",
                new BeanPropertyRowMapper<>(Book.class), id);
    }
}
