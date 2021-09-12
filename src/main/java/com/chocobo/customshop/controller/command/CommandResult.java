package com.chocobo.customshop.controller.command;

public class CommandResult {

    public enum RouteType {
        FORWARD,
        REDIRECT,
        ERROR,
        JSON
    }

    private final Object route;
    private final RouteType routeType;

    public CommandResult(Object route, RouteType routeType) {
        this.route = route;
        this.routeType = routeType;
    }

    public Object getRoute() {
        return route;
    }

    public RouteType getRouteType() {
        return routeType;
    }
}
