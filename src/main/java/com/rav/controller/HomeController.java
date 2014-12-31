package com.rav.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
    String url = "jdbc:mysql://localhost:3306/testapp";
    String user = "root";
    String password = "";
    
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/uploadservice")
    public String download(@RequestParam(required = true) String action, HttpServletRequest request,
            HttpServletResponse response, Locale locale, Model model) throws ServletException, IOException
    {
        InputStream inputStream = null;

        if (action.equalsIgnoreCase("upload"))
        {

            Part filePart = request.getPart("ufile");
            /*
             * String imagefileName = request.getContextPath() +
             * request.geti.getInputStream();
             */
            PrintWriter pw = response.getWriter();
            if (filePart != null)
            {
                inputStream = filePart.getInputStream();
            }

            if (inputStream != null)
            {
                try
                {
                    String sql = "INSERT INTO t_image(id, p_image) values (?, ?)";
                    PreparedStatement ps;
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, password);

                    ps = (PreparedStatement) conn.prepareStatement(sql);
                    ps.setInt(1, 1);
                    ps.setBlob(2, inputStream);

                    int i = ps.executeUpdate();
                    if (i != 0)
                    {
                        pw.println("<h1>Image saved successfully!</h1>");
                    }
                }
                catch (SQLException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (ClassNotFoundException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        else if(action.equalsIgnoreCase("download"))
        {
            downloadAnImage(request, response);
        }

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        return "home";
    }
	    
	    
    protected void downloadAnImage(HttpServletRequest request, HttpServletResponse response)
    {

        String imageId = request.getParameter("id");

        if (imageId != null)
        {

            String sql = "SELECT from t_image where id=:id";
            OutputStream out;
            PreparedStatement ps;
            Connection conn = null;
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);

                out = response.getOutputStream();
                ps = (PreparedStatement) conn.prepareStatement(sql);
                ps.setLong(1, 1);
                ResultSet rs = ps.executeQuery();

                if (rs.next())
                {

                    InputStream inputStream = rs.getBinaryStream(2); //blob column index passed to binary stream

                    response.setContentType("image/jpg");

                    int bytesRead = -1;
                    byte[] buffer = new byte[1024];
                    while ((bytesRead = inputStream.read(buffer)) != -1)
                    {
                        out.write(buffer, 0, bytesRead);
                    }

                    out.flush();
                    out.close();
                }

            }
            catch (IOException e)
            {

                e.printStackTrace();

            }
            catch (SQLException e)
            {

                e.printStackTrace();

            }
            catch (ClassNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally
            {
                if(conn!=null)
                    try
                    {
                        conn.close();
                    }
                    catch (SQLException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                
            }
            
        }

    }	    
	
}
