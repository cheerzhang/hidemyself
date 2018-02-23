package giphy.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;


@WebServlet(urlPatterns = "/user/*")
public class UserServlet extends HttpServlet{
    
    @Resource(lookup = "jdbc/giphy")
    private DataSource ds;  
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {        
        JsonArrayBuilder userBuilder = Json.createArrayBuilder();
        Integer userID = Integer.parseInt(req.getPathInfo().substring(1));
        String sql = "SELECT imageid, title, url FROM userdetails WHERE userid = ?";
        
        try (Connection conn = ds.getConnection())
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            
            ResultSet rs  = stmt.executeQuery();
            if (!rs.next()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            else
            {
                if (rs.first()!= false)
                {
                    JsonObjectBuilder element = Json.createObjectBuilder();
                    element.add("imgid", rs.getString("imageid"))
                           .add("imgtitle", rs.getString("title"))
                           .add("imgurl", rs.getString("url"));
                    userBuilder.add(element.build());                 
                }
                while (rs.next()) {                   
                    JsonObjectBuilder element = Json.createObjectBuilder();
                    element.add("imgid", rs.getString("imageid"))
                           .add("imgtitle", rs.getString("title"))
                           .add("imgurl", rs.getString("url"));
                    userBuilder.add(element.build());                    
                }
            }
            
            rs.close();
            conn.close();
        }
        catch (SQLException ex)
        {
            log(ex.getMessage());
            ex.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        
        //Status Code
        resp.setStatus(HttpServletResponse.SC_OK);
        
        //Media Type
        resp.setContentType(MediaType.APPLICATION_JSON);
        
        //Build array
        JsonArray imageList = userBuilder.build();
        
        try (PrintWriter pw = resp.getWriter())
        {
            //Write Array
            pw.println(imageList.toString()); 
        }
    }
}
