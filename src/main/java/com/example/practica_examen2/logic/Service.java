package com.example.practica_examen2.logic;


import com.example.practica_examen2.data.*;
import com.example.practica_examen2.data.ContactRepository;
import com.example.practica_examen2.data.MeetingRepository;
import com.example.practica_examen2.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service("Service")
public class Service {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private ContactRepository contactRepository;

    // ------------------- Meeting -------------------

    public List<Meeting> getMeetings(User user) {
        return meetingRepository.findByUser(user);
    }

    public Meeting findMeetingById(String id) throws Exception {
        return meetingRepository.findById(id);
    }
    public Meeting saveMeeting(Meeting m){
        return meetingRepository.save(m);
    }


    // ------------------- Contact ------------------

    public List<Contact> getContacts(Meeting meeting){
        return contactRepository.findByMeeting(meeting);
    }
    public List<Contact> contactsByUser(User user){return contactRepository.findByUser(user);}
    public void saveContact(Contact c){
        contactRepository.save(c);
    }
}
