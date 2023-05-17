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
import ru.schung.project.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }



    @GetMapping()
    public String listOfPersons(Model model, @ModelAttribute("book") Book book) {
        model.addAttribute("people", personDAO.index());
        model.addAttribute("books", bookDAO.indexUntaken());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("books", personDAO.getBooksByPersonId(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String save(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String save(@ModelAttribute("person") Person person,
                       BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

}
