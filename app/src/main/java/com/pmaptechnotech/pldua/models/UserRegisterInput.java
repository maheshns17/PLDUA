package com.pmaptechnotech.pldua.models;

/**
 * Created by intel on 10-02-18.
 */

public class UserRegisterInput {

    public String user_name;
    public String user_mobile_number;
    public String user_password;

// ALT INSERT FOR Constructor//


    public UserRegisterInput(String user_name, String mobile_number, String user_password) {
        this.user_name = user_name;
        this.user_mobile_number = mobile_number;
        this.user_password = user_password;
    }
}


