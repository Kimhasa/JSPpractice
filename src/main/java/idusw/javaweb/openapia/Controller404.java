package idusw.javaweb.openapia;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(name="controller404", urlPatterns = {"/sb-admin/404"})
public class Controller404 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // response.sendRedirect("404.html");
        request.getRequestDispatcher("500.html").forward(request, response);
        /*
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "    <head>\n" +
                "        <meta charset=\"utf-8\" />\n" +
                "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />\n" +
                "        <meta name=\"description\" content=\"\" />\n" +
                "        <meta name=\"author\" content=\"\" />\n" +
                "        <title>404 Error - SB Admin</title>\n" +
                "        <link href=\"css/styles.css\" rel=\"stylesheet\" />\n" +
                "        <script src=\"https://use.fontawesome.com/releases/v6.3.0/js/all.js\" crossorigin=\"anonymous\"></script>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div id=\"layoutError\">\n" +
                "            <div id=\"layoutError_content\">\n" +
                "                <main>\n" +
                "                    <div class=\"container\">\n" +
                "                        <div class=\"row justify-content-center\">\n" +
                "                            <div class=\"col-lg-6\">\n" +
                "                                <div class=\"text-center mt-4\">\n" +
                "                                    <img class=\"mb-4 img-error\" src=\"assets/img/error-404-monochrome.svg\" />\n" +
                "                                    <p class=\"lead\">This requested URL was not found on this server.</p>\n" +
                "                                    <a href=\"index.jsp\">\n" +
                "                                        <i class=\"fas fa-arrow-left me-1\"></i>\n" +
                "                                        Return to Dashboard\n" +
                "                                    </a>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </main>\n" +
                "            </div>\n" +
                "            <div id=\"layoutError_footer\">\n" +
                "                <footer class=\"py-4 bg-light mt-auto\">\n" +
                "                    <div class=\"container-fluid px-4\">\n" +
                "                        <div class=\"d-flex align-items-center justify-content-between small\">\n" +
                "                            <div class=\"text-muted\">Copyright &copy; Your Website 2023</div>\n" +
                "                            <div>\n" +
                "                                <a href=\"#\">Privacy Policy</a>\n" +
                "                                &middot;\n" +
                "                                <a href=\"#\">Terms &amp; Conditions</a>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </footer>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js\" crossorigin=\"anonymous\"></script>\n" +
                "        <script src=\"js/scripts.js\"></script>\n" +
                "    </body>\n" +
                "</html>\n");
        */
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
