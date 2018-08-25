package com.pmaptechnotech.pldua.models;

/**
 * Created by intel on 01-03-18.
 */

public class UploadDepartmentsInput {
    public String dep_type;
    public String dep_name;
    public String dep_lat;
    public String dep_lng;
    public String dep_phone_number;

    public UploadDepartmentsInput(String dep_type, String dep_name, String dep_lat, String dep_lng, String dep_phone_number) {
        this.dep_type = dep_type;
        this.dep_name = dep_name;
        this.dep_lat = dep_lat;
        this.dep_lng = dep_lng;
        this.dep_phone_number = dep_phone_number;
    }
}
