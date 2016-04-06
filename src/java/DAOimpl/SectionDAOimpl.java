
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

    public void addSection (Section section) throws SQLException {
        Session session;
        session = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(section);
                session.getTransaction().commit();
            } catch (Exception e) {
             //   JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
    }
}
