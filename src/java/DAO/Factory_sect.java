package DAO;

import DAOimpl.SectionDAOimpl;

public class Factory_sect {
      
      private static SectionsDAO sectionDAO = null;
      private static Factory_sect instance = null;

      public static synchronized Factory_sect getInstance(){
            if (instance == null){
              instance = new Factory_sect();
            }
            return instance;
      }

      public SectionsDAO getSectionDAO(){
            if (sectionDAO == null){
              sectionDAO = new SectionDAOimpl();
            }
            return sectionDAO;
      }   
}