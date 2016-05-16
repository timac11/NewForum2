/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import DAOimpl.UsersDAOimpl;
import DAO.Factory;
import DAO.Factory_mess;
import DAO.Factory_sect;
import DAO.Factory_topic;
import DAOimpl.SectionDAOimpl;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Map;
import java.sql.*;
import helpers.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import logic.Message;
import logic.Section;
import logic.Topic;
import logic.User;
import static logic.hash_password.md5Apache;

/**
 *
 * @author User
 */
@ManagedBean(name = "forumBean")
@SessionScoped
public class ForumBean implements Serializable {

    private WorkDB workDB;
    private String nameNewSection;
    private String section_id;
    private String topic_id;

    //Колличество выводимых на страницу сообщений - топиков.
    private final int amountString = 10;
    //Для прогрузки по amountOnPage строк из базы данных.При увеличении выгружает следующие 10
    private int pageTopics = 0;
    private int pageMessages = 0;

    private boolean lastPageTop = false;
    private boolean lastPageMess = false;
    private boolean itSearch = false;
    private String textSearch;
    private String message;
    private String nameNewTopic;
    
    
    public String getNameNewTopic(){
        return this.nameNewTopic;
    }
    
    public void setNameNewTopic(String name){
        this.nameNewTopic = name;
    }
    
    public String getMessage(){
        return this.message;
    }
    
    public void setMessage(String mess){
    this.message = mess;
    }
    
    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    private UIComponent mybutton;

    public boolean isLastPageTop() {
        return lastPageTop;
    }

    public boolean isLastPageMess() {
        return lastPageMess;
    }

    public String isTopics() {
        return "TOPICS";
    }

    public String isMessag() {
        return "MESSAGES";
    }

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

    public boolean isItSearch() {
        return itSearch;
    }

    public String getNameNewSection() {
        return nameNewSection;
    }

    public void setNameNewSection(String nameNewSection) {
        this.nameNewSection = nameNewSection;
    }

    public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }

    public UIComponent getMybutton() {
        return mybutton;
    }

    public void addNewSection(long user_id) throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        if (!((Factory_sect.getInstance().getSectionDAO().getSectionByName(this.nameNewSection)).getName().equals(""))) {
            FacesMessage msg = new FacesMessage("Sorry, this section there is. Try again");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), msg);
        } else {
            Section sect = new Section();
            sect.setName(this.nameNewSection);
            sect.setUser_id(user_id);
            //sect.setUser_id(1L);
            Date d = new Date(System.currentTimeMillis());
            sect.setDate(d);
            if (!Factory_sect.getInstance().getSectionDAO().addSection(sect)) {
                FacesMessage msg = new FacesMessage("Sorry, system error. Try again");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(mybutton.getClientId(context), msg);
            }
        this.nameNewSection = "";
        }

    }
    
        public void addNewMessage(long user_id) throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
            Message mess = new Message();
            mess.setMessage(this.message);
            mess.setUser_id(user_id);
            long val = new Long(this.getCurrentTopicID()); 
            mess.setTop_id(val);
            Date d = new Date(System.currentTimeMillis());
            mess.setDate(d);
            if (!Factory_mess.getInstance().getMessageDAO().addMessage(mess)) {
                FacesMessage msg = new FacesMessage("Sorry, system error. Try again");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(mybutton.getClientId(context), msg);
            }
            this.message = "";
        }

     public void addNewTopic (long user_id) throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        if (!((Factory_topic.getInstance().getTopicDAO().getTopicByName(this.nameNewTopic)).getName().equals(""))) {
            FacesMessage msg = new FacesMessage("Sorry, this topic there is. Try again");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), msg);
        } else {
            Topic top = new Topic();
            top.setName(this.nameNewTopic);
            top.setUser_id(user_id);
            long val = new Long(this.getCurrentSectionID()); 
            top.setSection_id(val);
            Date d = new Date(System.currentTimeMillis());
            top.setDate(d);
            if (!Factory_topic.getInstance().getTopicDAO().addTopic(top)) {
                FacesMessage msg = new FacesMessage("Sorry, system error. Try again");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(mybutton.getClientId(context), msg);
            }
        }
        this.nameNewTopic = "";
        

    }

//    public ResultSet getAllSections() {
//        connect();
//        return resultSet("S");
//    }
    public ResultSet getSectionsInfo() {
        connect();
        return resultSet("SI");
    }

    public ResultSet getTopicsInfo() throws SQLException {
        connect();
        return resultSet("TI", getCurrentSectionID());
    }

    //// НЕ РАБОТАЕТ ЕСЛИ ВЕРНУТЬСЯ НА СТРАНИЦУ СЕКЦИЙ ИЗ ТОПИКОВ! =(
    public String getCurrentSectionName() throws SQLException {
        connect();
        Map<String, String> parameters = Helper.getQueryMap();
        if (parameters != null) {
            section_id = parameters.get("section_id");
        }
        ResultSet rs = resultSet("CSN", section_id);
        if (rs != null) {
            rs.next();
            return rs.getString("SECTION_NAME");
        } else {
            return "NO QUERY_MAP";
        }
//        if (parameters != null) {
//            section_id = parameters.get("section_id");
//            ResultSet rs = resultSet("CSN",section_id);
//            rs.next();
//            return rs.getString("SECTION_NAME");
//        }else{
//            return "NO QUERY_MAP";
//        }
    }

    public String getCurrentSectionID() throws SQLException {
        connect();
        Map<String, String> parameters = Helper.getQueryMap();
        if (parameters != null) {
            section_id = parameters.get("section_id");
        }

        return section_id;

//        if (parameters != null) {
//            section_id = parameters.get("section_id");
//            return section_id;
//        }else{
//            return "NO QUERY_MAP";
//        }
    }

    public String getCurrentTopicName() throws SQLException {
        connect();
        Map<String, String> parameters = Helper.getQueryMap();
        if (parameters != null) {
            topic_id = parameters.get("topic_id");
        }
        ResultSet rs = resultSet("CTN", topic_id);
        if (rs != null) {
            rs.next();
            return rs.getString("TOPIC_NAME");
        } else {
            return "NO QUERY_MAP";
        }

//        if (parameters != null) {
//            topic_id = parameters.get("topic_id");
//            ResultSet rs = resultSet("CTN",topic_id);
//            rs.next();
//            return rs.getString("TOPIC_NAME");
//        }else{
//            return "NO QUERY_MAP";
//        }
    }

    public String getCurrentTopicID() throws SQLException {
        connect();
        Map<String, String> parameters = Helper.getQueryMap();
        if (parameters != null) {
            topic_id = parameters.get("topic_id");
        }
        return topic_id;
//        if (parameters != null) {
//            topic_id = parameters.get("topic_id");
//            return topic_id;
//        }else{
//            return "NO QUERY_MAP";
//        }
    }

//    public ResultSet getTopicsFromSect() {
//        connect();
//        Map<String, String> parameters = Helper.getQueryMap();
//        if (parameters != null) {
//            section_id = parameters.get("section_id");
//        }
//        if (itSearch && getUrl().contains("topics")) {
//            return resultSet("TFSS", section_id, Integer.toString(pageTopics * amountString),
//                    Integer.toString((pageTopics + 1) * amountString),textSearch);
//        } else {
//            return resultSet("TFS", section_id, Integer.toString(pageTopics * amountString),
//                    Integer.toString((pageTopics + 1) * amountString));
//        }
//    }
    public ResultSet getTopicsFromSect() {
        connect();
        Map<String, String> parameters = Helper.getQueryMap();
        if (parameters != null) {
            section_id = parameters.get("section_id");
        }
        if (itSearch && !textSearch.trim().equals("") && getUrl().contains("topics")) {
            return resultSet("TFSS", section_id, textSearch.toLowerCase(), Integer.toString(pageTopics * amountString),
                    Integer.toString((pageTopics + 1) * amountString));
        } else {
            return resultSet("TFS", section_id, Integer.toString(pageTopics * amountString),
                    Integer.toString((pageTopics + 1) * amountString));
        }
    }

    public ResultSet getMessageFromTop() {
        connect();
        Map<String, String> parameters = Helper.getQueryMap();
        if (parameters != null) {
            topic_id = parameters.get("topic_id");
        }
        if (itSearch && !textSearch.trim().equals("") && getUrl().contains("messages")) {
            return resultSet("MFTS", topic_id, textSearch.toLowerCase(), Integer.toString(pageMessages * amountString),
                    Integer.toString((pageMessages + 1) * amountString));
        } else {
            return resultSet("MFT", topic_id, Integer.toString(pageMessages * amountString),
                    Integer.toString((pageMessages + 1) * amountString));
        }

    }

    public void nextPage(String table) {
        int count = 0;
        ResultSet rs = (table.equals(isTopics())) ? resultSet("NPT") : resultSet("NPM");
        try {
            if (rs != null) {
                rs.beforeFirst();
                rs.last();
                count = rs.getRow();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForumBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (table.equals(isTopics())) {
            pageTopics++;
            lastPageTop = (pageTopics + 1) * amountString > count;
        } else if (table.equals(isMessag())) {
            pageMessages++;
            lastPageMess = (pageMessages + 1) * amountString > count;
        }
        redirect();
    }

    public void previousPage(String table) {
        if (table.equals(isTopics())) {
            pageTopics--;
            lastPageTop = false;
        } else {
            pageMessages--;
            lastPageMess = false;
        }
        redirect();
    }

    public void search() {
        itSearch = true;
        redirect();
    }

    private void connect() {
        if (workDB == null) {
            workDB = new WorkDB();
            try {
                workDB.createPreparedStatements();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ForumBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ForumBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private ResultSet resultSet(String id_preparedStatement, String... params) {
        try {
            return workDB.resultOfQuery(id_preparedStatement, params);
        } catch (SQLException ex) {
            Logger.getLogger(ForumBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void redirect() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(ForumBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getUrl() {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
    }

    public ForumBean() {

    }

}
