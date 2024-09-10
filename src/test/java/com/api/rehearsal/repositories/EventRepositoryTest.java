package com.api.rehearsal.repositories;

import com.api.rehearsal.domain.event.Event;
import jakarta.persistence.EntityManager;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class EventRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("should retrieve the next events successfully")
    void findUpcomingEvents() {
        this.createDummyEventEntity();
        Pageable pageable = PageRequest.of(0, 1);
        System.out.println(new Date());
        Page<Event> eventPage = this.eventRepository.findUpcomingEvents(new Date(1704071445), pageable);
        assertEquals(eventPage.getTotalPages(), 1);

    }

    private void createDummyEventEntity() {
        Event event = new Event();
        event.setTitle("event dummy");
        event.setDescription("testing");
        event.setEventUrl("https://event-dummy.com");
        event.setDate(new Date(1704071445));
        event.setImgUrl("https://bucket-dummy.com");
        event.setRemote(false);
        event.setCity("Salvador");
        event.setState("BA");

        entityManager.persist(event);
    }

}