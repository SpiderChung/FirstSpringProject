package ru.schung.project.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.schung.project.dao.BookDAO;
import ru.schung.project.dao.PersonDAO;
import ru.schung.project.models.Book;
import ru.schung.project.models.Person;

import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/lol")
    public String lol(Model model, @ModelAttribute("book") Book book) {
        model.addAttribute("books", bookDAO.index());
        System.out.println(book.getName());
        return "lol";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.index());
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add")
    public String takeBook(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookDAO.updatePerson(id, book);
        return "redirect:/people";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        bookDAO.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("{id}/assign")
    public String assing(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        System.out.println(person.getName() + ",   " + person.getPersonId() + ",  " + person.getBirthYear());
        bookDAO.assing(id, person);
        return "redirect:/books/" + id;
    }
}
