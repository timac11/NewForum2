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
        }

    }

    public ResultSet getAllSections() {
        connect();
        return resultSet("S");
    }
    
    public ResultSet getSectionsInfo() {
        connect();
        return resultSet("SI");
    }

    public ResultSet getTopicsFromSect() {
        connect();
        Map<String, String> parameters = Helper.getQueryMap();
        if (parameters != null) {
            section_id = parameters.get("section_id");
        }
        if (itSearch && getUrl().contains("topics")) {
            return resultSet("TFSS", section_id, Integer.toString(pageTopics * amountString),
                    Integer.toString((pageTopics + 1) * amountString),textSearch);
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
        if (itSearch && getUrl().contains("messages")) {
            return resultSet("MFTS", topic_id, Integer.toString(pageMessages * amountString),
                    Integer.toString((pageMessages + 1) * amountString),textSearch);
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
