package com.pmaptechnotech.pldua.models;

/**
 * Created by intel on 12-02-18.
 */

public class UserLoginInput {
    public String user_mobile_number;
    public String user_password;

    // ALT INSERT FOR Constructor//


    public UserLoginInput(String user_mobile_number, String user_password) {
        this.user_mobile_number = user_mobile_number;
        this.user_password = user_password;
    }
}

