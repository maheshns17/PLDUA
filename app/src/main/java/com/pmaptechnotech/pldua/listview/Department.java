package com.pmaptechnotech.pldua.listview;

/**
 * Created by Lincoln on 15/01/16.
 */
public class Department {
    public String dep_id;
    public String dep_type ;
    public String dep_name ;
    public String dep_lat;
    public String dep_lng;
    public String dep_phone_number;


    public Department() {
    }

    public Department(String dep_id, String dep_type, String dep_name, String dep_lat,String dep_lng, String dep_phone_number) {
        this.dep_id = dep_id;
        this.dep_type = dep_type;
        this.dep_name = dep_name;
        this.dep_lat = dep_lat;
        this.dep_lat = dep_lng;
        this.dep_lat = dep_phone_number;

    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getDep_type() {
        return dep_type;
    }

    public void setDep_type(String dep_type) {
        this.dep_type = dep_type;
    }



    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }


    public String getDep_lat() {
        return dep_lat;
    }

    public void setDep_lat(String dep_lat) {
        this.dep_lat = dep_lat;
    }


    public String getDep_lng() {
        return dep_lng;
    }

    public void setDep_lng(String dep_lng) {
        this.dep_lng = dep_lng;
    }


    public String getDep_phone_number() {
        return dep_phone_number;
    }

    public void setDep_phone_number(String dep_phone_number) {
        this.dep_phone_number = dep_phone_number;
    }


}
