package br.com.felipesilva.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                var authorization = request.getHeader("Authorization");
                
                if (authorization != null) {
                    var password_user = authorization.substring("Basic".length()).trim();
                    byte[] authDecode = Base64.getDecoder().decode(password_user);
                    System.out.println(authDecode);

                    var nova = new String(authDecode);
                    System.out.println(nova);
                    
                    String[] credentials = nova.split(":"); 

                    String username = credentials[0];
                    String password = credentials[1];
                    
                    System.out.println("Username: " + username);
                    System.out.println("Password: " + password);

                } else {
                    System.out.println("No Authorization header found");
                }

                filterChain.doFilter(request, response);
    }
}
 