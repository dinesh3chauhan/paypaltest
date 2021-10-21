package com.paypal.bfs.test.bookingserv.impl;

import com.paypal.bfs.test.bookingserv.api.BookingResource;
import com.paypal.bfs.test.bookingserv.api.model.Address;
import com.paypal.bfs.test.bookingserv.api.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class BookingResourceImpl implements BookingResource {

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public ResponseEntity<Booking> create(Booking booking) {
        if(!payLoadValid(booking)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(Objects.nonNull(booking.getId())){
            BookingEntity entity = bookingRepository.save(convertDto(booking));
            return ResponseEntity.ok(convertEntity(entity));
        }else {
            List<BookingEntity> entities = bookingRepository.findAllBookingsBy(
                    booking.getFirstName(),booking.getLastName(),booking.getDateOfBirth(),
                    booking.getCheckinDatetime(),booking.getCheckoutDatetime(),booking.getAddress().getCity(),
                    booking.getAddress().getState(),booking.getAddress().getZipCode(),booking.getAddress().getLine1(),
                    booking.getAddress().getLine2(),booking.getTotalprice(), booking.getDeposit());
           if(!CollectionUtils.isEmpty(entities)){
               return ResponseEntity.ok(convertEntity(entities.get(0)));
           } else {
               BookingEntity newBooking =  bookingRepository.save(convertDto(booking));
               return ResponseEntity.ok(convertEntity(newBooking));
           }
        }
    }

    @Override
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        bookingRepository.findAll().forEach(booking->{
            bookings.add(convertEntity(booking));

        });
        return  ResponseEntity.ok(bookings);
    }

    private boolean payLoadValid(Booking booking){
        if(StringUtils.isEmpty(booking.getFirstName())
                || StringUtils.isEmpty(booking.getLastName())
                ||StringUtils.isEmpty(booking.getCheckinDatetime())
                || Objects.isNull(booking.getDeposit())
                || Objects.isNull(booking.getTotalprice())
                || StringUtils.isEmpty(booking.getCheckoutDatetime())
                || StringUtils.isEmpty(booking.getDateOfBirth())
                || Objects.isNull(booking.getAddress()) ||
                (Objects.nonNull(booking.getAddress()) &&
                        (StringUtils.isEmpty(booking.getAddress().getCity())
                                || StringUtils.isEmpty(booking.getAddress().getState())
                                || StringUtils.isEmpty(booking.getAddress().getLine1())
                                ||  StringUtils.isEmpty(booking.getAddress().getZipCode())))){
                return false;
        }
        return true;
    }



    private BookingEntity convertDto(Booking booking){
        BookingEntity entity = new BookingEntity();
        entity.setId(booking.getId());
        entity.setFirstName(booking.getFirstName());
        entity.setLastName(booking.getLastName());
        entity.setDob(booking.getDateOfBirth());
        entity.setCheckInTime(booking.getCheckinDatetime());
        entity.setCheckOutTime(booking.getCheckoutDatetime());
        entity.setTotalPrice(booking.getTotalprice());
        entity.setDeposit(booking.getDeposit());
        if(Objects.nonNull(booking.getAddress())){
            entity.setZipcode(booking.getAddress().getZipCode());
            entity.setLine1(booking.getAddress().getLine1());
            entity.setLine2(booking.getAddress().getLine2());
            entity.setCity(booking.getAddress().getCity());
            entity.setState(booking.getAddress().getState());
        }
        return entity;
    }

    private Booking convertEntity(BookingEntity booking){
        Booking dto = new Booking();
        dto.setId(booking.getId());
        dto.setFirstName(booking.getFirstName());
        dto.setLastName(booking.getLastName());
        dto.setDateOfBirth(booking.getDob());
        dto.setCheckinDatetime(booking.getCheckInTime());
        dto.setCheckoutDatetime(booking.getCheckOutTime());
        dto.setTotalprice(booking.getTotalPrice());
        dto.setDeposit(booking.getDeposit());
        Address address = new Address();
        address.setZipCode(booking.getZipcode());
        address.setLine1(booking.getLine1());
        address.setLine2(booking.getLine2());
        address.setCity(booking.getCity());
        address.setState(booking.getState());
        dto.setAddress(address);
        return dto;
    }

    @GetMapping("/aa")
    public String aa(){
        return "aa";
    }
}
