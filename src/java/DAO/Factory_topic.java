package DAO;

import DAOimpl.TopicDAOimpl;

public class Factory_topic {
      
      private static TopicsDAO topicDAO = null;
      private static Factory_topic instance = null;

      public static synchronized Factory_topic getInstance(){
            if (instance == null){
              instance = new Factory_topic();
            }
            return instance;
      }

      public TopicsDAO getTopicDAO(){
            if (topicDAO == null){
              topicDAO = new TopicDAOimpl();
            }
            return topicDAO;
      }   
}