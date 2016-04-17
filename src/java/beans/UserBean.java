/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import DAO.Factory;
import DAOimpl.UsersDAOimpl;
import java.sql.SQLException;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import logic.User;
import oracle.net.aso.s;

/**
 *
 * @author aser 2014
 */
@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean {
    private String nick;
    private String name;
    private String sname;
    private User UsFromDb;
    private String email;
    private String rights;
// public UserBean() throws SQLException {
   // UsersDAOimpl cn = new UsersDAOimpl();
   // User user;    
   // String s = "bka";
   // static final User user = cn.getUserByName(s);
   // user = UsersDAOimpl.getUserByName(s);
   // }
    public String getRights (){
        return this.rights;
    }
    public void setRights (){
        this.rights = UsFromDb.getRights();
    }
    
    public void setEmail (){
        this.email = UsFromDb.getEmail();
    }
    
    public String getEmail(){
        return this.email;
    }
    public void setNick (String s){
        this.nick = UsFromDb.getName();
    }
    
    public String getNick (){
        return this.nick;
    }
    public void setName (String s){
        this.name = UsFromDb.getName();
    }
    public String getName (){
        return this.name;
    }
   public void setSname (String s){
       this.sname = s;
   }
   public String getSname(){
       return this.sname;
   }
   public void getUserFromDB(String s) throws SQLException{    
   UsFromDb = Factory.getInstance().getUserDAO().getUserByName(s);
   }

}
