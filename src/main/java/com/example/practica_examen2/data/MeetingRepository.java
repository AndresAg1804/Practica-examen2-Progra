package com.example.practica_examen2.data;

import com.example.practica_examen2.logic.Meeting;
import com.example.practica_examen2.logic.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component("meetingRepository")
public class MeetingRepository {
    List<Meeting> list;
    int meeting_autonumber=1;

    String nextMeeting(){return (meeting_autonumber++)+""; }

    public Meeting findById(String id) throws Exception{
        Meeting r = list.stream()
                .filter( e-> e.getId().equals(id))
                .findFirst().get();
        return r.clone();
    }
    public List<Meeting> findAll(){ return list.stream().map(Meeting::clone).toList(); }

    public List<Meeting> findByUser(User user){
        return list.stream()
                .filter( e->e.getOwner().getEmail().equals(user.getEmail()))
                .map(Meeting::clone)
                .toList();
    }
    public Meeting save(Meeting entity){
        Meeting saved=entity.clone();
        saved.setId(nextMeeting());
        list.add(saved);
        return saved.clone();
    }

    UserRepository userRepository;
    @Autowired
    public MeetingRepository(UserRepository userRepository) {
        this.userRepository = userRepository;

        list = new ArrayList<Meeting>();
        try {
            User freeman=userRepository.findById("mfreeman@hollywood.com");
            list.add(new Meeting(freeman, nextMeeting(), "Plan new movie", "25/06/2023", "PUBLISHED"));
            list.add(new Meeting(freeman, nextMeeting(), "Party", "24/06/2023", "OVERDUE"));
            list.add(new Meeting(freeman, nextMeeting(), "Plot review", "27/06/2023", "UPCOMING"));

            User calle=userRepository.findById("scalle@hollywood.com");
            list.add(new Meeting(calle, nextMeeting(), "Rehearsal", "28/06/2023", "UPCOMING"));
        }
        catch (Exception e){ }
    }
}
