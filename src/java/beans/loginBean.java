package beans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import DAO.Factory;
import DAOimpl.UsersDAOimpl;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import logic.User;
import static logic.hash_password.md5Apache;
import helpers.*;

/**
 *
 * @author User
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String name;
    private String password;
    private boolean isLogged = false;
    private UIComponent mybutton;
    private long id;
    public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }
    public long getId(){
        return this.id;
    }
    
    
    public UIComponent getMybutton() {
        return mybutton;
    }

    protected void setIsLogged(boolean isLogg) {
        isLogged = isLogg;
    }

    public boolean getIsLogged() {
        return isLogged;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String logout() {
        isLogged = false;
        name = null;
        password = null;
        id = 0;
        return PageNavigation.redirectToIndex;
    }

    public String login() {
        // проверка совпадений имён в базе данных, иначе - вывод ошибке
        //  о неправильном пароле логине 
        Locale.setDefault(Locale.ENGLISH);
        String s1 = null;
        String s2 = null;
        String s3 = null;
        User user;
        try {
        user = Factory.getInstance().getUserDAO().getUserByName(this.name);
        String name;
        name = user.getName();
        if (!name.equals("")) {
            s1 = user.getName();
            s2 = user.getPassword();
            s3 = user.getEmail();
        } else if (!name.equals("_")) {
            FacesMessage msg = new FacesMessage("Invalid username. Try again");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), msg);
            return PageNavigation.toLogin;
        }
        else return "Error-page.xhtml";
        if ((md5Apache(this.password, s1, s3)).equals(s2)) {
            isLogged = true;
            id = (Factory.getInstance().getUserDAO().getUserByName(this.name)).getId();
            return PageNavigation.toIndex;
        } else {
            FacesMessage msg = new FacesMessage("Invalid password. Try again");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), msg);
            return PageNavigation.toLogin;
        }
        }
        catch (SQLException e){
            return "pageError.";
        }
        }
    public String profile(){
    return "profile.xhtml";
    }
    public LoginBean() {
    }

}
