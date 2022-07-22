/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import java.io.File;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rmans
 */
public interface HeroImageDao {
    public InputStream getHeroImage(int heroId);
    public void saveHeroImage(MultipartFile image, int heroId) throws HeroImageDaoException;
    public void deleteHeroImage(int heroId) throws HeroImageDaoException;
}
