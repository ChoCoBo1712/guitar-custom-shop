package com.chocobo.customshop.web.command;

/**
 * {@code CommandResult} class stores a result of {@link Command} instances execution.
 * @author Evgeniy Sokolchik
 */
public class CommandResult {


    /**
     * {@code RouteType} enum shows the way for controller on how to handle the command result.
     */
    public enum RouteType {
        /**
         * Forward to JSP pages.
         */
        FORWARD,
        /**
         * Redirect to URLs.
         */
        REDIRECT,
        /**
         * Write JSON for ajax requests.
         */
        JSON,
        /**
         * Send error.
         */
        ERROR
    }

    private final String route;
    private final Integer errorCode;
    private final RouteType routeType;

    private CommandResult(String route, Integer errorCode, RouteType routeType) {
        this.route = route;
        this.errorCode = errorCode;
        this.routeType = routeType;
    }

    /**
     * Instantiates a new forward {@code CommandResult}.
     *
     * @param route is a local path to JSP.
     */
    public static CommandResult createForwardResult(String route) {
        return new CommandResult(route, null, RouteType.FORWARD);
    }

    /**
     * Instantiates a new redirect {@code CommandResult}.
     *
     * @param route is a URL string to redirect to.
     */
    public static CommandResult createRedirectResult(String route) {
        return new CommandResult(route, null, RouteType.REDIRECT);
    }

    /**
     * Instantiates a new JSON {@code CommandResult}.
     *
     * @param route is a JSON string representation.
     */
    public static CommandResult createJsonResult(String route) {
        return new CommandResult(route, null, RouteType.JSON);
    }

    /**
     * Instantiates a new JSON {@code CommandResult}.
     *
     * @param errorCode is a code sent to the server.
     */
    public static CommandResult createErrorResult(Integer errorCode) {
        return new CommandResult(null, errorCode, RouteType.ERROR);
    }

    public String getRoute() {
        return route;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public RouteType getRouteType() {
        return routeType;
    }
}
