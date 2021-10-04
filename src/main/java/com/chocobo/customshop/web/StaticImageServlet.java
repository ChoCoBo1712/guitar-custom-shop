package com.chocobo.customshop.web;

import com.chocobo.customshop.util.impl.ImageUploadUtilImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * {@code StaticImageServlet} class is a subclass of {@link HttpServlet} class.
 * It processes get requests to {@code /images} url after filtering and is used for loading images.
 * @author Evgeniy Sokolchik
 */
@WebServlet(urlPatterns = "/images/*")
public class StaticImageServlet extends HttpServlet {

    private static final int PATH_SLASH_INDEX = 1;
    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    private static final String CONTENT_LENGTH_HEADER_NAME = "Content-Length";
    private static final String CONTENT_DISPOSITION_HEADER_NAME = "Content-Disposition";
    private static final String CONTENT_DISPOSITION_HEADER_VALUE = "inline; filename=\"%s\"";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = URLDecoder.decode(request.getPathInfo().substring(PATH_SLASH_INDEX), StandardCharsets.UTF_8);
        File file = new File(ImageUploadUtilImpl.uploadDirectory, filename);
        response.setHeader(CONTENT_TYPE_HEADER_NAME, getServletContext().getMimeType(filename));
        response.setHeader(CONTENT_LENGTH_HEADER_NAME, String.valueOf(file.length()));
        response.setHeader(CONTENT_DISPOSITION_HEADER_NAME, String.format(CONTENT_DISPOSITION_HEADER_VALUE, file.getName()));
        Files.copy(file.toPath(), response.getOutputStream());
    }
}