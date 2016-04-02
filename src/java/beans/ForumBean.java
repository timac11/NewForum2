/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Map;
import java.sql.*;
import helpers.*;

/**
 *
 * @author User
 */
@ManagedBean(name = "forumBean")
@SessionScoped
public class ForumBean implements Serializable {

    public ResultSet getAllSections() {
        String sqlQuery = "SELECT * FROM SECTIONS";
        return Helper.workWithDB(sqlQuery);
    }

    public ResultSet getTopicsFromSect() {
        Map<String, String> parameters = Helper.getQueryMap();
        String section_id = parameters.get("section_id");
        //String sqlQuery = "SELECT * FROM TOPICS WHERE SECTION_ID =  :?" + section_id; Доделать. Вынести в отдельный класс запросы и параметр доделать
        String sqlQuery = "SELECT * FROM TOPICS WHERE SECTION_ID = " + section_id;
        return Helper.workWithDB(sqlQuery);
    }

    public ResultSet getMessageFromTop() {
        Map<String, String> parameters = Helper.getQueryMap();
        String topic_id = parameters.get("topic_id");
        String sqlQuery = "SELECT u.USER_NAME as USER_NAME, m.MESSAGE as MESSAGE,m.DATE_T "
                + "as DATE_T FROM USERS u, MESSAGES m WHERE m.TOPIC_ID = " + topic_id + 
                " AND m.USER_ID = u.USER_ID ORDER BY m.DATE_T";
        return Helper.workWithDB(sqlQuery);
    }

    public ForumBean() {

    }

}
