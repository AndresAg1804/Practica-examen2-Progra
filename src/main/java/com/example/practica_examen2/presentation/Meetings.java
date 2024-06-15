package com.example.practica_examen2.presentation;

import com.example.practica_examen2.logic.Contact;
import com.example.practica_examen2.logic.Meeting;
import com.example.practica_examen2.logic.Service;
import com.example.practica_examen2.security.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class Meetings {
    @Autowired
    private Service service;

    @GetMapping
    public List<Meeting> getMeetingsByUser(@AuthenticationPrincipal UserDetailsImp user) {
        return service.getMeetings(user.getUser());
    }

    @GetMapping("/{id}")
    public Meeting getMeetingById(@PathVariable String id){
        try{
            return service.findMeetingById(id);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/contacts")
    public List<Contact> getContacts(@RequestParam String idM){
        try{
            Meeting m=new Meeting();
            m.setId(idM);
            return service.getContacts(m);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Meeting saveMeeting(@RequestBody Meeting meeting){
        return service.saveMeeting(meeting);
    }

    @GetMapping("/contacts/byUser")
    public List<Contact> contactsByUser(@AuthenticationPrincipal UserDetailsImp user){
        return service.contactsByUser(user.getUser());
    }

    @PostMapping("/addContacts")
    public void saveContacts(@RequestBody List<Contact> contacts){
        for(Contact c:contacts){
            service.saveContact(c);
        }
    }

}
