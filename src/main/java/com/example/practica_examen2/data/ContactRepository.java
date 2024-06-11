package com.example.practica_examen2.data;
import com.example.practica_examen2.logic.Contact;
import com.example.practica_examen2.logic.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("contactRepository")
public class ContactRepository {
    List<Contact> list;

    int contact_autonumber=0;
    String nextContact(){return (contact_autonumber++)+""; }

    public Contact findById(String id) throws Exception{
        Contact r = list.stream()
                .filter( e-> e.getEmail().equals(id))
                .findFirst().get();
        return r.clone();
    }
    public List<Contact> findAll(){return list.stream().map(Contact::clone).toList(); }

    public List<Contact> findByMeeting(Meeting meeting){
        return list.stream()
                .filter( e->e.getMeeting().getId().equals(meeting.getId()))
                .map(Contact::clone)
                .toList();
    }

    public Contact save(Contact entity){
        Contact saved=entity.clone();
        saved.setId(nextContact());
        list.add(saved);
        return saved;
    }

    MeetingRepository meetingRepository;
    @Autowired
    public ContactRepository(MeetingRepository meetingRepository){
        this.meetingRepository = meetingRepository;
        list = new ArrayList<Contact>();
        try {
            Meeting m1=meetingRepository.findById("1");
            list.add(new Contact(m1, nextContact(),"mpfeiffer@hollywood.com", "Michelle Pfeiffer"));
            list.add(new Contact(m1, nextContact(),"jroberts@gmail.com", "Julia Roberts"));

            Meeting m2=meetingRepository.findById("2");
            list.add(new Contact(m2, nextContact(),"jroberts@gmail.com", "Julia Roberts"));
            list.add(new Contact(m2, nextContact(),"tcruise@hollywood.com", "Tom Cruise"));

            Meeting m3=meetingRepository.findById("3");
            list.add(new Contact(m3, nextContact(),"jroberts@gmail.com", "Julia Roberts"));

            Meeting m4=meetingRepository.findById("4");
            list.add(new Contact(m4, nextContact(),"emiller@hollywood.com", "Esra Miller"));
            list.add(new Contact(m4, nextContact(),"mkeaton@gmail.com", "Michael Keaton"));
        }
        catch (Exception e){ }
   }

}
