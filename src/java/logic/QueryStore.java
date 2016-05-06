/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author Mary
 */
public class QueryStore {
    
    private static final String sectionsInfoQuery = "SELECT s.SECTION_ID, s.SECTION_NAME, "
        +"(SELECT count(*) FROM TOPICS t WHERE s.SECTION_ID = t.SECTION_ID ) as SECTION_TOPICS,"
        +"(SELECT count(*) FROM messages WHERE TOPIC_ID in"
        +"(SELECT  t.topic_id from topics t WHERE t.SECTION_ID=s.SECTION_ID)) as SECTION_Messages FROM SECTIONS s";
    
    private static final String currentSectionNameQuery = "SELECT SECTION_NAME FROM SECTIONS WHERE SECTION_ID = ?";
    
    private static final String currentTopicNameQuery = "SELECT TOPIC_NAME FROM TOPICS WHERE TOPIC_ID = ?";
    
     private static final String topicsInfoQuery = "SELECT t.TOPIC_ID, t.TOPIC_NAME, "
             +"(SELECT count(*) FROM MESSAGES m WHERE m.TOPIC_ID = t.TOPIC_ID) as MESSAGES,"
             +" (SELECT u.USER_NAME FROM USERS u WHERE t.USER_ID = u.USER_ID) as CREATOR,"
             +"(SELECT NVL(TO_CHAR(MAX(m.DATE_T),'MM.DD.YYYY'),' ') FROM MESSAGES m WHERE m.TOPIC_ID=t.TOPIC_ID) as LATEST"
             +" FROM TOPICS t WHERE t.SECTION_ID = ? ORDER BY t.DATE_T ASC";

    public static String getCurrentSectionNameQuery() {
        return currentSectionNameQuery;
    }

    public static String getTopicsInfoQuery() {
        return topicsInfoQuery;
    }
    
    public static String getCurrentTopicNameQuery() {
        return currentTopicNameQuery;
    }
    
    public static String getSectionsInfoQuery() {
        return sectionsInfoQuery;
    }


    
    
}
