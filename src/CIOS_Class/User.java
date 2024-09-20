package CIOS_Class;

import java.io.BufferedReader;
import java.io.IOException;

public class User extends Authenticator{
    
    Database databaseUser = new Database("User.txt");
    
    private String email, password, userType;

    public User(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public boolean Userlogin() {
        try (BufferedReader reader = databaseUser.readFile()) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                String storedEmail = userInfo[0];
                String storedPassword = userInfo[1];
                String storedUserType = userInfo[2];

                if (getEmail().equals(storedEmail) && getPassword().equals(storedPassword)) {
                    setUserType(storedUserType);
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error validating user: " + e.getMessage());
        }
        return false;
    }
    
}
