package com.reserve.arenamanagement.event.internal.repository;

import com.reserve.arenamanagement.event.internal.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByName(String value);
}
