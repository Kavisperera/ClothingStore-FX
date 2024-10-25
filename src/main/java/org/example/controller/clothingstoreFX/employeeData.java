package org.example.controller.clothingstoreFX;

import java.sql.Date;

public class employeeData {

    private String employeeeId;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private Date date;

    public employeeData(String employeeeId,String password,String firstName,String lastName,String gender,Date date){
        this.employeeeId = employeeeId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.date = date;
    }

    public String getEmployeeId() {
        return employeeeId;
    }

    public String getPassword(){
        return password;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getGender(){
        return gender;
    }
    public Date getDate(){
        return date;
    }

}
