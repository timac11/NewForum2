package logic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "MESSAGES")
public class Message {
   @Id    
   @GeneratedValue(generator="increment")
   @GenericGenerator(name="increment", strategy = "increment")
   @Column(name="MESSAGE_ID")    
   private long  id;
   @Column(name = "TOPIC_ID")    
   private long top_id;
   @Column (name = "MESSAGE")     
   private String message;
   @Column (name = "USER_ID")  
   private long user_id;
   @Column (name = "DATE_T")
   private Date date;
public Message (long id, long top_id, String message, long user_id, Date date){
this.id= id;
this.top_id = top_id;
this.message = message;
this.user_id = user_id;
this.date = date;
}

    public Message() {
    this.message = "";
    }
public long getId(){
        return this.id;
    }
public void setId(long id){
      this.id = id;
    }
    
public long getTop_id(){
        return this.top_id;
    }
public void  setTop_id(long id){
        this.top_id = id;
    }
public void setName(long top_id){
        this.top_id = top_id;
    }
 
public String getMessage (){
        return this.message;
    }
 public void setMessage( String message){
        this.message = message;
    }
   
public long getUser_id(){
        return this.user_id;
    }
 public void setUser_id (long user_id){
        this.user_id = user_id;
    }   
public Date getDate (){
return this.date;
}
public void setDate (Date date){
    this.date = date;
}
}