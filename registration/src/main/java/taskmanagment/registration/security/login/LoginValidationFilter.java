package taskmanagment.registration.security.login;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class LoginValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("/login") && request.getMethod().equalsIgnoreCase("POST")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                request.getSession().setAttribute("FIELDS_ERROR", "Fields can not be empty");

                // SIMPLE SOLUTION WITH REDIRECT
                //response.sendRedirect("/login");

                // SOLUTION WITH CHANGING HTTP-VERB AND TRANSFERRING CONTROL TO OTHER SERVLET ON SERVER-SIDE
                HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request) {
                    @Override
                    public String getMethod() {
                        return "GET";
                    }
                };
                request.getRequestDispatcher("/login").forward(wrappedRequest, response);

                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
