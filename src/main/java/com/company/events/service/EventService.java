package com.company.events.service;

import static com.company.events.model.Event.EVENT_TITLE;

import static com.company.events.database.MongoUtils.filterToString;

import com.company.events.database.MongoRepository;
import com.company.events.model.Event;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.mongodb.client.model.Filters.eq;

@Component
public class EventService {

    private static final String COLLECTION = "events";

    @Autowired
    private MongoRepository repository;
    private MongoCollection events;

    @PostConstruct
    public void init() { events = repository.getCollection(COLLECTION); }

    public Event registerEvent (Event event) {
        event.setId(null);
        Event dbEvent = findEventById(event.getTitle());

        if(dbEvent == null) {
            events.save(event);
        } else {
            events.update(new ObjectId(dbEvent.getId())).with(event);
            event.setId(dbEvent.getId());
        }
        return event;
    }

    public Event findEventById(String id) { return events.findOne(new ObjectId(id)).as(Event.class); }

    public Event findEventByTitle(String name) {
        return events.findOne(filterToString(eq(EVENT_TITLE, name))).as(Event.class);
    }

    public boolean deleteEvent(String id) {
        Event event = findEventById(id);
        if (event != null) {
            events.remove(new ObjectId(event.getId()));
            return true;
        }
        return false;
    }
}
