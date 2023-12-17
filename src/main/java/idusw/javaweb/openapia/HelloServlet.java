package idusw.javaweb.openapia;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "안녕 서블릿!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        if(request.getParameter("msg") != null)
            out.println("<h1>" + message + request.getParameter("msg") + "</h1>");
        else
            out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        if(request.getParameter("msg") != null)
            out.println("<h1>" + "post method" + request.getParameter("msg") + "</h1>");
        else
            out.println("<h1>" + "post method" + "</h1>");
        out.println("</body></html>");

    }

    public void destroy() {
    }
}