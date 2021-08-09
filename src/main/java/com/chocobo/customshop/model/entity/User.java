package com.chocobo.customshop.model.entity;

import java.util.Arrays;
import java.util.Objects;

public class User extends BaseEntity {

    public enum UserRole {
        GUEST,
        CLIENT,
        MASTER,
        ADMIN
    }

    public enum UserStatus {
        NOT_CONFIRMED,
        CONFIRMED,
        DELETED
    }

    private String email;
    private String login;
    private byte[] passwordHash;
    private byte[] salt;
    private UserRole role;
    private UserStatus status;

    public User(String email, String login, byte[] passwordHash, byte[] salt, UserRole role, UserStatus status) {
        // TODO: 09.08.2021 fix sending default value of long to constructor
        super(0);
        this.email = email;
        this.login = login;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.role = role;
        this.status = status;
    }

    public User(long entityId, String email, String login, byte[] passwordHash, byte[] salt, UserRole role, UserStatus status) {
        super(entityId);
        this.email = email;
        this.login = login;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.role = role;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        User user = (User) obj;
        return super.equals(user) && Objects.equals(user.login, login) && Objects.equals(user.email, email)
                && Arrays.equals(user.passwordHash, passwordHash) && Arrays.equals(user.salt, salt) && Objects.equals(user.role, role)
                && Objects.equals(user.status, status);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + super.hashCode();

        result = prime * result + (login != null ? login.hashCode() : 0);
        result = prime * result + (email != null ? email.hashCode() : 0);
        result = prime * result + (passwordHash != null ? Arrays.hashCode(passwordHash) : 0);
        result = prime * result + (passwordHash != null ? Arrays.hashCode(salt) : 0);
        result = prime * result + (role != null ? role.hashCode() : 0);
        result = prime * result + (status != null ? status.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("User ");
        builder.append(super.toString()).append(": (");
        builder.append("login = ").append(login).append(", ");
        builder.append("email = ").append(email).append(", ");
        builder.append("user role = ").append(role).append(", ");
        builder.append("user status = ").append(status).append(")");

        return builder.toString();
    }
}
