package com.reserve.reserveService.arena.internal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArenaRepository extends MongoRepository<Arena, String> {
    List<Arena> findByName(String value);
}
