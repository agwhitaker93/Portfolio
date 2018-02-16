/*
 * Authors: George Othen & Andrew Whitaker
 * Title: RandomFactServlet
 * Date: 15/04/2016
 */
package servlet;

import bigjava.TimeZoneBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author FleaNovus
 */
@WebServlet(name = "TimeZoneServlet", urlPatterns =
{
    "/TimeZoneServlet"
})
public class TimeZoneServlet extends HttpServlet
{
    //Create timezonebean object and intialise
    TimeZoneBean timeZoneBean = new TimeZoneBean();
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
	    out.println("<title>Servlet TimeZoneServlet</title>");	    
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Servlet TimeZoneServlet at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        //location equal to location name
        String location = request.getParameter("zone");
        
        //if back button hasn't been pressed
        if(request.getParameter("Back") == null){
            //set location in timezonebean
            timeZoneBean.setCity(location);
            //if timezone location is valid
            if(timeZoneBean.checkCity().equals("error")){
                //return html page with error message and back button
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet TimeZoneServlet</title>");	    
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Sorry, no information is available for " + location + "</h1>");
                out.println("<form action=\"TimeZoneServlet\" method=\"get\">");
                out.println("<input class=\"button\" type=\"submit\" value=\"Back\" name=\"Back\"/>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
            }
            else{
                //return html page with time message and back button
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet TimeZoneServlet</title>");	    
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>The current time in " + location + " is " + timeZoneBean.getTime() + "</h1>");
                out.println("<form action=\"TimeZoneServlet\" method=\"get\">");
                out.println("<input class=\"button\" type=\"submit\" value=\"Back\" name=\"Back\"/>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
            }
        }
        else{
            //return to xhtml file
            response.sendRedirect("TimeZone.xhtml");
        }
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

}
