/*
 * Authors: George Othen & Andrew Whitaker
 * Title: RandomFactServlet
 * Date: 15/04/2016
 */
package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author FleaNovus
 */
@WebServlet(name = "RandomFactServlet", urlPatterns =
{
    "/RandomFactServlet"
})
public class RandomFactServlet extends HttpServlet
{
    Random rand = new Random();
    int hitCount = 0;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
    {
	response.setContentType("text/html;charset=UTF-8");
	try (PrintWriter out = response.getWriter())
	{
	    /* TODO output your page here. You may use following sample code. */
	    out.println("<!DOCTYPE html>");
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<title>Servlet RandomFactServlet</title>");
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Servlet RandomFactServlet at " + request.getContextPath() + "</h1>");
	    out.println("</body>");
	    out.println("</html>");
	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
    {
        //counts number of uses
        hitCount++;
        
        PrintWriter out = response.getWriter();
        
        //return webpage with random fact
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet RandomFactServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Fact of the Day</h1>");
        out.println("<p style=\"word-wrap: normal;\" >"+ readFile() +"</p>");
        out.println("<p>Site hits: " + hitCount + "</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
    {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
	return "Short description";
    }// </editor-fold>

    /**
     * Returns a String containing a random fact from a .txt file
     * @return A string containing a random fact
     */
    public String readFile()
    {	//http://www.avajava.com/tutorials/lessons/how-do-i-read-a-string-from-a-file-line-by-line.html
	int chosen = rand.nextInt(9)+1;
	System.out.println(chosen);
	int count = 1;
	StringBuffer stringBuffer = new StringBuffer();
	try
	{
	    File file = new File("E:\\NetBeansProjects\\Hello World\\src\\java\\servlet\\facts.txt");
	    FileReader fileReader = new FileReader(file);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);	    
	    String line;
	    while ((line = bufferedReader.readLine()) != null)
	    {
		if(chosen == count){		    
		    stringBuffer.append(line);		
		}
		count++;
		System.out.println(count);
	    }		
	    fileReader.close();
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
	return stringBuffer.toString();
    }
}

