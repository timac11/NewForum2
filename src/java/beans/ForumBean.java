/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import DAO.Factory;
import DAO.Factory_sect;
import DAOimpl.SectionDAOimpl;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Map;
import java.sql.*;
import helpers.*;
import java.util.Locale;
import logic.Section;
import static logic.hash_password.md5Apache;

/**
 *
 * @author User
 */
@ManagedBean(name = "forumBean")
@SessionScoped
public class ForumBean implements Serializable {
    private String name;
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addNewSection() throws SQLException
    {
        Locale.setDefault(Locale.ENGLISH);
        Section sect = new Section();
        sect.setName(this.getName());
        sect.setUser_id(12L);
        Date d = new Date(199);
        sect.setDate(d);
        Factory_sect.getInstance().getSectionDAO().addSection(sect);
    }
    public String setNewSection () {
       // Date d = new Date();
       System.out.println("asdas");
       String  Query = "INSERT INTO SECTIONS(SECTION_ID, SECTION_NAME ,USER_ID ,DATE_T) VALUES(3,'asdf',2,'2-APR-16')";
        Helper.addToDB(Query);
       return "/secured/forum.xhtml";
    }
        
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
