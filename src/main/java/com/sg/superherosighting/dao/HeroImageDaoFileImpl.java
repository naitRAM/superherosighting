package com.sg.superherosighting.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Jul. 21, 2022 purpose:
 */
@Repository
public class HeroImageDaoFileImpl implements HeroImageDao {

    private final String IMAGE_DIRECTORY = "images" + File.separator + "heroes" + File.separator;
    private final String FILE_PREFIX = "hero_";
    private final String[] FILE_EXTENSIONS = {".jpg", ".jpeg", ".png"};

    public HeroImageDaoFileImpl() {
        Path path = Paths.get(IMAGE_DIRECTORY);
        if (!Files.exists(path)) {
            File file = new File(IMAGE_DIRECTORY);
            file.mkdirs();
        }
    }

    @Override
    public Path getFilePath(int heroId) {
        for (String ext : FILE_EXTENSIONS) {
            Path path = assignFilePath(heroId, ext);
            if (Files.exists(path)) {
                return path;
            }
        }
        return null;
    }

    private Path assignFilePath(int heroId, String fileExtension) {
        return Paths.get(IMAGE_DIRECTORY + FILE_PREFIX + heroId + fileExtension);
    }

    @Override
    public InputStream getHeroImage(int heroId){
        Path path = getFilePath(heroId);
        if (path != null) {

            InputStream image;
            try {
                image = new FileInputStream(path.toString());
            } catch (FileNotFoundException ex) {
                return null;
            }
            return image;
        }
        return null;
    }

    @Override
    public void saveHeroImage(MultipartFile imageFile, int heroId) throws HeroImageDaoException {
        String fileName = imageFile.getOriginalFilename();
        boolean isValidFileType = false;
        if (imageFile.isEmpty()) {
            throw new HeroImageDaoException("File is empty");
        }
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            throw new HeroImageDaoException("Invalid file type");
        }
        String fileExtension = fileName.substring(dotIndex);
        for (String ext : FILE_EXTENSIONS) {
            if (fileExtension.equals(ext)) {
                isValidFileType = true;
            }
        }
        if (!isValidFileType) {
            throw new HeroImageDaoException("Invalid file type");
        }
        deleteHeroImage(heroId);
        Path path = assignFilePath(heroId, fileExtension);
        File file = new File(path.toString());
        try {
            InputStream image = imageFile.getInputStream();
            Files.copy(image, path);
            image.close();
        } catch (IOException ex) {
            throw new HeroImageDaoException("Image could not be stored");
        }

    }

    @Override
    public void deleteHeroImage(int heroId) throws HeroImageDaoException {
        Path path = getFilePath(heroId);
        if (path != null) {
            try {
                Files.delete(path);
            } catch (IOException ex) {
                throw new HeroImageDaoException("Error occured during delete operation");
            }
        }
    }

}
