package ro.uaic.info.appl1.servlets;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "compulsoryServlet", value = "/compulsory")
public class CompulsoryServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String number = request.getParameter("number");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1> Digits of '" + number + "' are: </h1>");
        out.println("<ol>");
        number.chars().forEach(digit -> out.println("<li>" + (char)digit + "</li>"));
        out.println("</ol>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}