package com.chocobo.customshop.util.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.util.ImageUploadUtil;
import jakarta.servlet.http.Part;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImageUploadUtilImplTest {
    private static final String DIRECTORY = "C:\\statics\\";
    private final int RANDOM_LENGTH = 8;

    ImageUploadUtil imageUploadUtil = ImageUploadUtilImpl.getInstance();

    @Test
    void testIfFileExists() throws ServiceException, IOException {
        String fileName = "fileName";
        String rand = "RAND";

        Part part = Mockito.mock(Part.class);
        when(part.getSubmittedFileName()).thenReturn(fileName);
        mockStatic(RandomStringUtils.class).when(() -> RandomStringUtils.randomAlphanumeric(RANDOM_LENGTH)).thenReturn(rand);

        String path = imageUploadUtil.uploadImage(part);

        verify(part).write(DIRECTORY + rand + fileName);
        assertEquals(ImageUploadUtilImpl.IMAGES_URL + rand + fileName, path);
    }

    @Test
    void testIfFileNotExists() throws ServiceException {
        Part part = Mockito.mock(Part.class);
        when(part.getSubmittedFileName()).thenReturn("");

        String path = imageUploadUtil.uploadImage(part);

        assertEquals(ImageUploadUtilImpl.IMAGES_URL + ImageUploadUtilImpl.DEFAULT_IMAGE_NAME, path);
    }
}