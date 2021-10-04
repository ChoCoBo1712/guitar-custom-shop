package com.chocobo.customshop.util;

import com.chocobo.customshop.exception.ServiceException;
import jakarta.servlet.http.Part;

/**
 * {@code HashingUtil} interface provides functionality for uploading images.
 * @author Evgeniy Sokolchik
 */
public interface ImageUploadUtil {

    /**
     * Method that uploads given image {@link Part} to a directory specified in resources
     * and returning a path to it.
     * Or if part isn't containing any files returns path to a default image.
     *
     * @param part containing image file
     * @return string path to the stored image
     * @throws ServiceException if application is unable to upload file
     */
    String uploadImage(Part part) throws ServiceException;

    /**
     * Method that checks if given path is a path to a default picture.
     *
     * @param picturePath to image
     * @return boolean {@code true} if given path is a path to a default picture.
     */
    boolean isDefaultPicturePath(String picturePath);
}
