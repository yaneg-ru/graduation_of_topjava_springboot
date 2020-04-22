package ru.yaneg.graduation_of_topjava_springboot.ui.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserDetailsRequest {
    @Size(min = 3, max = 20)
    private String firstName;
    @Size(min = 3, max = 20)
    private String lastName;
    @Email
    private String email;
    @Size(min = 7, max = 20)
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
