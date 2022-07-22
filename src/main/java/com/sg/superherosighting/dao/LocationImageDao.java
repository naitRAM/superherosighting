/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entity.Location;
import java.io.InputStream;

/**
 *
 * @author rmans
 */
public interface LocationImageDao {
    public void saveLocationImage(Location location) throws LocationImageDaoException;
    public void deleteLocationImage(Location location) throws LocationImageDaoException;
    public InputStream getLocationImage(Location locations) throws LocationImageDaoException;
}
