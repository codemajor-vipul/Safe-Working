package com.example.hello.safe_working;

public class NewRegister {
    public NewRegister() {
    }

    public NewRegister(String fullname, String emailid, String mobileno, String adminflag,String number) {
        this.fullname = fullname;
        this.emailid = emailid;
        this.mobileno = mobileno;
        this.adminflag = adminflag;
        this.number=number;
    }
    public String getNumber() {
        return number;
    }

    public  void setNumber(String number) {
        this.number = number;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getAdminflag() {
        return adminflag;
    }

    public void setAdminflag(String adminflag) {
        this.adminflag = adminflag;
    }

    private String fullname,emailid,mobileno,adminflag;
    private String number;
}
