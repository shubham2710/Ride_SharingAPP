package main.Data;

import java.util.Objects;

public class User {
    private String id;
    private String name;
    private Gender gender;
    private int age;

    public User(String id,String name,Gender gender, int age ) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }

}
