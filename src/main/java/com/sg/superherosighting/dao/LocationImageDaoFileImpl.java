package com.sg.superherosighting.dao;

import com.sg.superherosighting.entity.Location;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Jul. 22, 2022 purpose:
 */

@Repository
public class LocationImageDaoFileImpl implements LocationImageDao {
    private final String IMAGE_DIRECTORY = "images" + File.separator + "locations" + File.separator;
    private final String FILE_PREFIX = "location_";
    private final String FILE_EXTENSION = ".png";

    private InputStream getImageFromGoogleAPI(Location location) throws LocationImageDaoException {
        final String API_KEY = "";
        final String WIDTH_BY_HEIGHT = "300x200";
        final String ZOOM = "8";
        final String ENC_COMMA = "%2C";
        final String ENC_SPACE = "%20";
        final String BASE_URL = "https://maps.googleapis.com/maps/api/staticmap";
        final String COORDINATES = location.getLatitude() + ENC_COMMA
                + ENC_SPACE + location.getLongitude();
        final String CENTER_PARAM = "?center=" + COORDINATES;
        final String ZOOM_PARAM = "&zoom=" + ZOOM;
        final String SIZE_PARAM = "&size=" + WIDTH_BY_HEIGHT;
        final String KEY_PARAM = "&key=" + API_KEY;
        final String MARKERS_PARAM = "&markers=" + COORDINATES;
        URL url;
        InputStream image;
        try {
            url = new URL(BASE_URL + CENTER_PARAM + ZOOM_PARAM + SIZE_PARAM
                    + KEY_PARAM + MARKERS_PARAM);
            image = url.openStream();
        } catch (IOException ex) {
            throw new LocationImageDaoException("Could not retrieve static image from Google API");
        }
        return image;
    }

    @Override
    public void saveLocationImage(Location location) throws LocationImageDaoException {
        
        InputStream image = getImageFromGoogleAPI(location);
        Path filePath = Paths.get(IMAGE_DIRECTORY + FILE_PREFIX + 
                location.getLocationId() + FILE_EXTENSION);
        try {
            deleteLocationImage(location);
        } catch(LocationImageDaoException ex) {
            throw new LocationImageDaoException("Could not update location image");
        }
        try {
            Files.copy(image, filePath);
        } catch (IOException ex) {
            throw new LocationImageDaoException("Could not store location image");
        }

    }

    @Override
    public void deleteLocationImage(Location location) throws LocationImageDaoException {
        Path filePath = Paths.get(IMAGE_DIRECTORY + FILE_PREFIX + 
                location.getLocationId() + FILE_EXTENSION);
        try {
            Files.deleteIfExists(filePath);
        } catch(IOException ex) {
            throw new LocationImageDaoException("Could not delete location image");
        }
    }

    @Override
    public InputStream getLocationImage(Location location) throws LocationImageDaoException {
        Path filePath = Paths.get(IMAGE_DIRECTORY + FILE_PREFIX + 
                location.getLocationId()+ FILE_EXTENSION);
        if (! Files.exists(filePath)) {
            saveLocationImage(location);
        }
        File file = new File(filePath.toString());
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            throw new LocationImageDaoException("Could not obtain location image");
        }
    }

}
