package Models;

import java.io.Serializable;

public class User implements Serializable {
    private int user_id;

    private String username;

    private String email;

    private String role;

    private String password;

    private static final long serialVersionUID = 123456789L;

//    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Ticket> tickets;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int user_id, String username, String email, String password /*List<Ticket> tickets*/) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.role = "user";
        this.password = password;
        //this.tickets = tickets;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String toString() {
        return "id = " + user_id +
                "\nusername = " + username +
                "\nemail = " + email +
                "\nrole = " + role +
                "\npassword = " + password; //+
        //"\ntickets = " + tickets;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role){
        this.role = role;
    }

    public void setUser_id(int id){
        this.user_id = id;
    }
}
