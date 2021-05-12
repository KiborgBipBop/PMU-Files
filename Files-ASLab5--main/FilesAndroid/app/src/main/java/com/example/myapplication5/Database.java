package com.example.myapplication5;

import java.util.Objects;

public class Database
{
    private String name;
    private String surname;
    private String email;
    private String password;


    public Database(String name, String surname, String email, String password)
    {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com.example.myapplication5.Database data = (com.example.myapplication5.Database) o;
        return name.equals(data.name) &&
                surname.equals(data.surname) &&
                email.equals(data.email) &&
                password.equals(data.password);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, surname, email, password);
    }
}
