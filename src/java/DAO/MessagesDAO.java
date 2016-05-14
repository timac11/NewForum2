package DAO;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import logic.Message;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

/**
 *
 * @author aser 2014
 */
public interface MessagesDAO {
     public boolean addMessage(Message message) throws SQLException;   //добавить 
    //public void updateUser(Users user) throws SQLException;//обновить 
    public Message getMessageById(long id) throws SQLException;    //получить по id
    public List getMessageByUserId (long id) throws SQLException;
    public void deleteMessage(Message message) throws SQLException;//удалить 
    public List getMessageByWords (String name) throws SQLException;// поиск по имени юзера 
    public List getMessagesByDate (Date date) throws SQLException;
    public void ChangeMessage (long id) throws SQLException;
    public List SearchByUser (String user) throws SQLException;
}
