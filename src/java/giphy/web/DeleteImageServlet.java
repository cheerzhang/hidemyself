package giphy.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(urlPatterns = "/image/delete/*")
public class DeleteImageServlet extends HttpServlet{
    @Resource(lookup = "jdbc/giphy")
    private DataSource ds;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageid = req.getPathInfo().substring(1);
        try {
            deleteRecord(imageid);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteRecord (String query) throws SQLException
    {
        String imageid = query.split("&")[0].toString();
        String userid = query.split("&")[1].toString();
        String sql = "DELETE FROM userdetails WHERE imageid = ? && userid = ?";
              
        try (Connection conn = ds.getConnection())
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, imageid);
            stmt.setInt(2, Integer.parseInt(userid));
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            System.out.println("Record is deleted from userdetails table!");
        }
        catch (SQLException ex)
        {            
            ex.printStackTrace();            
            return;
        }       
    }
    
}
