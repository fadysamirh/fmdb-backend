package xyz.fmdb.fmdb.common.utils;

import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
    public static void deleteFile(String deleteDir,String fileName) throws IOException {
        Path deletePath = Paths.get(deleteDir+"/"+fileName);

        try  {
            Files.delete(deletePath);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            throw new IOException("Could not delete image file: " + fileName, ioe);
        }
    }
}