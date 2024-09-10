package com.api.rehearsal.controllers;

import com.api.rehearsal.domain.coupon.Coupon;
import com.api.rehearsal.domain.coupon.CouponRequestDTO;
import com.api.rehearsal.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("event/{eventId}")
    public ResponseEntity<Coupon> addCouponsToEvent(@PathVariable UUID eventId, @RequestBody CouponRequestDTO data){
        Coupon coupon = this.couponService.addCouponToEvent(eventId, data);
        return ResponseEntity.ok(coupon);
    }
}
