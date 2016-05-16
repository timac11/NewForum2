package beans;


import DAO.Factory;
import java.io.Serializable;
import java.sql.SQLException;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import logic.User;
import logic.hash_password;
import static logic.hash_password.md5Apache;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {
@PreDestroy
public void shutdown(){
equa=false;
check=false;    
}    
    private String nick;
    private String name;
    private String sname;
    private User UsFromDb;
    private String email;
    private String rights;
    private String rpassword;
    private String newpassword1;
    private String newpassword2;
    private boolean equa=false;
    private boolean check=false;
    private UIComponent mybutton;
// public UserBean() throws SQLException {
   // UsersDAOimpl cn = new UsersDAOimpl();
   // User user;    
   // String s = "bka";
   // static final User user = cn.getUserByName(s);
   // user = UsersDAOimpl.getUserByName(s);
   // }

    
    public void setCheck(){
        this.check = true;
    }
    public boolean getCheck(){
        return this.check;
    }
    
    public void inspectCheck(){
        check = true;
    }
    
    public void setEqua(boolean e){
        this.equa = e;
    }
    
    public boolean getEqua(){
        return equa;
    }

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
   
    public void setUserFromDB(String s) throws SQLException{    
   UsFromDb = Factory.getInstance().getUserDAO().getUserByName(s);
  // return "/secured/profile.xhtml";
   ;
    }

   
   public String getRpassword(){
       return this.rpassword;
   }

   public void setRpassword (String word){
       this.rpassword = word;
   }
   
   public void inspection(){
        if(UsFromDb.getPassword().equals(md5Apache(this.rpassword,UsFromDb.getName(),UsFromDb.getEmail()))){
            this.equa = true ;
            this.check = false;
        }
       else {
        this.equa = false ; 
        FacesMessage msg = new FacesMessage("Invalid old password. Try again.");
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(mybutton.getClientId(context), msg);
        }
        
   }
  
   public void inspection2() throws SQLException{
        if(this.newpassword1.equals(this.newpassword2)){
            equa = false ;
            this.UsFromDb.setPassword(md5Apache(this.newpassword1,UsFromDb.getName(),UsFromDb.getEmail()));
            Factory.getInstance().getUserDAO().updateUser(this.UsFromDb);
        }
        else {
        equa = false ; 
        FacesMessage msg = new FacesMessage("Password are not similar. Try again.");
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(mybutton.getClientId(context), msg);
        }
        this.equa = false ;
        this.check = false;
        this.newpassword2 = "";
        this.newpassword1 = "";
        this.rpassword = "";
   }
   
   public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }

    public UIComponent getMybutton() {
        return mybutton;
    }

    public String getNewpassword1(){
       return this.newpassword1;
   }

    public void setNewpassword1 (String word){
       this.newpassword1 = word;
   }
   
       public String getNewpassword2(){
       return this.newpassword2;
   }

   public void setNewpassword2 (String word){
       this.newpassword2 = word;
   }
   
   public boolean isAdmin(String name) throws SQLException{
       UsFromDb = Factory.getInstance().getUserDAO().getUserByName(name);
       return UsFromDb.getRights().contentEquals("Admin");      
   }



}
