package com.service;

import com.model.PersonInformation;

import java.util.List;

public class AddressBookService {
    private final AddressBookDBService addressBookDBService;
    private List<PersonInformation> contactList;

    public enum IOService {DB_IO}

    public AddressBookService() {
        addressBookDBService = AddressBookDBService.getInstance();
    }

    public List<PersonInformation> readPersonInfoData(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            this.contactList = addressBookDBService.readData();
        return this.contactList;
    }
}
