package com.jcpallavicino.sample.myrecyclerviewsample.utils;

/**
 * Created by juan.pallavicino on 16/1/2018.
 */

public class DataHandler {

    public String name;
    public String id;
    public String companyName = "";
    public String isFavorite;
    public String smallImageURL;
    public String largeImageURL;
    public String emailAddress = "";
    public String birthdate;
    public String work;
    public String home;
    public String mobile;
    public String street;
    public String city;
    public String state;
    public String country;
    public String zipCode;

    public DataHandler(ContactDetail detail){
        this.name = detail.getFirstName() + " " + detail.getLastName();
        this.birthdate = detail.getBirthDate();
        this.id = detail.getUserId();
        this.largeImageURL = detail.getPhoto();
        for (int i=0; i<detail.getAddresses().size(); i++){
            if (detail.getAddresses().get(0).getHome() != null){
                this.street = detail.getAddresses().get(0).getHome();
            }

            if (detail.getAddresses().get(0).getWork() != null){
                this.street = detail.getAddresses().get(0).getWork();
            }

        }
        for (int i=0; i<detail.getPhones().size(); i++) {
            if ((detail.getPhones().get(i).getType().equals("Home"))&&
                    (detail.getPhones().get(i).getNumber() != null)){
                this.home = detail.getPhones().get(i).getNumber();
            }

            if ((detail.getPhones().get(i).getType().equals("Cellphone"))&&
                    (detail.getPhones().get(i).getNumber() != null)){
                this.mobile = detail.getPhones().get(i).getNumber();
            }

            if ((detail.getPhones().get(i).getType().equals("Office"))&&
                    ((detail.getPhones().get(i).getNumber() != null))){
                this.work = detail.getPhones().get(i).getNumber();
            }
        }
    }
}
