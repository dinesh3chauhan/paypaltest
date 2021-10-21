package com.paypal.bfs.test.bookingserv;

import com.paypal.bfs.test.bookingserv.api.model.Address;
import com.paypal.bfs.test.bookingserv.api.model.Booking;
import com.paypal.bfs.test.bookingserv.impl.BookingResourceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BookingServApplicationTests {

    @Autowired
    private BookingResourceImpl bookingService;

    @Test
    public void createDuplicateBookingTest(){

        Booking booking = new Booking();
        booking.setFirstName("test");
        booking.setLastName("user");
        booking.setDeposit(12);
        booking.setTotalprice(20);
        booking.setCheckoutDatetime("12-10-2021");
        booking.setCheckinDatetime("11-10-2021");
        booking.setDateOfBirth("10-10-1993");
        Address address = new Address();
        address.setZipCode("111111");
        address.setCity("city");
        address.setState("state");
        address.setLine1("line 1");
        booking.setAddress(address);
        ResponseEntity<Booking> bookingResponseEntity = bookingService.create(booking);
        ResponseEntity<Booking> bookingResponseEntity2 = bookingService.create(booking);
        ResponseEntity<List<Booking>> allBookings = bookingService.getAllBookings();
        Assert.assertEquals(1,allBookings.getBody().size());
    }
}
