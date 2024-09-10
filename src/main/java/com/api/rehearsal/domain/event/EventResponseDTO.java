package com.api.rehearsal.domain.event;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public record EventResponseDTO(String title, String description, Date date, String city, String state, Boolean remote, String eventUrl, String imgUrl) {
}
