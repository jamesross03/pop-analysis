package org.example;

public class Person {
    private final String forename;
    private final String surname;

    public Person(String forename, String surname) {
        this.forename = forename;
        this.surname = surname;
    }

    public String getForename() {
        return this.forename;
    }

    public String getSurname() {
        return this.surname;
    }
}