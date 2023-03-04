package videohome.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import java.io.IOException;

@Order(1)
@WebFilter(urlPatterns = {"/*"}, filterName = "routerFilter")
public class RouterFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String servletPath = request.getServletPath();
        String context = request.getContextPath();
        //路由地址一律转发到index.html页面
        if (servletPath.startsWith("/ui")) {
            req.getRequestDispatcher(context + "/index.html").forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }

}