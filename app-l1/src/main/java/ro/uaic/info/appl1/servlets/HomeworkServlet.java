package ro.uaic.info.appl1.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ro.uaic.info.appl1.utils.RandomTreeGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet(name = "homeworkServlet", value = "/homework")
public class HomeworkServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger("");

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        int numVertices = Integer.parseInt(request.getParameter("numVertices"));
        logRequestInfo(request);

        int[][] adjacencyMatrix = RandomTreeGenerator.generateRandomTree(numVertices);

        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Adjacency Matrix</title></head><body>");
        out.println("<h1>Adjacency Matrix for a randomly generated tree</h1>");
        out.println("<table border='1'>");

        for (int i = 0; i < numVertices; i++) {
            out.println("<tr>");
            for (int j = 0; j < numVertices; j++) {
                out.println("<td>" + adjacencyMatrix[i][j] + "</td>");
            }
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    public void destroy() {
    }

    private void logRequestInfo(HttpServletRequest request) {
        String method = request.getMethod();
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String clientLanguages = request.getHeader("Accept-Language");
        String numVertices = request.getParameter("numVertices");

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\nHTTP Method: ").append(method).append("\n");
        logMessage.append("Client IP Address: ").append(ipAddress).append("\n");
        logMessage.append("User-Agent: ").append(userAgent).append("\n");
        logMessage.append("Client Language(s): ").append(clientLanguages).append("\n");
        logMessage.append("Request Parameter (numVertices): ").append(numVertices).append("\n");

        logger.info(logMessage.toString());
    }
}
