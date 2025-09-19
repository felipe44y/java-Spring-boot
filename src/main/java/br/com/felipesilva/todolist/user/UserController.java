package br.com.felipesilva.todolist.user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/users")
public class UserController {





    

    @PostMapping("/create")
    public void createUser(@RequestBody UserModel userModel) {
    
        System.out.println(userModel.name);
    }
    
}
