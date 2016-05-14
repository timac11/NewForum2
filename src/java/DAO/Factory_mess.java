package DAO;

import DAOimpl.MessageDAOimpl;

public class Factory_mess {
      
      private static MessagesDAO messageDAO = null;
      private static Factory_mess instance = null;

      public static synchronized Factory_mess getInstance(){
            if (instance == null){
              instance = new Factory_mess();
            }
            return instance;
      }

      public MessagesDAO getMessageDAO(){
            if (messageDAO == null){
              messageDAO = new MessageDAOimpl();
            }
            return messageDAO;
      }   
}