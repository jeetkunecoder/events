package com.company.events.controller;

import com.company.events.application.EventsApplication;

import com.company.events.model.Event;
import com.company.events.service.EventService;
import com.google.common.collect.ImmutableList;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import io.swagger.annotations.ApiOperation;
import org.jongo.Find;
import org.jongo.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = EventsApplication.API_V1, produces = MediaType.APPLICATION_JSON_VALUE)
public class EventsController {

    public static final String ENDPOINT = "events";

    @Autowired
    private EventService service;

    @ApiOperation(value = "Register a new event", nickname = "Register a new event",
            notes = "Endpoint to register a new event. If the event already exists, the info will be updated"
                    + " with the submitted data.")
    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpEntity<Event> registerEvent(final @Validated @RequestBody Event event) {
        return new ResponseEntity<>(service.registerEvent(event), HttpStatus.OK);
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpEntity<Event> updateEvent(final @Validated @RequestBody Event event) {
        return new ResponseEntity<>(service.updateEvent(event), HttpStatus.OK);
    }

    @ApiOperation(value = "Find the event details", nickname = "Find the event details",
            notes = "It will retrieve the event details")
    @RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<Event> getEventDetails(@PathVariable String id) {
        Event event = service.findEventById(id);
        return new ResponseEntity<>(event, event != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Find the events", nickname = "Find the events",
            notes = "It will retrieve a collection of event")
    @RequestMapping(value = ENDPOINT , method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<ImmutableList<Event>> getEvents() {
        ImmutableList<Event> collection = service.findEvents();

        return new ResponseEntity<>(collection, collection != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Find the lastest events", nickname = "Find the lastest events",
            notes = "It will retrieve a collection of event")
    @RequestMapping(value = ENDPOINT + "/latest" , method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<ImmutableList<Event>> getLatestEvent() {
        ImmutableList<Event> collection = service.findLastesEvents();
        return new ResponseEntity<>(collection, collection != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Delete the specified event", nickname = "Delete the specified event",
            notes = "It will delete the event specified in the request.")
    @RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public HttpEntity<Void> deleteEvent(@PathVariable String id) {
        return new ResponseEntity<>(service.deleteEvent(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
