package beans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DAO.Factory;
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

/**
 *
 * @author User
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class loginBean implements Serializable {

    private String name;
    private String password;
    private boolean isLogged = false;
    private UIComponent mybutton;
    
    public void setMybutton(UIComponent mybutton) {
    this.mybutton = mybutton;
    }

    public UIComponent getMybutton() {
    return mybutton;
    }
     public void setIsLogged(boolean isLogg) {
     isLogged=isLogg;
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
    
    public void logout(){
        isLogged=false;
        name=null;
        password=null;
    }
    
    public String login() throws SQLException{
       // проверка совпадений имён в базе данных, иначе - вывод ошибке
       //  о неправильном пароле логине 
       Locale.setDefault(Locale.ENGLISH);
       String  s1 = null;
       String  s2 = null;
       String  s3=null;
       User user;
       user = Factory.getInstance().getUserDAO().getUserByName(this.name);
       if (!user.getName().equals("")){
           s1 = user.getName();
           s2 = user.getPassword();
           s3 = user.getEmail();
       }
       else{ 
           FacesMessage msg = new FacesMessage("Invalid username. Try again");
           FacesContext context = FacesContext.getCurrentInstance();
           context.addMessage(mybutton.getClientId(context), msg);
         return "login.xhtml";
       }
       if((md5Apache(this.password,s1,s3)).equals(s2)){
           isLogged=true;
          return "secured/forum.xhtml";
       }
       else{
           FacesMessage msg = new FacesMessage("Invalid password. Try again");
           FacesContext context = FacesContext.getCurrentInstance();
           context.addMessage(mybutton.getClientId(context), msg);
         return "login.xhtml";
       }
    }
    
    public  loginBean() {
    }

    
}