package idusw.javaweb.openapia.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="jdbcTestController", urlPatterns = {"/jdbc/test"})
public class JDBCTestController extends HttpServlet {
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // http://localhost:8080/<application-context>/jdbc/test?input=<입력한 내용>
        req.setAttribute("input", req.getParameter("input"));
        req.getRequestDispatcher("../examples/blank.jsp").forward(req, resp);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp); // 상위 클래스(HttpServlet)의 doGet 메소드를 호출
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp); // 상위 클래스의 doPost 메소드를 호출
        process(req, resp);
    }
}
