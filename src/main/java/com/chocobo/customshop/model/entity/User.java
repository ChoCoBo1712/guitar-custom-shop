package com.chocobo.customshop.model.entity;

import jakarta.jws.soap.SOAPBinding;

import java.util.Arrays;
import java.util.Objects;

public class User extends AbstractEntity {

    public enum UserRole {
        ADMIN,
        MASTER,
        CLIENT
    }

    public enum UserStatus {
        NOT_CONFIRMED,
        CONFIRMED,
        DELETED
    }

    private String email;
    private String login;
    private transient byte[] passwordHash;
    private transient byte[] salt;
    private UserRole role;
    private UserStatus status;

    private User() {

    }

    public static UserBuilder builder() {
        return new User().new UserBuilder();
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
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

    public class UserBuilder extends AbstractBuilder {

        private UserBuilder() {

        }

        public UserBuilder setLogin(String login) {
            User.this.login = login;
            return this;
        }

        public UserBuilder setEmail(String email) {
            User.this.email = email;
            return this;
        }

        public UserBuilder setPasswordHash(byte[] passwordHash) {
            User.this.passwordHash = passwordHash;
            return this;
        }

        public UserBuilder setSalt(byte[] salt) {
            User.this.salt = salt;
            return this;
        }

        public UserBuilder setRole(UserRole role) {
            User.this.role = role;
            return this;
        }

        public UserBuilder setStatus(UserStatus status) {
            User.this.status = status;
            return this;
        }

        public UserBuilder of(User user) {
            super.of(user);
            User.this.login = user.login;
            User.this.email = user.email;
            User.this.passwordHash = user.passwordHash;
            User.this.salt = user.salt;
            User.this.role = user.role;
            User.this.status = user.status;
            return this;
        }

        @Override
        public User build() {
            return User.this;
        }
    }
}
