package com.company.events.model;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;

public class Event {

    public static final String EVENT_TITLE = "title";

    @MongoId
    @MongoObjectId
    private String id;
    private String title;
    private String description;
    private Date date;
    private String address;
    private Double price;

    public Event() {}

    public Event(String title, String description, Date date, String address, Double price) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.address = address;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
