package giphy.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

@WebServlet(urlPatterns = "/image/add/*")
public class AddImageServlet extends HttpServlet {
    
    @Resource(lookup = "jdbc/giphy")
    private DataSource ds;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageid = req.getPathInfo().substring(1);
        try {
            insertRecord(imageid);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertRecord(String query) throws SQLException
    {        
        String sql = "INSERT INTO userdetails" + "(userid, imageid, title, url) VALUES" + "(?,?,?,?)";
        try (Connection conn = ds.getConnection())
        {
            PreparedStatement stmt = conn.prepareStatement(sql); 
            
            String imageid = query.split("&")[0].toString();
            String title = query.split("&")[1].toString();
            String url = query.split("&")[2].toString();
            String userid = query.split("&")[3].toString();
            
            stmt.setInt(1, Integer.parseInt(userid));
            stmt.setString(2, imageid);
            stmt.setString(3, title);
            stmt.setString(4, url);            
            
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            System.out.println("Record is inserted into userdetails table!");
        }
        catch (SQLException ex)
        {            
            ex.printStackTrace();            
            return;
        }        
    }
}