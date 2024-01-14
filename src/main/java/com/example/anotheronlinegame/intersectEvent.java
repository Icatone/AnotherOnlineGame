package com.example.anotheronlinegame;

import javafx.event.Event;
import javafx.event.EventType;

public class intersectEvent extends Event {

    final TakenObjectNode intersectedObject;

    public intersectEvent(EventType<? extends Event> eventType, TakenObjectNode intersectedObject) {
        super(eventType);
        this.intersectedObject = intersectedObject;
    }

    public TakenObjectNode getIntersectedObject() {
        return intersectedObject;
    }
}
