package idusw.javaweb.openapia;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="datagokr", value="/data-api")
public class Datagokr extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //System.out.println("data-api");
        PrintWriter out = response.getWriter();
        // 매개변수를 숫자로 파싱해서 사용 : 15(일오) -> 15(십오)
        int count = Integer.parseInt(request.getParameter("cnt"));
        for(int i = 0; i < count; i++)
            out.println("data-api");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //System.out.println("data-api");
        PrintWriter out = response.getWriter();
        out.println("email : " + request.getParameter("email"));
        out.println("passwd : " + request.getParameter("passwd"));
    }

}
