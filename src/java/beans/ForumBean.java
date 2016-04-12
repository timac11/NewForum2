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
import java.io.IOException;
import java.util.Locale;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
    private String section_id;
    private String topic_id;

    //Колличество выводимых на страницу сообщений - топиков.
    private final int amountString = 10;
    //Для прогрузки по countOnPage из базы данных.При увеличении выгружает следующие 10
    private int pageTopics = 0;
    private int pageMessages = 0;

    public void setSection_id(int pageTopics) {
        this.pageTopics = pageTopics;
    }

    public int getPageTopics() {
        return pageTopics;
    }

    public void setPageTopics(int pageTopics) {
        this.pageTopics = pageTopics;
    }

    public int getPageMessages() {
        return pageMessages;
    }

    public void setPageMessages(int pageMessages) {
        this.pageMessages = pageMessages;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addNewSection() throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        Section sect = new Section();
        sect.setName(this.getName());
        sect.setUser_id(12L);
        Date d = new Date(199);
        sect.setDate(d);
        Factory_sect.getInstance().getSectionDAO().addSection(sect);
    }

    public String setNewSection() {
        // Date d = new Date();
        System.out.println("asdas");
        String Query = "INSERT INTO SECTIONS(SECTION_ID, SECTION_NAME ,USER_ID ,DATE_T) VALUES(3,'asdf',2,'2-APR-16')";
        Helper.addToDB(Query);
        return "/secured/forum.xhtml";
    }

    public ResultSet getAllSections() {
        String sqlQuery = "SELECT * FROM SECTIONS";
        return Helper.workWithDB(sqlQuery);
    }

    public ResultSet getTopicsFromSect() {
        Map<String, String> parameters = Helper.getQueryMap();
        if (parameters != null) {
            section_id = parameters.get("section_id");
        }
        String sqlQuery = "SELECT sort.* FROM (SELECT * FROM TOPICS WHERE SECTION_ID = ? ORDER BY DATE_T) sort "
                + "WHERE rownum > ? AND rownum <= ?";
        return Helper.workWithDB(sqlQuery, section_id, Integer.toString(pageTopics * amountString), Integer.toString((pageTopics + 1) * amountString));
    }

    public ResultSet getMessageFromTop() {
        Map<String, String> parameters = Helper.getQueryMap();
        String topic_id = parameters.get("topic_id");
        String sqlQuery = "SELECT sort.* FROM (SELECT u.USER_NAME as USER_NAME, m.MESSAGE as MESSAGE,m.DATE_T as DATE_T "
                + "FROM USERS u, MESSAGES m WHERE m.TOPIC_ID = ? AND m.USER_ID = u.USER_ID ORDER BY m.DATE_T) sort "
                + "WHERE rownum > ? AND rownum <= ?";
        return Helper.workWithDB(sqlQuery, topic_id, Integer.toString(pageMessages * amountString), Integer.toString((pageMessages + 1) * amountString));
    }

    public void nextPage() throws IOException {
        pageTopics++;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()); 
    }

    public void previousPage() {
        if (true) {
            pageTopics--;
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("main");
        }
//        else if(from.equals("messages")){
//            pageMessages--;
//            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("messages");
//        }

    }

    public ForumBean() {

    }

}
