package com.jcpallavicino.sample.myrecyclerviewsample.utils;

import java.util.List;

/**
 * Created by juan.pallavicino on 16/3/2018.
 */

public class ContactDetail extends Contact {
    private List<Address> addresses = null;

    public List<Address> getAddresses(){
        return addresses;
    }

    public void setAddresses(Address ad){
        this.addresses.add(ad);
    }
}
