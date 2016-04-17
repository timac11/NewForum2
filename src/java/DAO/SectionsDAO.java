/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.SQLException;
import logic.Section;

/**
 *
 * @author aser 2014
 */
public interface SectionsDAO {
     public boolean addSection (Section section)throws SQLException;
     public Section getSectionByName (String name) throws SQLException;
}
