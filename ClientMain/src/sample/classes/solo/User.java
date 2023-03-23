package sample.classes.solo;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private int role;
    private String name;

    public User(){};

    public User(String login, String password, int role, String name){
        this.login = login;
        this.password = password;
        this.role = role;
        this.name = name;
    };

    public User(int id, String login, int role, String name) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.name = name;
    }

    public User(int id, String login, String password, int role){
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    };

    public User(int id, String login, String password, int role, String name) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }
    public int getRole() {
        return role;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  "id: " + id +
                ", login=" + login +
                ", password=" + password +
                ", role: " + role +
                ", name=" + name;
    }
}