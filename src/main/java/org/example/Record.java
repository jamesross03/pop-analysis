package org.example;

/**
 * Basic generic class for Records, only stores forename and surname.
 */
public class Record {
    private final String forename;
    private final String surname;

    public Record(String forename, String surname) {
        this.forename = forename;
        this.surname = surname;
    }

    /**
     * @param r Record
     * @return forename
     */
    public static String getForenameStatic(Record r) {
        return r.getForename();
    }

    public String getForename() {
        return this.forename;
    }

    /**
     * @param r Record
     * @return surname
     */
    public static String getSurnameStatic(Record r) {
        return r.getSurname();
    }

    public String getSurname() {
        return this.surname;
    }
}