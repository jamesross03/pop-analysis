package org.example;

/**
 * Basic generic class for Records, only stores forename and surname.
 */
public class Person {
    private final String forename;
    private final String surname;

    public Person(String forename, String surname) {
        this.forename = forename;
        this.surname = surname;
    }

    /**
     * @param r Record
     * @return forename
     */
    public static String getForenameStatic(Person r) {
        return r.getForename();
    }

    public String getForename() {
        return this.forename;
    }

    /**
     * @param r Record
     * @return surname
     */
    public static String getSurnameStatic(Person r) {
        return r.getSurname();
    }

    public String getSurname() {
        return this.surname;
    }
}