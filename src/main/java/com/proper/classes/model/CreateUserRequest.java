package com.proper.classes.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateUserRequest {
    @NotBlank(message = "First name can not be empty")
    @Size(min = 2, max = 50, message = "First name size issue")
    private String firstName;
    @NotBlank(message = "Last name can not be empty")
    @Size(min = 2, max = 50, message = "Last name size issue")
    private String lastName;
    @NotBlank(message = "Email can not be empty")
    private String email;
    @NotBlank(message = "Password can not be empty")
    @Size(min = 8, max = 16, message = "Password size issue")
    private String password;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

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
