package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilities.Utilities;

public class ActionServletUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       doPost(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	 
    	 //Getting root for data file path
    	 String root = getServletContext().getRealPath("");
    	 
    	 //Preparing response
    	 PrintWriter out = response.getWriter();
    	 response.setContentType("text/plain");

    	 response.setCharacterEncoding("UTF-8");
         
       
    	 String newContent = Utilities.readingUploadFile(request,out);
    	 //Bringing original file
    	 String stringContent = Utilities.bringingOriginalFile(root,out);
         
         //Adding new data into the file
         Utilities.writingFile(root, stringContent, newContent,out);
         
         //Processing data
         Utilities.processingFile(root, out);                                             
         
         
   }       
   

}