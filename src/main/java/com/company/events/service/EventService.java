package com.company.events.service;

import static com.company.events.model.Event.EVENT_TITLE;

import static com.company.events.database.MongoUtils.filterToString;

import com.company.events.database.MongoRepository;
import com.company.events.model.Event;
import com.google.common.collect.ImmutableList;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
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
    
    // Creates a new event
    public Event registerEvent (Event event) {
        event.setId(null);
        events.save(event);
        return event;
    }

    public Event updateEvent(Event event){
        Event dbEvent = findEventById(event.getId());
        events.update(new ObjectId(dbEvent.getId())).with(event);
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


    public ImmutableList<Event> findEvents() {
        MongoCursor<Event> eventList = events.find().as(Event.class);
        return ImmutableList.copyOf((Iterable<Event>) eventList);
    }

    public ImmutableList<Event> findLastesEvents() {
        MongoCursor<Event> eventList = events.find().limit(3).as(Event.class);
        return ImmutableList.copyOf((Iterable<Event>) eventList);
    }




}
