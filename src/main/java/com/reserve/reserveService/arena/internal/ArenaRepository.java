package com.reserve.reserveService.arena.internal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArenaRepository extends MongoRepository<Arena, String> {
}
