package com.chocobo.customshop.web.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.chocobo.customshop.web.command.SessionAttribute.USER_ID;

@WebListener
public class HttpSessionAttributeListenerImpl implements HttpSessionAttributeListener {
    private static final Map<Long, HttpSession> allSessions = new ConcurrentHashMap<>();

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        String attributeName = event.getName();

        if (attributeName.equals(USER_ID)) {
            long userId = Long.parseLong(session.getAttribute(USER_ID).toString());
            allSessions.put(userId, session);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        String attributeName = event.getName();

        if (attributeName.equals(USER_ID)) {
            long userId = Long.parseLong(event.getValue().toString());
            allSessions.remove(userId);
        }
    }

    /**
     * Find an authenticated session by its owner's id.
     *
     * @param userId unique id of ther user.
     * @return {@link HttpSession} instance wrapped with {@link Optional}.
     */
    public static Optional<HttpSession> findSession(long userId) {
        HttpSession session = allSessions.get(userId);

        return session != null
                ? Optional.of(session)
                : Optional.empty();
    }
}
