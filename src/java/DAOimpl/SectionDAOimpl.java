
package DAOimpl;

import DAO.MessagesDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import util.HibernateUtil;
import DAO.MessagesDAO;
import DAO.SectionsDAO;
import java.beans.Expression;
import java.util.Date;
import logic.Message;
import logic.Section;
import logic.Topic;
import logic.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class SectionDAOimpl implements SectionsDAO {

    public boolean addSection (Section section) throws SQLException {
        Session session;
        session = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(section);
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                return false;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
    }
     public  Section getSectionByName (String name) throws SQLException{
            Session session = null;
            List<Section> studs = new ArrayList<Section>();
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                studs = session.createCriteria(Section.class)
                        .add ( Restrictions.eq("name", name))
                        .list();
            } catch (Exception e) {
               Section u = new Section("");
               return u;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            if (studs.isEmpty()){
            Section u = new Section();    
            return u;
            }
            return studs.get(0);
      }
}
