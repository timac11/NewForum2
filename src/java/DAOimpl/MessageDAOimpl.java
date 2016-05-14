package DAOimpl;

import DAO.MessagesDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import util.HibernateUtil;
import DAO.MessagesDAO;
import java.beans.Expression;
import java.util.Date;
import logic.Message;
import logic.Topic;
import logic.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
public class MessageDAOimpl implements MessagesDAO {

    public boolean addMessage(Message message) throws SQLException {
        Session session;
        session = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(message);
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
                return false;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
    }

   
    public Message getMessageById(long id) throws SQLException {
       // throw new UnsupportedOperationException("Not supported yet."); 
     Session session = null;
             List <Message> messages = new ArrayList<Message>();
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                messages = session.createCriteria(Message.class)
                        .add ( Restrictions.eq("id", id)).list();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            return messages.get(0);
    }

  
    public List getMessageByUserId(long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    
    public void deleteMessage(Message message) throws SQLException {
    Session session = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.delete(message);
                session.getTransaction().commit();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
    }

   
    public List getMessageByWords(String name) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    
    public List getMessagesByDate(Date date) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

   
    public void ChangeMessage(long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

   
    public List SearchByUser(String user) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
