package com.chocobo.customshop.controller.tag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.chocobo.customshop.controller.command.SessionAttribute.LOCALE;

public class LocaleTag extends TagSupport {

    private static final String BUNDLE_NAME = "properties.pagecontent";

    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpSession session = request.getSession();

        String localeCode = (String) session.getAttribute(LOCALE);
        String[] codeParts = localeCode.split("_");
        Locale locale = new Locale(codeParts[0], codeParts[1]);
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        String localizedString = bundle.getString(key);

        try {
            JspWriter out = pageContext.getOut();
            out.write(localizedString);
        } catch (IOException e) {
            throw new JspException("An error occurred processing tag: ", e);
        }

        return SKIP_BODY;
    }
}
