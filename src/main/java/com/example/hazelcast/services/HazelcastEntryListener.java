package com.example.hazelcast.services;

import com.example.hazelcast.dto.User;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.MapEvent;

import java.util.Map;

/**
 * Created by Vitaliy on 25.12.2019.
 */
public class HazelcastEntryListener implements EntryListener<Long, User>{
    Map<Long, User> map;

    public HazelcastEntryListener(Map<Long, User> map){
        this.map = map;
    }

    @Override
    public void entryAdded(EntryEvent<Long, User> entryEvent) {
        map.put(entryEvent.getKey(), entryEvent.getValue());
    }

    @Override
    public void entryEvicted(EntryEvent<Long, User> entryEvent) {
        //TODO
    }

    @Override
    public void entryRemoved(EntryEvent<Long, User> entryEvent) {
        map.remove(entryEvent.getKey());
    }

    @Override
    public void entryUpdated(EntryEvent<Long, User> entryEvent) {
        map.put(entryEvent.getKey(), entryEvent.getValue());
    }

    @Override
    public void mapCleared(MapEvent mapEvent) {
        map.clear();
    }

    @Override
    public void mapEvicted(MapEvent mapEvent) {
        //TODO
    }
}
