package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import logic.QueryStore;

public class WorkDB {

    private String selectSection_str = "SELECT * FROM SECTIONS";
    private String selectTopicFromSect_str = "SELECT * "
            + "FROM ( SELECT sort.*,rownum rn "
            + "FROM (SELECT * "
            + "FROM TOPICS WHERE SECTION_ID = ? ORDER BY DATE_T) sort) "
            + "WHERE rn BETWEEN ? AND ?";
    private String selectMessFromTop_str = "SELECT * "
            + "FROM (SELECT sort.*,rownum rn "
            + "FROM (SELECT u.USER_NAME as USER_NAME, m.MESSAGE as MESSAGE,m.DATE_T as DATE_T "
            + "FROM USERS u, MESSAGES m "
            + "WHERE m.TOPIC_ID = ? AND m.USER_ID = u.USER_ID ORDER BY m.DATE_T) sort) "
            + "WHERE rn BETWEEN ? AND ?";
    private String selectTopicFromSectSearch_str = "SELECT * "
            + "FROM ( SELECT sort.*,rownum rn "
            + "FROM (SELECT * "
            + "FROM TOPICS WHERE SECTION_ID = ?"
            + " AND REGEXP_LIKE (LOWER(TOPIC_NAME),?) ORDER BY DATE_T) sort) "
            + "WHERE rn BETWEEN ? AND ?";
    private String selectMessFromTopSearch_str = "SELECT * "
            + "FROM (SELECT sort.*,rownum rn "
            + "FROM (SELECT u.USER_NAME as USER_NAME, m.MESSAGE as MESSAGE,m.DATE_T as DATE_T "
            + "FROM USERS u, MESSAGES m "
            + "WHERE m.TOPIC_ID = ? AND m.USER_ID = u.USER_ID"
            + " AND REGEXP_LIKE (LOWER(MESSAGE),?) ORDER BY m.DATE_T) sort) "
            + "WHERE rn BETWEEN ? AND ?";			
    private String forNextPageTop_str = "SELECT * FROM TOPICS";
    private String forNextPageMess_str = "SELECT * FROM MESSAGES";

    // JDBC driver name and database URL
    private final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
    private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    //  Database credentials
    private final String USER = "SYSTEM";
    private final String PASS = "52415";
    Connection conn;
    PreparedStatement selectSection;
    PreparedStatement selectTopicFromSect;
    PreparedStatement selectMessFromTop;
    PreparedStatement selectTopicFromSectSearch;
    PreparedStatement selectMessFromTopSearch;
    PreparedStatement forNextPageTop;
    PreparedStatement forNextPageMess;
    // новые селекты, текст запроса вынесен в отдельный класс
    PreparedStatement selectSectionsInfo;
    PreparedStatement selectCurrentSectionName;
    PreparedStatement selectCurrentTopicName;
    PreparedStatement selectTopicsInfo;

    public void createPreparedStatements() throws ClassNotFoundException, SQLException {
        //STEP 1: Register JDBC driver
        Class.forName(JDBC_DRIVER);
        //STEP 2: Open a connection
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        //STEP 3: Execute a query
        selectSection = conn.prepareStatement(selectSection_str);
        selectTopicFromSect = conn.prepareStatement(selectTopicFromSect_str);
        selectMessFromTop = conn.prepareStatement(selectMessFromTop_str);
        forNextPageTop = conn.prepareStatement(forNextPageTop_str);
        forNextPageMess = conn.prepareStatement(forNextPageMess_str);
        selectTopicFromSectSearch = conn.prepareStatement(selectTopicFromSectSearch_str);
        selectMessFromTopSearch = conn.prepareStatement(selectMessFromTopSearch_str);
        
        //// текст запроса вынесен в класс QueryStore
        selectSectionsInfo = conn.prepareStatement(QueryStore.getSectionsInfoQuery());
        selectCurrentSectionName = conn.prepareStatement(QueryStore.getCurrentSectionNameQuery());
        selectCurrentTopicName = conn.prepareStatement(QueryStore.getCurrentTopicNameQuery());
        selectTopicsInfo = conn.prepareStatement(QueryStore.getTopicsInfoQuery());
    }
    
    public ResultSet resultOfQuery(String id_preparedStatement,String... params) throws SQLException{
        PreparedStatement pstmt;
        switch (id_preparedStatement) {
            case "S":
                pstmt = selectSection;
                break;
            case "TFS":
                pstmt = selectTopicFromSect;
                break;
            case "MFT":
                pstmt = selectMessFromTop;
                break;
            case "TFSS":
                pstmt = selectTopicFromSectSearch;
                break;
            case "MFTS":
                pstmt = selectMessFromTopSearch;
                break;
            case "NPT":
                pstmt = forNextPageTop;
                break;
            case "NPM":
                pstmt = forNextPageMess;
                break;
            case "SI":
                pstmt = selectSectionsInfo;
                break;
            case "CSN":
                pstmt = selectCurrentSectionName;
                break;
            case "TI":
                pstmt = selectTopicsInfo;
                break;
            case "CTN":
                pstmt = selectCurrentTopicName;
                break;
            default:
                return null;
        }
        for(int i = 0;i<params.length;i++){
                pstmt.setString(i+1, params[i]);
            }
        ResultSet rs = pstmt.executeQuery();
            CachedRowSet crs= new com.sun.rowset.CachedRowSetImpl();
            crs.populate(rs);
            return crs;
    }

    public WorkDB() {
    }
}
