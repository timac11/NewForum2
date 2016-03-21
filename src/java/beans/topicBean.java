/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author User
 */
@ManagedBean(name = "topicBean")
@SessionScoped
public class topicBean implements Serializable {
    
    public class Topics{
        String topicName;
        public Topics(String s){
            topicName=s;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }
    }

    
    private Topics[] topics = new Topics[] {
        new Topics("hfhfhfhfhf"),
        new Topics("hddiiudhqoiahdsk"),
        new Topics("wihdnkzxcbuwyagshb")};

    public Topics[] getTopics() {
        return topics;
    }

    
    
    public topicBean() {
        
    }
    
}