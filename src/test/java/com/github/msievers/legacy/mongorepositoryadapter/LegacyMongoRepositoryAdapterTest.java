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

    private Iterable<Pet> getIterableOfPets() {

        ArrayList<Pet> petList = new ArrayList<>();

        petList.add(getSomePet());
        petList.add(getSomeOtherPet());

        return petList;
    }

    private ArrayList<String> getIterableOfIDs(Iterable<Pet> petlist) {

        ArrayList<String> idList = new ArrayList<>();

        for (Pet p : petlist) {
            idList.add(p.getId());
        }

        return idList;
    }


    private Pet seedPet(Pet pet) {

        mongoOperations.save(pet);
        return pet;
    }

    private Pet seedSomePet() {

        Pet pet = getSomeOtherPet();
        mongoOperations.save(pet);
        return pet;
    }

    private Pet seedSomeOtherPet() {

        Pet pet = getSomeOtherPet();
        mongoOperations.save(pet);
        return pet;
    }

    private Iterable<Pet> seedIterableOfPets(Iterable<Pet> pets) {
        for (Pet pet : pets) {
            seedPet(pet);
        }
        return pets;
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

        //given
        seedPet(getSomePet());
        seedPet(getSomeOtherPet());

        //when
        petRepository.delete(getSomePet().getId());

        //then
        assertThat(petRepository.count(), equalTo(1L));

    }

    @Test
    public void deleteWithIterableParameterTest() {

        // given
        Pet somePet = seedPet(getSomePet());
        Pet someOtherPet = seedPet(getSomeOtherPet());

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

        //given
        seedIterableOfPets(getIterableOfPets());

        //when
        List<Pet> petList = petRepository.findAll(getIterableOfIDs(getIterableOfPets()));

        //then
        assertThat(petList.size(), equalTo(2));
        assertThat(petList.contains(getSomePet()), equalTo(true));
        assertThat(petList.contains(getSomeOtherPet()), equalTo(true));
    }

    @Test
    public void testFindAllWithIterableParameter() {

        // given
        Pet seededPet = seedSomePet();
        List<String> petIds = Stream.of(seededPet).map(Pet::getId).collect(Collectors.toList());

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
        Pet somePet = getSomePet();
        Pet someOtherPet = getSomeOtherPet();

        seedPet(somePet);
        seedPet(someOtherPet);

        // when
        String nameSomePetRepository = petRepository.findOne(somePet.getId()).getName();
        String nameSomeOtherPetRepository = petRepository.findOne(someOtherPet.getId()).getName();

        // then
        assertThat(somePet.getName(), equalTo(nameSomePetRepository));
        assertThat(someOtherPet.getName(), equalTo(nameSomeOtherPetRepository));
    }

    /**
     * save
     */
    @Test
    public void saveWithIterableParameterTest() {

        // given
        Collection<Pet> pets = Arrays.asList(getSomeOtherPet(), getSomeOtherPet());

        // when
        Iterable<Pet> savedPets = petRepository.save(pets);

        // then
        savedPets.forEach(pet -> {
            assertThat(pet.getName().equals(getSomePet().getName()) || pet.getName().equals(getSomeOtherPet().getName()), is(true));
        });
    }
}
