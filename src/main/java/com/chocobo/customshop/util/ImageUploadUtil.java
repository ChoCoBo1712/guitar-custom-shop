package com.chocobo.customshop.util;

import com.chocobo.customshop.exception.ServiceException;
import jakarta.servlet.http.Part;

public interface ImageUploadUtil {

    String uploadImage(Part part) throws ServiceException;

    boolean isDefaultPicturePath(String picturePath);
}
