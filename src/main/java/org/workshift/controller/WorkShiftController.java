package org.workshift.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.workshift.model.ShiftRequest;
import org.workshift.model.ShopRequest;
import org.workshift.model.UserRequest;
import org.workshift.service.WorkShiftService;

@RestController()
@RequestMapping("/workshift")
@RequiredArgsConstructor
public class WorkShiftController {

    private final WorkShiftService workShiftService;

    @PostMapping("/create-user")
    public String createUser(@RequestBody UserRequest user) {
        return workShiftService.createUser(user);
    }

    @PostMapping("/create-shop")
    public String createShop(@RequestBody ShopRequest shop) {
        return workShiftService.createShop(shop);
    }

    @PostMapping("/add-user-shop")
    public String addUserToShop(@RequestBody ShopRequest shop) throws Exception {
        return workShiftService.addUserToShop(shop);
    }

    @PostMapping("/create-shift")
    public String addShift(@RequestBody ShiftRequest shift) {
        return workShiftService.createShift(shift);
    }

    @PostMapping("/add-user-shift-shop")
    public String addUserToShift(@RequestBody ShiftRequest shift) {
        return workShiftService.addUserByShopInShift(shift);
    }
}
