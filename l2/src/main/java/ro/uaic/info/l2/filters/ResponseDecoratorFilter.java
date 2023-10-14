package ro.uaic.info.l2.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;

// A web filter that will decorate the response by adding a specific prelude (at the beginning)
// and a specific coda (at the end) to the generated HTML page.

@WebFilter(filterName = "ResponseDecorator", urlPatterns = {"/*"})
public class ResponseDecoratorFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SimpleResponseWrapper wrapper = new SimpleResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, wrapper);
        String content = wrapper.toString();
        String result = "<p><b>Date:</b> " + LocalDate.now() + "</p>" + content + "<p><b>Java Technologies</p></b>";
        PrintWriter out = servletResponse.getWriter();
        out.write(result);
    }
}

class SimpleResponseWrapper extends HttpServletResponseWrapper {
    private final StringWriter output;
    public SimpleResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new StringWriter();
    }
    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(output);
    }
    @Override
    public String toString() {
        return output.toString();
    }
}