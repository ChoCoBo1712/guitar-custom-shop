package com.chocobo.customshop.controller.command;

public class CommandResult {

    public enum RouteType {
        FORWARD,
        REDIRECT,
        JSON
    }

    private final String route;
    private final RouteType routeType;

    public CommandResult(String route, RouteType routeType) {
        this.route = route;
        this.routeType = routeType;
    }

    public String getRoute() {
        return route;
    }

    public RouteType getRouteType() {
        return routeType;
    }
}
