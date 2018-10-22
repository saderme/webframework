package com.application.dataprovider;

import org.testng.annotations.Test;

public class BBOSTestAccount {
    private String login;
    private String email;
    private String password;
    private String runmode;
    
    public BBOSTestAccount() {
    }
    
    public BBOSTestAccount(String login, String email,String password, String runmode ) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.runmode = runmode;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public String getRunMode() {
        return runmode;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setEmail(String email) {
        this.email = email;
    } 
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setRunMode(String runmode) {
        this.runmode = runmode;
    }
    

/*    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BBOSTestAccount [login=").append(login).append(", email=").append(email).append(", password=").append(password).append(", runmode=").append(runmode).append("]");
        return builder.toString();
    }*/
}
