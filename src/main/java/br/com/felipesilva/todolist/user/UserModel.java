package br.com.felipesilva.todolist.user;

public class UserModel {

    private String username;
    private String name;
    private String password;


    //Aponta para o username
    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
 