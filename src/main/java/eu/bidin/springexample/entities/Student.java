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

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!Student.class.isAssignableFrom(obj.getClass()))
            return false;

        final Student other = (Student) obj;
        if (this.uuid != other.uuid)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.uuid != null ? this.uuid.hashCode() : 0);
        return hash;
    }
}
