package ru.schung.project.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {


    private int person_id;
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    private String name;

   @Min(value = 1940, message = "Слишком старым не выдаем")
    private int birth_year;

    public Person() {
    }

    public Person(int person_id, String name, int birth_year) {
        this.person_id = person_id;
        this.name = name;
        this.birth_year = birth_year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birth_year;
    }

    public void setBirthYear(int birth_year) {
        this.birth_year = birth_year;
    }

    public int getPersonId() {
        return person_id;
    }

    public void setPersonId(int person_id) {
        this.person_id = person_id;
    }
}
