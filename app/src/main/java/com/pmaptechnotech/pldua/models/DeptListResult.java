package com.pmaptechnotech.pldua.models;

import com.pmaptechnotech.pldua.listview.Department;

import java.util.List;

/**
 * Created by intel on 02-03-18.
 */


public class DeptListResult {

  public List<Department> Departments;

    public boolean is_success;
    public String msg;
    public List<User> user;

}
