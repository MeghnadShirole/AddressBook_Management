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

    public List<PersonInformation> readContactData(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            this.contactList = addressBookDBService.readData();
        return this.contactList;
    }

    public void updatePersonInfo(String name, String state) {
        int result = addressBookDBService.updateContact(name, state);
        if(result == 0)
            return;
        PersonInformation contactData = this.getContactData(name);
        if( contactData != null )
            contactData.state = state;
    }

    private PersonInformation getContactData(String name) {
        return this.contactList.stream()
                .filter(personInfo -> personInfo.first_name.equals(name))
                .findFirst()
                .orElse(null);
    }

    public boolean checkContactInSyncWithDB(String name) {
        List<PersonInformation> personInfoDataList = addressBookDBService.getContactData(name);
        return personInfoDataList.get(0).equals(getContactData(name));
    }
}
