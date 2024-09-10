package com.api.rehearsal.services;

import com.amazonaws.services.s3.AmazonS3;
import com.api.rehearsal.domain.event.Event;
import com.api.rehearsal.domain.event.EventRequestDTO;
import com.api.rehearsal.domain.event.EventResponseDTO;
import com.api.rehearsal.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class EventService {
    @Value(("${aws.bucket.name}"))
    private String bucketName;

    @Autowired
    private EventRepository repository;

    @Autowired
    private AmazonS3 s3Client;


    public Event createEvent(EventRequestDTO data) {
        String imgUrl = null;

        if(data.image() != null) {
            imgUrl = this.uploadImg(data.image());
        }

        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setDate(new Date(data.date()));
        newEvent.setImgUrl(imgUrl);
        newEvent.setRemote(data.remote());
        newEvent.setCity(data.city());
        newEvent.setState(data.state());

        repository.save(newEvent);

        return newEvent;
    }

    private String uploadImg(MultipartFile multipartFile) {
        String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        try {
            File file = this.convertMultipartToFile(multipartFile);
            s3Client.putObject(bucketName, filename, file);
            file.delete();
            return  s3Client.getUrl(bucketName, filename).toString();
        } catch (Exception e) {
            System.out.println("file upload error" );
            return null;
        }
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream output = new FileOutputStream(file);
        output.write(multipartFile.getBytes());
        output.close();
        return file;
    }

    public List<EventResponseDTO> getUpcomingEvents(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventPage = this.repository.findUpcomingEvents(new Date(), pageable);
        return  eventPage.map(event -> new EventResponseDTO(event.getTitle(), event.getDescription(), event.getDate(), "", "", event.getRemote(), event.getEventUrl(), event.getImgUrl() ))
                .stream().toList();

    }
}
