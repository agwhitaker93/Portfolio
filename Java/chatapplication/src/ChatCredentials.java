/*
 * Authors:		Andrew Whitaker & George Othen
 * Title:		Chat Credentials
 * Created:		05/02/2016
 * Version:		1.0
 */

public class ChatCredentials {
    String username;
    String password;

    /**
     * Default constructor to initialize the credentials of a new user as ""
     */
    public ChatCredentials() {
        username = "";
        password = "";
    }

    /**
     * @param name the username for the new chat user's credentials
     * @param pass the password for the new chat user's credentials
     */
    public ChatCredentials(String name, String pass) {
        username = name;
        password = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "username: " + username + " password: " + password;
    }
}
