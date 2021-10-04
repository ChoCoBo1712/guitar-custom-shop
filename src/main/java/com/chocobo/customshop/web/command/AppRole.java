package com.chocobo.customshop.web.command;

/**
 * {@code AppRole} enum contains roles used in application.
 * @author Evgeniy Sokolchik
 */
public enum AppRole {
    /**
     * System administrator role.
     */
    ADMIN,
    /**
     * Guitar maker role.
     */
    MAKER,
    /**
     * Site client role.
     */
    CLIENT,
    /**
     * Not confirmed user.
     */
    NOT_CONFIRMED,
    /**
     * Guest unauthenticated user.
     */
    GUEST
}
