package com.api.rehearsal.domain.coupon;

public record CouponRequestDTO(String code, Integer discount, Long valid) {
}
