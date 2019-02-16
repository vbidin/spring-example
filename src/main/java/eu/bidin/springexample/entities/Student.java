package eu.bidin.springexample.entities;

import java.util.UUID;

public class Student {

    private final UUID uuid;
    private String name;
    private Integer grade;

    public Student(UUID uuid, String name, Integer grade) {
        this.uuid = uuid;
        this.name = name;
        this.grade = grade;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
