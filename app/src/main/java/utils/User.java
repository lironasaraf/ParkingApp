package utils;

import java.io.Serializable;
import java.util.Objects;

import Exceptions.NotValidEmailException;
import Exceptions.NotValidNameException;
import Exceptions.NotValidPasswordException;
import Exceptions.NotValidPhoneNumberException;

public class User implements Serializable{
    private String phone, password, email, name;
    private boolean isManager;

    public User(){ }

    public User(String name, String email, String password, String phone){

        // check all details are valid
        if(!InputChecks.CheckValidEMail(email)){throw new NotValidEmailException();}
        if(!InputChecks.CheckValidPassword(password)){throw new NotValidPasswordException();}
        if(!InputChecks.CheckValidPhoneNumber(phone)){throw new NotValidPhoneNumberException();}
        if(!InputChecks.CheckValidName(name)){throw new NotValidNameException();}

        // set variables
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.name = name;
        this.isManager = false;
    }

    // getters
    public String getPhone(){return this.phone;}
    public String getPassword(){return this.password;}
    public String getEmail(){return this.email;}
    public String getName(){return this.name;}
    public boolean getIsManager(){return this.isManager;}



    @Override
    public String toString() {
        return "User{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(phone, user.phone) && Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) && Objects.equals(name, user.name);
    }



}



