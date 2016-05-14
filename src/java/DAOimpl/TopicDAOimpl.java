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
import DAO.TopicsDAO;
import java.beans.Expression;
import java.util.Date;
import logic.Message;
import logic.Section;
import logic.Topic;
import logic.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class TopicDAOimpl implements TopicsDAO {

    public boolean addTopic (Topic topic) throws SQLException {
        Session session;
        session = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(topic);
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

    @Override
    public Topic getTopicByName(String name) {
     Session session = null;
            List<Topic> studs = new ArrayList<Topic>();
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                studs = session.createCriteria(Topic.class)
                        .add ( Restrictions.eq("name", name))
                        .list();
            } catch (Exception e) {
               Topic u = new Topic("");
               return u;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            if (studs.isEmpty()){
            Topic u = new Topic("");    
            return u;
            }
            return studs.get(0);
      }

   
}
     