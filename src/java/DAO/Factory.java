package DAO;

import DAOimpl.UsersDAOimpl;

public class Factory {
      
      private static UsersDAO studentDAO = null;
      private static Factory instance = null;

      public static synchronized Factory getInstance(){
            if (instance == null){
              instance = new Factory();
            }
            return instance;
      }

      public UsersDAO getUserDAO(){
            if (studentDAO == null){
              studentDAO = new UsersDAOimpl();
            }
            return studentDAO;
      }   

   
}