package xyz.fmdb.fmdb.resources.auth.entity;

import xyz.fmdb.fmdb.common.validations.Password;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class AccountRequest {

    @NotBlank(message = "username must not be blank")
    private String username;

    @NotBlank(message = "password must not be blank")
    @Password
    private String password;

    public AccountRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
