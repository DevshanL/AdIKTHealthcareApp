package com.tech.adikthealthcare;

public class pcrModel {
    String name,nic,address,email;
    String mobile;
    String noPcr;
    String pcrPrice;

    public pcrModel(){

    }



    public pcrModel(String name, String nic, String noPcr , String address, String mobile, String email,String pcrPrice) {
        this.name = name;
        this.nic = nic;
        this.noPcr=noPcr;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.pcrPrice=pcrPrice;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public String getPcrPrice() {
        return pcrPrice;
    }

    public void setPcrPrice(String pcrPrice) {
        this.pcrPrice = pcrPrice;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getNoPcr() { return noPcr; }

    public void setNoPcr(String noPcr) { this.noPcr = noPcr; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
