package com.paypal.bfs.test.bookingserv.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity,Integer> {

    @Query("SELECT b FROM BookingEntity b WHERE" +
            " b.firstName = :fname and b.lastName = :lname" +
            " and b.dob =:dob and b.checkInTime =:checkInTime " +
            "and b.checkOutTime=:checkOutTime and b.city=:city and b.state =:state" +
            " and b.zipcode=:zipCode and b.line1=:line1 and (:line2 is null or b.line2=:line2)" +
            " and b.totalPrice=:totalPrice and b.deposit=:deposit"
            )
    List<BookingEntity> findAllBookingsBy(@Param("fname") String fname,
                                           @Param("lname") String lname,@Param("dob") String dob,
                                           @Param("checkInTime") String checkInTime,
                                           @Param("checkOutTime") String checkOutTime,
                                           @Param("city") String city,
                                           @Param("state") String state, @Param("zipCode") String zipCode,
                                           @Param("line1") String line1, @Param("line2") String line2,
                                          @Param("totalPrice") Integer totalPrice,@Param("deposit") Integer deposit);
}
