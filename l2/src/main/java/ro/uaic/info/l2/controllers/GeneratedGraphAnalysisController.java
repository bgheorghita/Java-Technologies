package ro.uaic.info.l2.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.graph4j.Graph;
import ro.uaic.info.l2.helpers.GraphGenerator;
import ro.uaic.info.l2.models.Output;
import ro.uaic.info.l2.services.CookieService;
import ro.uaic.info.l2.services.CookieServiceImpl;
import ro.uaic.info.l2.services.GraphAnalysisService;
import ro.uaic.info.l2.services.GraphAnalysisServiceImpl;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "generatedGraphAnalysis", value = "/generatedGraphAnalysis")
public class GeneratedGraphAnalysisController extends HttpServlet {

    private final CookieService cookieService = new CookieServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String order = request.getParameter("order");
        String size = request.getParameter("size");

        if(isInvalidInput(order)) {
            order = cookieService.read(request, "order");
        }

        if(isInvalidInput(size)) {
            size = cookieService.read(request, "size");
        }

        ServletContext context = this.getServletContext();
        int orderInt = isInvalidInput(order) ? Integer.parseInt(context.getInitParameter("defaultOrder")) : Integer.parseInt(order);
        int sizeInt = isInvalidInput(size) ? Integer.parseInt(context.getInitParameter("defaultSize")) : Integer.parseInt(size);

        Map<String, String> cookies = Map.ofEntries(Map.entry("order", String.valueOf(orderInt)), Map.entry("size", String.valueOf(sizeInt)));
        cookieService.createOrUpdateCookies(response, cookies);

        Graph<String, String> graph = GraphGenerator.generate(orderInt, sizeInt);

        GraphAnalysisService graphAnalysisService = new GraphAnalysisServiceImpl();
        Output output = graphAnalysisService.analyzeGraph(graph);

        request.setAttribute("analysisResult", output);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/result.jsp");
        dispatcher.forward(request, response);
    }

    private boolean isInvalidInput(String input) {
        return input == null || input.isEmpty() || !input.matches("[0-9]+");
    }
}
