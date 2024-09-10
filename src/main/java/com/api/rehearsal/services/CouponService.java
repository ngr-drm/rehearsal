package com.api.rehearsal.services;

import com.api.rehearsal.domain.coupon.Coupon;
import com.api.rehearsal.domain.coupon.CouponRequestDTO;
import com.api.rehearsal.domain.event.Event;
import com.api.rehearsal.repositories.CouponRepository;
import com.api.rehearsal.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class CouponService {
    @Autowired
    private CouponRepository repository;

    @Autowired
    private EventRepository eventRepository;

    public Coupon addCouponToEvent(UUID eventId, CouponRequestDTO data){
        Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("event not found"));

        Coupon newCoupon = new Coupon();
        newCoupon.setCode(data.code());
        newCoupon.setDiscount(data.discount());
        newCoupon.setValid(new Date(data.valid()));
        newCoupon.setEvent(event);
        return repository.save(newCoupon);

    }
}
