package DAOimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import util.HibernateUtil;
import DAO.UsersDAO;
import java.beans.Expression;
import logic.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class UsersDAOimpl implements UsersDAO {
    
    public void addUser(User stud) throws SQLException {
            Session session = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(stud);
                session.getTransaction().commit();
            } catch (Exception e) {
      //        JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
      }

      public void updateUser(User stud) throws SQLException {
            Session session = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.update(stud);
                session.getTransaction().commit();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
      }

      public User getUserById(long id) throws SQLException {
            Session session = null;
            List <User> stud = null;
             List <User> users = new ArrayList<User>();
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                 //Criteria crit = session.createCriteria(User.class);
                 //crit.setMaxResults(50);
                // List users =crit.list();
                users = session.createCriteria(User.class)
                        .add ( Restrictions.eq("id", id)).list();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            return users.get(0);
      }

      public List<User> getAllUsers() throws SQLException {
            Session session = null;
            List<User> studs = new ArrayList<User>();
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                studs = session.createCriteria(User.class).list();
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            return studs;
      }

      public void deleteUser(User stud) throws SQLException {
            Session session = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.delete(stud);
                session.getTransaction().commit();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
      }  
      public User getUserByName (String name) throws SQLException{
                  Session session = null;
            List<User> studs = new ArrayList<User>();
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                studs = session.createCriteria(User.class)
                        .add ( Restrictions.eq("name", name))
                        .list();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            if (studs.isEmpty()){
            User u = new User();    
            return u;
            }
            return studs.get(0);
      }
      
      public User getUserByMale (String mail) throws SQLException{
                  Session session = null;
            List<User> studs = new ArrayList<User>();
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                studs = session.createCriteria(User.class)
                        .add ( Restrictions.eq("email", mail))
                        .list();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            if (studs.isEmpty()){
            User u = new User();    
            return u;
            }
            return studs.get(0);
      }
      

}