/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import java.util.Collection;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Repository class for <code>Vet</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface VetRepository extends Repository<Vet, Integer> {

    /**
     * Retrieve all <code>Vet</code>s from the data store.
     *
     * @return a <code>Collection</code> of <code>Vet</code>s
     */
    @Transactional(readOnly = true)
    @Cacheable("vets")
    Collection<Vet> findAll() throws DataAccessException;


    /**
     * Retrieve all {@link Specialty}s from the data store.
     * @return a Collection of {@link Specialty}s.
     */
    @Query("SELECT pSpecialty FROM Specialty pSpecialty ORDER BY pSpecialty.name")
    @Transactional(readOnly = true)
    Collection<Specialty> findSpecialties();

    /**
     * Save an {@link Vet} to the data store, either inserting or updating it.
     * @param vet the {@link Vet} to save
     */
    @CacheEvict(value = "vets" , allEntries = true)
    void save(Vet vet);

    /**
     * Retrieve {@link Vet}s from the data store by last name, returning all owners
     * whose last name <i>starts</i> with the given name.
     * @param lastName Value to search for
     * @return a Collection of matching {@link Vet}s (or an empty Collection if none
     * found)
     */
    @Query("SELECT DISTINCT vet FROM Vet vet left join fetch vet.specialties WHERE vet.lastName LIKE :lastName%")
    @Transactional(readOnly = true)
    Collection<Vet> findByLastName(@Param("lastName") String lastName);
}
