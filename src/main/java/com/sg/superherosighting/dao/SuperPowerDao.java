/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entity.SuperPower;
import java.util.List;

/**
 *
 * @author rmans
 */
public interface SuperPowerDao {
    public SuperPower getSuperPowerById(int id);
    public List<SuperPower> getAllSuperPowers();
    public SuperPower addSuperPower(SuperPower superPower);
    public void updateSuperPower(SuperPower superPower);
    public void deleteSuperPower(SuperPower superPower);
}
