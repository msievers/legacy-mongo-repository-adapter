package com.github.msievers.legacy.mongorepositoryadapter;

import com.github.msievers.legacy.mongorepositoryadapter.entity.Pet;
import com.github.msievers.legacy.mongorepositoryadapter.repository.PetRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataMongoTest
public class LegacyMongoRepositoryAdapterTest {

    @Autowired
    PetRepository petRepository;

    @Autowired
    MongoOperations mongoOperations; // we need this to avoid using the repository for setup/teardown tasks

    private Pet getSomePet() {
        return new Pet()
            .setId("66217398-e519-421f-b9ae-5132127bd0af")
            .setName("Bella");
    }

    private Pet getSomeOtherPet() {
        return new Pet()
            .setId("fcce4536-0412-4f09-b4e7-19291c0573aa")
            .setName("Charlie");
    }

    private Pet seedPet(Pet pet) {

        mongoOperations.save(pet);
        return pet;
    }

    @Before
    public void removeAllPets() {
        String collectionName = mongoOperations.getCollectionName(Pet.class);
        mongoOperations.getCollection(collectionName).drop();
    }

    /**
     * delete
     */
    @Test
    public void deleteWithIdParameterTest() {

        // given
        seedPet(getSomePet());
        seedPet(getSomeOtherPet());

        // pre-conditions
        assertThat(petRepository.count(), equalTo(2L));

        // when
        petRepository.delete(getSomePet().getId());

        // then
        assertThat(petRepository.count(), equalTo(1L));
    }

    @Test
    public void deleteWithIterableParameterTest() {

        // given
        Pet somePet = seedPet(getSomePet());
        seedPet(getSomeOtherPet());

        // pre-conditions
        assertThat(petRepository.count(), equalTo(2L));

        // when
        petRepository.delete(Collections.singletonList(somePet));

        // then
        assertThat(petRepository.count(), equalTo(1L));
    }

    /**
     * exists
     */
    @Test
    public void existsWithIdParameterTest() {

        // given
        Pet somePet = seedPet(getSomePet());

        // when
        boolean exists = petRepository.exists(somePet.getId());

        // then
        assertThat(exists, equalTo(true));
    }

    /**
     * findAll
     */
    @Test
    public void findAllWithIterableParameterTest() {

        // given
        Pet somePet = seedPet(getSomePet());
        Pet someOtherPet = seedPet(getSomeOtherPet());

        Iterable<String> petIds = getIdsFrom(somePet, someOtherPet);

        // when
        List<Pet> petList = petRepository.findAll(petIds);

        // then
        assertThat(petList.size(), equalTo(2));
        assertThat(petList.contains(somePet), equalTo(true));
        assertThat(petList.contains(someOtherPet), equalTo(true));
    }

    @Test
    public void testFindAllWithIterableParameter() {

        // given
        Pet seededPet = seedPet(getSomePet());
        Iterable<String> petIds = getIdsFrom(seededPet);

        // when
        List<Pet> pets = petRepository.findAll(petIds);

        // then
        assertThat(pets.size(), equalTo(1));
    }

    /**
     * findOne
     */
    @Test
    public void findOneWithIdParameterTest() {

        // given
        Pet somePet = seedPet(getSomePet());

        // when
        String nameSomePetRepository = petRepository.findOne(somePet.getId()).getName();

        // then
        assertThat(somePet.getName(), equalTo(nameSomePetRepository));
    }

    /**
     * save
     */
    @Test
    public void saveWithIterableParameterTest() {

        // given
        Pet somePet = getSomePet();
        Pet someOtherPet = getSomeOtherPet();

        Iterable<Pet> pets = Arrays.asList(somePet, someOtherPet);

        // when
        List<Pet> savedPets = petRepository.save(pets);

        // then
        savedPets.forEach(pet -> {
            assertThat(pet.getName().equals(somePet.getName()) || pet.getName().equals(someOtherPet.getName()), is(true));
        });
    }

    private Iterable<String> getIdsFrom(Pet... pets) {

        return Stream.of(pets)
            .map(Pet::getId)
            .collect(Collectors.toList());
    }
}
