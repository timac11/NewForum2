package logic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table( name = "TOPICS")
public class Topic {
    @Id    
    @Column(name="TOPIC_ID")    
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long  id;
    @Column(name = "TOPIC_NAME")    
    private String name;
    @Column (name = "DATE_T")     
    private Date date;
    @Column (name = "USER_ID")  
    private long user_id; 
    @Column (name = "SECTION_ID")  
    private long section_id; 
    public Topic (long id, String name, Date date, long user_id){
        this.id = id;
        this.name = name;
        this.date = date;
        this.user_id = user_id;
}
    public Topic (String s){
    name = s;
    }
    public Topic() {
    name = "_";
    }
    public long getId(){
        return this.id;
    }
    public void setId(long id){
        this.id = id;
    }
    
    public void setSection_id(long id){
        this.section_id = id;
    }
    
    public long getSection_id(){
        return this.section_id;
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
    
    public String getName (){
        return this.name;
    }
    public void setName (String name){
        this.name = name;
    }
}
