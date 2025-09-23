package br.com.felipesilva.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.felipesilva.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                var servletPath = request.getServletPath();
                if(servletPath.equals("/tasks/create")){
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

                        //validar useario
                        var user = this.userRepository.findByUsername(username);
                        if(user == null){
                            response.sendError(401, "User not found");
                            return;
                        }else{
                            //validar senha
                            var passwordVerrify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                            if(passwordVerrify.verified){
                                request .setAttribute("userId", user.getId());
                                filterChain.doFilter(request, response);
                            }else{
                                response.sendError(401, "Invalid password");
                                return;
                            }
                        }
        

                    } else {
                        response.sendError(401, "Authorization header required");
                        return;
                    }

                }else{
                    filterChain.doFilter(request, response);
                }    
    }
}
 