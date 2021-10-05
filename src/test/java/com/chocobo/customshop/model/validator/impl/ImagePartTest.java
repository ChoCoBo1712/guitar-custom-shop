package com.chocobo.customshop.model.validator.impl;

import jakarta.servlet.http.Part;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImagePartTest {

    @ParameterizedTest
    @MethodSource("provideImagePart")
    public void testValidate(boolean expected, Part part) {
        boolean actual = ImagePartValidator.getInstance().validate(part);
        assertEquals(actual, expected);
    }

    private Stream<Arguments> provideImagePart() {
        return Stream.of(
                Arguments.of(true, createPart("image/jpeg", "image")),
                Arguments.of(true, createPart("image/png", "file")),
                Arguments.of(true, createPart("image/png", "")),
                Arguments.of(true, createPart("image/webp", "")),
                Arguments.of(false, createPart("image/webp", "file")),
                Arguments.of(false, createPart("text/html", "text"))
        );
    }

    private Part createPart(String contentType, String submittedFileName) {
        Part part = Mockito.mock(Part.class);
        when(part.getContentType()).thenReturn(contentType);
        when(part.getSubmittedFileName()).thenReturn(submittedFileName);
        return part;
    }
}
