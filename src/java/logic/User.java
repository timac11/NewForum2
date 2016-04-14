/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table( name = "USERS")
public class User  {
//private static final long serialVersionUID = -1798070786993154676L;
   @Id    
   @Column(name="USER_ID")    
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")  
    private long  id;
    @Column(name = "USER_NAME")    
    private String name;
    @Column (name = "PASSWORD_HASH")     
    private String password;
    @Column (name = "RIGHTS")  
    private String rights;
    @Column (name = "EMAIL")
    private String email;
    @Column (name = "DATE_N")
    private Date date;
    public User (long id,String name,String password, String rights ){
    
        this.id = id;
        this.name = name;
        this.password = password;
        this.rights = rights;
    }   
    public User (){
    name = "";
    };
    public User (String s){
    name = s;
    }
    public String getEmail(){
        return this.email;
    }
   
    public void setEmail(String email){
        this.email = email;
    }
   
   public long getId(){
       return this.id;
    }
   
   public void setId(long id){
       this.id = id;
    }
    
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
 
    public String getPassword(){
        return this.password;
    }
    public void setPassword( String password){
        this.password = password;
    }
   
    public String getRights(){
        return this.rights;
    }
    
    public void setRights(String rights){
        this.rights = rights;
    }   
    
    public void setDate (Date d){
        this.date = d;
    }
    
    public Date getDate(){
        return this.date;
    }

}
