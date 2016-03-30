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
    
    public ResultSet getAllSections(){
        String sqlQuery = "SELECT * FROM SECTIONS";
        return Helper.workWithDB(sqlQuery);
    }
    
    public ResultSet getTopicsFromSect(){
        Map <String,String> parameters = Helper.getQueryMap();
        String section_id = parameters.get("section_id");
        String sqlQuery = "SELECT * FROM TOPICS WHERE SECTION_ID = " + section_id;
        return Helper.workWithDB(sqlQuery);
    }
    
    public ResultSet getMessFromTop(){
        Map <String,String> parameters = Helper.getQueryMap();
        String topic_id = parameters.get("topic_id");
        String sqlQuery = "SELECT * FROM MESSAGES WHERE TOPIC_ID  = " + topic_id;
        return Helper.workWithDB(sqlQuery);
    }

    public ForumBean() {

    }

}
