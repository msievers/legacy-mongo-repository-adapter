package com.github.msievers.legacy.mongorepositoryadapter.repository;

import com.github.msievers.legacy.mongorepositoryadapter.LegacyMongoRepositoryAdapter;
import com.github.msievers.legacy.mongorepositoryadapter.entity.Pet;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends LegacyMongoRepositoryAdapter<Pet, String> {
}
