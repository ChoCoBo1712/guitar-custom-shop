package com.chocobo.customshop.web.command;

/**
 * {@code RequestAttribute} class contains constant strings that are stored as {@link java.net.http.HttpRequest} attributes.
 * @author Evgeniy Sokolchik
 */
public final class RequestAttribute {

    public static final String COMMAND = "command";
    public static final String EMAIL = "email";
    public static final String LOGIN = "login";
    public static final String ROLE = "role";
    public static final String STATUS = "status";
    public static final String PASSWORD = "password";
    public static final String TOKEN = "token";
    public static final String PAGINATION_START = "start";
    public static final String PAGINATION_LENGTH = "length";
    public static final String DRAW = "draw";
    public static final String FILTER_CRITERIA = "filterCriteria";
    public static final String SEARCH_VALUE = "search[value]";
    public static final String RECORDS_TOTAL = "recordsTotal";
    public static final String RECORDS_FILTERED = "recordsFiltered";
    public static final String DATA = "data";
    public static final String ENTITY_ID = "id";
    public static final String USER = "user";
    public static final String WOOD = "wood";
    public static final String NAME = "name";
    public static final String REQUEST_TYPE = "requestType";
    public static final String ENTITY = "entity";
    public static final String BODY = "body";
    public static final String TERM = "term";
    public static final String PAGE = "page";
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGINATION_MORE = "paginationMore";
    public static final String RESULTS = "results";
    public static final String WOOD_ID = "woodId";
    public static final String PICKUP = "pickup";
    public static final String NECK = "neck";
    public static final String FRETBOARD_WOOD_ID = "fretboardWoodId";
    public static final String BODY_ID = "bodyId";
    public static final String NECK_ID = "neckId";
    public static final String PICKUP_ID = "pickupId";
    public static final String USER_ID = "userId";
    public static final String COLOR = "color";
    public static final String NECK_JOINT = "neckJoint";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String PICTURE_PATH = "picturePath";
    public static final String GUITAR = "guitar";
    public static final String ACTIVE_ORDER = "activeOrder";
    public static final String LOGIN_ERROR = "loginError";
    public static final String FORGOT_PASSWORD_ERROR = "forgotPasswordError";
    public static final String DUPLICATE_EMAIL_ERROR = "duplicateEmailError";
    public static final String DUPLICATE_LOGIN_ERROR = "duplicateLoginError";
    public static final String VALIDATION_ERROR = "validationError";
    public static final String EMAIL_CONFIRMATION_TOKEN = "emailConfirmationToken";
    public static final String PASSWORD_CHANGE_TOKEN = "passwordChangeToken";
    public static final String EMAIL_CONFIRMATION_SUCCESS = "emailConfirmationSuccess";
    public static final String PASSWORD_CHANGE_SUCCESS = "passwordChangeSuccess";
    public static final String PROFILE_UPDATED = "profileUpdated";
    public static final String GUITAR_CONSTRUCTED = "guitarConstructed";

    private RequestAttribute() {

    }
}
