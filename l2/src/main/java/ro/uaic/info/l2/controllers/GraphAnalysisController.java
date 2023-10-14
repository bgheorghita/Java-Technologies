package ro.uaic.info.l2.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ro.uaic.info.l2.models.Input;
import ro.uaic.info.l2.models.Output;
import ro.uaic.info.l2.services.GraphAnalysisService;
import ro.uaic.info.l2.services.GraphAnalysisServiceImpl;

import java.io.IOException;

@WebServlet(name = "analyzeGraph", value = "/analyzeGraph")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 50    // 50 MB
)
public class GraphAnalysisController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("graphFile");
        Input input = new Input(filePart.getInputStream());

        GraphAnalysisService graphAnalysisService = new GraphAnalysisServiceImpl();
        Output output = graphAnalysisService.analyzeDIMACSGraph(input);

        request.setAttribute("analysisResult", output);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/result.jsp");
        dispatcher.forward(request, response);
    }
}
