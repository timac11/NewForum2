/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import DAOimpl.UsersDAOimpl;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import logic.Section;
import logic.User;
import static logic.hash_password.md5Apache;

/**
 *
 * @author User
 */
@ManagedBean(name = "forumBean")
@SessionScoped
public class ForumBean implements Serializable {

    private final String forCheckLastPage = "SELECT COUNT(*) FROM TOPICS";
    private String name;
    private String section_id;
    private String topic_id;

    //Колличество выводимых на страницу сообщений - топиков.
    private final int amountString = 10;
    //Для прогрузки по amountOnPage строк из базы данных.При увеличении выгружает следующие 10
    private int pageTopics = 0;
    private int pageMessages = 0;
    private boolean lastPageTop=false;
    private boolean lastPageMess=false;
    private UIComponent mybutton;
    public boolean isLastPageTop() {
        return lastPageTop;
    }

    public boolean isLastPageMess() {
        return lastPageMess;
    }

    public String isTopics(){
        return "TOPICS";
    }
    public String isMessag(){
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }

    public UIComponent getMybutton() {
        return mybutton;
    }
    public void addNewSection(String s) throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        if (!((Factory_sect.getInstance().getSectionDAO().getSectionByName(this.name)).getName().equals(""))){
        FacesMessage msg = new FacesMessage("Sorry, this section there is. Try again");
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(mybutton.getClientId(context), msg);      
        }
        else{
        Section sect = new Section();
        sect.setName(this.getName());
        sect.setUser_id((Factory.getInstance().getUserDAO().getUserByName(s)).getId());
        //sect.setUser_id(1L);
        Date d = new Date(System.currentTimeMillis());
        sect.setDate(d);
        if (!Factory_sect.getInstance().getSectionDAO().addSection(sect)){
        FacesMessage msg = new FacesMessage("Sorry, system error. Try again");
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(mybutton.getClientId(context), msg);
        }
        };
     
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
        String sqlQuery = "SELECT * "
                + "FROM ( SELECT sort.*,rownum rn "
                + "FROM (SELECT * "
                + "FROM TOPICS WHERE SECTION_ID = ? ORDER BY DATE_T) sort) "
                + "WHERE rn BETWEEN ? AND ?";
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

    public void nextPage(String table){
        ResultSet rs= Helper.workWithDB(forCheckLastPage);
        int count = 0;
        try {
            count = rs.getInt(0);
        } catch (SQLException ex) {
            Logger.getLogger(ForumBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(table.equals(isTopics())){
            pageTopics++;
            lastPageTop = (pageTopics+1)*amountString > count;
        }else if(table.equals(isMessag())){
            pageMessages++;
            lastPageMess = (pageMessages+1)*amountString > count;
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try { 
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(ForumBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void previousPage(String table) {
        if (table.equals(isTopics())) pageTopics--;
        else pageMessages--;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try { 
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(ForumBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ForumBean() {

    }

}
