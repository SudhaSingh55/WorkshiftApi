package org.workshift.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.workshift.model.ShiftRequest;
import org.workshift.model.ShopRequest;
import org.workshift.model.UserRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class WorkShiftServiceTest {

    @Autowired
    WorkShiftService workShiftService;

    public static final String DATETIME_FORMAT = "yyyy-MM-dd['T']HH:mm:ss.SSSSSS";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);


    @Transactional
    @Test
    public void testWorkShiftCases(){

        createUser();
        createShop();
        addUserToShop();
        createShift();
        addUserToShiftAtShopValidations();
        addUserToShiftAtShop();
        addUserToShiftAtShopFailureCases();
    }

    private void createUser(){
        UserRequest user1 = new UserRequest()
                .setUserId("1")
                .setUserName("John")
                .setAddress("Green Wood")
                .setPhoneNumber("1234567")
                .setEmail("john@test.com");

        UserRequest user2 = new UserRequest()
                .setUserId("2")
                .setUserName("Kim")
                .setAddress("Green Wood")
                .setPhoneNumber("1234567")
                .setEmail("kim@test.com");
        String result1 = workShiftService.createUser(user1);
        String result2 = workShiftService.createUser(user2);
        Assertions.assertEquals("User Created Successfully",result1);
        Assertions.assertEquals("User Created Successfully",result2);

    }

    private void createShop(){
        ShopRequest shop1 = new ShopRequest()
                .setShopId("101")
                .setShopName("shop1")
                .setAddress("Green wood");
        ShopRequest shop2 = new ShopRequest()
                .setShopId("102")
                .setShopName("shop2")
                .setAddress("Green wood");
        ShopRequest shop3 = new ShopRequest()
                .setShopId("103")
                .setShopName("shop2")
                .setAddress("Green wood");
        String result1 = workShiftService.createShop(shop1);
        String result2 = workShiftService.createShop(shop2);
        String result3 = workShiftService.createShop(shop3);
        Assertions.assertEquals("Shop Created Successfully",result1);
        Assertions.assertEquals("Shop Created Successfully",result2);
        Assertions.assertEquals("Shop Created Successfully",result3);
    }

    private void addUserToShop(){
        ShopRequest shop1 = new ShopRequest()
                .setShopId("101")
                .setShopName("shop1")
                .setAddress("Green wood")
                .setUserId("1");
        ShopRequest shop2 = new ShopRequest()
                .setShopId("102")
                .setShopName("shop2")
                .setAddress("Green wood")
                .setUserId("1");
        ShopRequest shop3 = new ShopRequest()
                .setShopId("103")
                .setShopName("shop3")
                .setAddress("Green wood")
                .setUserId("2");
        String result1 = workShiftService.addUserToShop(shop1);
        String result2 = workShiftService.addUserToShop(shop2);
        String result3 = workShiftService.addUserToShop(shop3);
        Assertions.assertEquals("UserId added to shop successfully!!",result1);
        Assertions.assertEquals("UserId added to shop successfully!!",result2);
        Assertions.assertEquals("UserId added to shop successfully!!",result3);
    }

    private void createShift(){
        ShiftRequest shift1 = new ShiftRequest()
                .setShiftId("201")
                .setShiftDescription("DayShift");
        ShiftRequest shift2 = new ShiftRequest()
                .setShiftId("202")
                .setShiftDescription("NightShift");
        ShiftRequest shift3 = new ShiftRequest()
                .setShiftId("203")
                .setShiftDescription("NightShift");
        ShiftRequest shift4 = new ShiftRequest()
                .setShiftId("204")
                .setShiftDescription("NightShift");
        ShiftRequest shift5 = new ShiftRequest()
                .setShiftId("205")
                .setShiftDescription("NightShift");
        ShiftRequest shift6 = new ShiftRequest()
                .setShiftId("206")
                .setShiftDescription("NightShift");
        ShiftRequest shift7 = new ShiftRequest()
                .setShiftId("207")
                .setShiftDescription("NightShift");
        ShiftRequest shift8 = new ShiftRequest()
                .setShiftId("208")
                .setShiftDescription("NightShift");
        String result1 = workShiftService.createShift(shift1);
        String result2 = workShiftService.createShift(shift2);
        String result3 = workShiftService.createShift(shift3);
        String result4 = workShiftService.createShift(shift4);
        String result5 = workShiftService.createShift(shift5);
        String result6 = workShiftService.createShift(shift6);
        String result7 = workShiftService.createShift(shift7);
        String result8 = workShiftService.createShift(shift8);
        Assertions.assertEquals("Shift Created Successfully",result1);
        Assertions.assertEquals("Shift Created Successfully",result2);
        Assertions.assertEquals("Shift Created Successfully",result3);
        Assertions.assertEquals("Shift Created Successfully",result4);
        Assertions.assertEquals("Shift Created Successfully",result5);
        Assertions.assertEquals("Shift Created Successfully",result6);
        Assertions.assertEquals("Shift Created Successfully",result7);
        Assertions.assertEquals("Shift Created Successfully",result8);
    }

    private void addUserToShiftAtShopValidations(){
        //Validation Case1 : ShiftDuration should not be more than 8hr
        ShiftRequest shift1 = new ShiftRequest()
                .setShiftId("201")
                .setShiftDescription("DayShift")
                .setUserId("1")
                .setShopId("101")
                .setShiftDuration(9);
        String result1 = workShiftService.addUserByShopInShift(shift1);
        Assertions.assertEquals("Shift Duration cannot be greater than 8 hours",result1);

        //Validation Case2 : User Should be a valid user ID
        shift1.setShiftDuration(8);
        shift1.setUserId("111");
        result1 = workShiftService.addUserByShopInShift(shift1);
        Assertions.assertEquals("Not a valid user Id!!!",result1);

        //Validation Case3 : Shop Should be a valid shop ID
        shift1.setUserId("1");
        shift1.setShopId("1111");
        result1 = workShiftService.addUserByShopInShift(shift1);
        Assertions.assertEquals("Not a valid shop Id!!!",result1);

        //Validation Case4 : User ID cannot be null
        shift1.setUserId(null);
        result1 = workShiftService.addUserByShopInShift(shift1);
        Assertions.assertEquals("User Id cannot be null",result1);

        //Validation Case5 : Shop ID cannot be null
        shift1.setUserId("1");
        shift1.setShopId(null);
        result1 = workShiftService.addUserByShopInShift(shift1);
        Assertions.assertEquals("Shop Id cannot be null",result1);

        //Validation Case5 : Shift ID should be valid
        shift1.setShopId("101");
        shift1.setShiftId("10111");
        result1 = workShiftService.addUserByShopInShift(shift1);
        Assertions.assertEquals("Shift Id is not created !!!!",result1);
    }

    private void addUserToShiftAtShop(){
        LocalDateTime startTime = LocalDateTime.now().minusDays(10);
        //add user 1 to shop1
        ShiftRequest shift1 = new ShiftRequest()
                .setShiftId("201")
                .setShiftDescription("DayShift")
                .setUserId("1")
                .setShopId("101")
                .setShiftDuration(8)
                .setStartTime(startTime.format(dateTimeFormatter));
        String result1 = workShiftService.addUserByShopInShift(shift1);
        Assertions.assertEquals("User added successfully to shift for a shop",result1);

        //add user 1 to shop2
        startTime = startTime.plusDays(1);
        ShiftRequest shift2 = new ShiftRequest()
                .setShiftId("202")
                .setShiftDescription("DayShift")
                .setUserId("1")
                .setShopId("102")
                .setShiftDuration(8)
                .setStartTime(startTime.format(dateTimeFormatter));
        String result2 = workShiftService.addUserByShopInShift(shift2);
        Assertions.assertEquals("User added successfully to shift for a shop",result2);

    }

    private void addUserToShiftAtShopFailureCases(){

        /********** Using Shift 202 as base ref data************/
        //A user can not work in multiple shops at the same time.
        LocalDateTime startTime = LocalDateTime.now().minusDays(10);
        LocalDateTime startTime1 = startTime.plusHours(25);
        ShiftRequest shift3 = new ShiftRequest()  // same data as of shift 202
                .setShiftId("203")
                .setShiftDescription("DayShift")
                .setUserId("1")
                .setShopId("101")
                .setShiftDuration(8)
                .setStartTime(startTime1.format(dateTimeFormatter));
        String result3 = workShiftService.addUserByShopInShift(shift3);
        Assertions.assertEquals("User cannot work in multiple shop at the same time",result3);

        //No user is allowed to work in the same shop for more than 8 hours, within a 24 hour window.
        LocalDateTime startTime2 = startTime.plusDays(1).plusHours(9);
         shift3 = new ShiftRequest()  // same data as of shift 202
                .setShiftId("203")
                .setShiftDescription("DayShift")
                .setUserId("1")
                .setShopId("102")
                .setShiftDuration(8)
                .setStartTime(startTime2.format(dateTimeFormatter));
         result3 = workShiftService.addUserByShopInShift(shift3);
        Assertions.assertEquals("User is not allowed to work more than 8hour in same shop",result3);

        //No user can work more than 5 days in a row in the same shop.
        // COUNTER1
        LocalDateTime startTimeIncr = startTime.plusDays(2);
        ShiftRequest shift = new ShiftRequest()  // same data as of shift 202
                .setShiftId("203")
                .setShiftDescription("DayShift")
                .setUserId("2")
                .setShopId("103")
                .setShiftDuration(8)
                .setStartTime(startTimeIncr.format(dateTimeFormatter));
        String result = workShiftService.addUserByShopInShift(shift);
        Assertions.assertEquals("User added successfully to shift for a shop",result);

        // COUNTER2
        startTimeIncr = startTime.plusDays(3);
        shift = new ShiftRequest()  // same data as of shift 202
                .setShiftId("204")
                .setShiftDescription("DayShift")
                .setUserId("2")
                .setShopId("103")
                .setShiftDuration(8)
                .setStartTime(startTimeIncr.format(dateTimeFormatter));
        result = workShiftService.addUserByShopInShift(shift);
        Assertions.assertEquals("User added successfully to shift for a shop",result);

        // COUNTER3
        startTimeIncr = startTime.plusDays(4);
        shift = new ShiftRequest()  // same data as of shift 202
                .setShiftId("205")
                .setShiftDescription("DayShift")
                .setUserId("2")
                .setShopId("103")
                .setShiftDuration(8)
                .setStartTime(startTimeIncr.format(dateTimeFormatter));
        result = workShiftService.addUserByShopInShift(shift);
        Assertions.assertEquals("User added successfully to shift for a shop",result);

        // COUNTER4
        startTimeIncr = startTime.plusDays(5);
        shift = new ShiftRequest()  // same data as of shift 202
                .setShiftId("206")
                .setShiftDescription("DayShift")
                .setUserId("2")
                .setShopId("103")
                .setShiftDuration(8)
                .setStartTime(startTimeIncr.format(dateTimeFormatter));
        result = workShiftService.addUserByShopInShift(shift);
        Assertions.assertEquals("User added successfully to shift for a shop",result);

        // COUNTER5
        startTimeIncr = startTime.plusDays(6);
        shift = new ShiftRequest()  // same data as of shift 202
                .setShiftId("207")
                .setShiftDescription("DayShift")
                .setUserId("2")
                .setShopId("103")
                .setShiftDuration(8)
                .setStartTime(startTimeIncr.format(dateTimeFormatter));
        result = workShiftService.addUserByShopInShift(shift);
        Assertions.assertEquals("User added successfully to shift for a shop",result);

        // COUNTER6 ----> This shift request should not be allowed
        startTimeIncr = startTime.plusDays(7);
        shift = new ShiftRequest()  // same data as of shift 202
                .setShiftId("208")
                .setShiftDescription("DayShift")
                .setUserId("2")
                .setShopId("103")
                .setShiftDuration(8)
                .setStartTime(startTimeIncr.format(dateTimeFormatter));
        result = workShiftService.addUserByShopInShift(shift);
        Assertions.assertEquals("User cannot work more than 5 days in same shop",result);

    }

}
