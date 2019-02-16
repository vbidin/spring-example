package eu.bidin.springexample.models;

import java.util.ArrayList;
import java.util.List;

public class StudentModel {

    private static final int MINIMUM_NAME_LENGTH = 1;
    private static final int MAXIMUM_NAME_LENGTH = 50;
    private static final int MINIMUM_GRADE = 1;
    private static final int MAXIMUM_GRADE = 5;

    private String name;
    private Integer grade;

    public StudentModel(String name, Integer grade) {
        this.name = name;
        this.grade = grade;
    }

    public List<String> Validate() {
        List<String> errors = new ArrayList<String>();

        if (name == null)
            errors.add("Name is required.");
        else if (getNameLength() < MINIMUM_NAME_LENGTH || getNameLength() > MAXIMUM_NAME_LENGTH)
            errors.add("Name must be between " + MINIMUM_NAME_LENGTH + " and " + MAXIMUM_NAME_LENGTH + " characters long.");

        if (grade == null)
            errors.add("Grade is required.");
        else if (grade < MINIMUM_GRADE || grade > MAXIMUM_GRADE)
            errors.add("Grade must be between " + MINIMUM_GRADE + " and " + MAXIMUM_GRADE + ".");

        return errors;
    }

    public String getName() {
        return name;
    }

    public Integer getGrade() {
        return grade;
    }

    private int getNameLength() {
        if (name == null)
            return 0;
        return name.trim().length();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}