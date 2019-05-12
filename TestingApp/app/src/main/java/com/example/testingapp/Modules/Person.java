package com.example.testingapp.Modules;

public class Person {
    public String name, surname, email, employerEmail;

    public Person(String name, String surname, String email, String employerEmail)
    {
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.employerEmail=employerEmail;
    }

    public String getEmail(){return email;}
    public void setEmail(String question){this.email=question;}

    public String getSurname(){return surname;}
    public void setSurname(String question){this.surname=question;}

    public String getName(){return name;}
    public void setName(String question){this.name=question;}

    public String getEmployerEmail(){return employerEmail;}
    public void setEmployerEmail(String question){this.employerEmail=question;}
}
