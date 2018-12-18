package by.bsuir.entity;

import java.io.Serializable;

public class Interview implements Serializable {
    private  static  final  long serialVersionUID = 3298857272404827489L;
    private  String time;
    private  String date;
    private  String userName;
    private  String adminName;
    private  String adminEmail;


    public Interview(String time, String date, String userName, String adminName) {
        this.time = time;
        this.date = date;
        this.userName = userName;
        this.adminName = adminName;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public String getAdminName() {
        return adminName;
    }
}
