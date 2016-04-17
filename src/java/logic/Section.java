
package logic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
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
@Table( name = "SECTIONS")
public class Section  {
//private static final long serialVersionUID = -1798070786993154676L;
   @Id    
   @Column(name="Section_ID")    
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")  
    private long  id;
    @Column(name = "SECTION_NAME")    
    private String name;
    @Column (name = "USER_ID")  
    private long user_id;
    @Column (name = "DATE_T")
    private Date date;
   
    public Section (){
    this.name = "";
    }   

    public Section(String s) {
    this.name = s;
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
 

    public long getUser_id(){
        return this.user_id;
    }
    
    public void setUser_id(long user_id){
        this.user_id = user_id;
    }   
    
    public void setDate (Date d){
        this.date = d;
    }
    
    public Date getDate(){
        return this.date;
    }

}
  

