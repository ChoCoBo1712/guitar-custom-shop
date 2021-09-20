package com.chocobo.customshop.model.dao;

public final class TableColumn {

    private TableColumn() {

    }

    // users TABLE
    public static final String USER_ID = "user_id";
    public static final String USER_EMAIL = "email";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD_HASH = "password_hash";
    public static final String USER_SALT = "salt";
    public static final String USER_ROLE = "role";
    public static final String USER_STATUS = "status";

    // woods TABLE
    public static final String WOOD_ID = "wood_id";
    public static final String WOOD_NAME = "name";

    // bodies TABLE
    public static final String BODY_ID = "body_id";
    public static final String BODY_NAME = "name";
    public static final String ID_WOOD = "id_wood";

    // pickups TABLE
    public static final String PICKUP_ID = "pickup_id";
    public static final String PICKUP_NAME = "name";

    // necks TABLE
    public static final String NECK_ID = "neck_id";
    public static final String NECK_NAME = "name";
    public static final String ID_NECK_WOOD = "id_neck_wood";
    public static final String ID_FRETBOARD_WOOD = "id_fretboard_wood";
    public static final String NECK_TUNER = "tuner";
}
