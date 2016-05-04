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

    public static String getSectionsInfoQuery() {
        return sectionsInfoQuery;
    }
    
    
}
