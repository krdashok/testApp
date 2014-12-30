package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.util.DBManager;


/**
 * Servlet implementation class ImageDownloadServlet
 */
@MultipartConfig      
@WebServlet("/imageUploader")
public class ImageDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageDownloadServlet() {
        super();
        
    }

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    doPost(request, response);
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        
	        String imagefileName = request.getContextPath() + request.getParameter("ufile");
	        ServletContext context = getServletContext().;  
	        PrintWriter pw = response.getWriter();
	        
	        if(imagefileName!=null) {
	            File file = new File(getServletContext().getRealPath(imagefileName));
	            FileInputStream fs = new FileInputStream(file);
	            String sql = "INSERT INTO t_image(id, p_image) values (?, ?)";
	            
	            PreparedStatement ps;
	            try {
	                ps = (PreparedStatement) DBManager.getConnection().prepareStatement(sql);
	                ps.setInt(1,1);
	                ps.setBinaryStream(2,fs,fs.available());
	                
	                int i = ps.executeUpdate();
	                if(i!=0){
	                    pw.println("<h1>Image saved successfully!</h1>");
	                }
	            }
	            catch (SQLException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }
	        else {
	            downloadAnImage(request, response);
	        }
	}
	
	protected void downloadAnImage(HttpServletRequest request, HttpServletResponse response) {

	    String imageId = request.getParameter("id");
	    
        if(imageId!=null) {
            
            String sql = "SELECT from t_image where id=:id";
            OutputStream out;
            PreparedStatement ps;
            try {
                
                out = response.getOutputStream();                            
                ps = (PreparedStatement) DBManager.getConnection().prepareStatement(sql);
                ps.setLong(1, 1);
                ResultSet rs = ps.executeQuery();
                
                InputStream binaryStream = rs.getBinaryStream(2); //blob column index passed to binary stream
                
              /*  response.setHeader("Content-Type", getServletContext().getMimeType(binaryStream.));
                response.setHeader("Content-Length", String.valueOf(image.getLength()));
                response.setHeader("Content-Disposition", "inline; filename=\"" + image.getFilename() + "\"");*/
                byte[]  bts = new byte[1];
                while(binaryStream.read(bts) > 0) {
                    out.write(bts);
                }
                
                out.flush();
                out.close();

                
            } catch (IOException e) {
                
                e.printStackTrace();
                
            }
            catch (SQLException e) {
                
                e.printStackTrace();
                
            }
        }

	    
	}

}
