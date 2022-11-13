package xyz.fmdb.fmdb.validations;

import org.springframework.web.multipart.MultipartFile;
import xyz.fmdb.fmdb.annotations.Image;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageFileConstraintsValidator implements ConstraintValidator<Image, MultipartFile> {

    @Override
    public void initialize(Image constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

        boolean result = true;

        String contentType = multipartFile.getContentType();
        if (!isSupportedContentType(contentType)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Only PNG or JPG images are allowed.")
                    .addConstraintViolation();

            result = false;
        }

        return result;
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}