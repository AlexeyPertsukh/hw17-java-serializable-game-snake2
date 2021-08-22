/*
Создать полноценный класс юзер(имя, фамилия,возраст, почта, номер телефона)
 */
package prog_notebook;

import java.io.Serializable;

public class User implements Serializable {
    private String surname;
    private String name;
    private int yearOfBirth;
    private String email;
    private String tel;

    public User(String surname, String name, int yearOfBirth, String email, String tel) {
        this.surname = surname;
        this.name = name;
        setYearOfBirth(yearOfBirth);
        this.email = email;
        this.tel = tel;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYearOfBirth(int yearOfBirth) {
        if(yearOfBirth < 0) {
            yearOfBirth = 0;
        }
        this.yearOfBirth = yearOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        String str = surname + " " + name;
        str = String.format("%-24s  %-4d   %-15s %s", str, yearOfBirth, tel, email);
        return str;

    }

    public String smallInfo() {
        return surname + " " + name + ", " + yearOfBirth + ", " + tel + ", " + email;
    }

}
