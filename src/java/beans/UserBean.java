
import DAO.Factory;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import logic.User;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {
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
        return UsFromDb.getRights();
    }
    public void setRights (){
        this.rights = UsFromDb.getRights();
    }
    
    public void setEmail (){
        this.email = UsFromDb.getEmail();
    }
    
    public String getEmail(){
        return UsFromDb.getEmail();
    }
    public void setNick (String s){
        this.nick = UsFromDb.getName();
    }
    
    public String getNick (){
        return UsFromDb.getRights();
    }
    public void setName (){
        this.name = UsFromDb.getName();
    }
    public String getName (){
        return UsFromDb.getName();
    }
   public void setSname (String s){
       this.sname = s;
   }
   public String getSname(){
       return this.sname;
   }
   public String getUserFromDB(String s) throws SQLException{    
   UsFromDb = Factory.getInstance().getUserDAO().getUserByName(s);
   return "/secured/profile.xhtml";
   }

}
