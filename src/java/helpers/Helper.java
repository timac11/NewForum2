package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.CachedRowSet;

public class Helper {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";

    //  Database credentials
    static final String USER = "SYSTEM";
    static final String PASS = "jhfg51";
    
    public static Map<String, String> getQueryMap() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String query = req.getQueryString();
        if(query==null) return null;
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }
    public static ResultSet workWithDB(String sqlQuery,String ... param) {
        Connection conn = null;
        PreparedStatement  pstmt = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 3: Execute a query
            pstmt = conn.prepareStatement(sqlQuery);
            for(int i = 0;i<param.length;i++){
                pstmt.setString(i+1, param[i]);
            }
            ResultSet rs = pstmt.executeQuery();
            CachedRowSet crs= new com.sun.rowset.CachedRowSetImpl();
            crs.populate(rs);
            return crs;
        } catch(SQLException se){
            System.out.println(se);
        }catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static void addToDB(String sqlQuery) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(true);
            int a;
            a = stmt.executeUpdate("INSERT INTO SECTIONS(SECTION_ID, SECTION_NAME ,USER_ID ,DATE_T) VALUES(3,'asdf',2,'23-APR-16')");
            //stmt.executeQuery("COMMIT");
            conn.commit();
//conn.setAutoCommit(true);
            System.out.println(a);
        } catch(SQLException se){
            System.out.println(se);
        }catch (Exception e) {
            System.out.println(e);
        }finally{
           
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
