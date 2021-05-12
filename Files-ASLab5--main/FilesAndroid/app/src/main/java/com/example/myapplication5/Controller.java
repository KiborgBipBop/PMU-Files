package com.example.myapplication5;

import java.util.ArrayList;

public class Controller
{
    private static com.example.myapplication5.Controller instance;
    private ArrayList<Database> data;
    private int activeIndex = -1;

    public Controller()
    {
        data = new ArrayList<>();
        data.add(new Database("Maria", "Pavlova", "test@gmail.com", "123"));
    }

    public static com.example.myapplication5.Controller getInstance()
    {
        if (instance == null)
            instance = new com.example.myapplication5.Controller();
        return instance;
    }

    public void addToList(Database data)
    {
        this.data.add(data);
    }

    public int emailExist(String email)
    {
        for (int i = 0; i < data.size(); i++)
        {
            if (data.get(i).getEmail().equals(email))
                return i;
        }
        return -1;
    }

    public int signIn(String email, String password)
    {
        int i = emailExist(email);
        if (i != -1)
        {
            if (data.get(i).getPassword().equals(password))
            {
                activeIndex = i;
                return i;
            }
        }
        return -1;
    }

    public String getFullName()
    {
        if (activeIndex == -1)
            return "";
        else
            return data.get(activeIndex).getName() + " " + data.get(activeIndex).getSurname();
    }
}

