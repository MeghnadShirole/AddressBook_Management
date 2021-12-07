package com.addressbooktest;

import com.model.PersonInformation;
import com.service.AddressBookService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class AddressBookTest {
    @Test
    public void givenContactsInDB_MatchEmployeeCount() {
        AddressBookService addressBookService = new AddressBookService();
        List<PersonInformation> contactData = addressBookService.readPersonInfoData(AddressBookService.IOService.DB_IO);
        System.out.println(contactData);
        Assert.assertEquals(3, contactData.size());
    }
}
