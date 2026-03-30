package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entry.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	public Optional<Person> findById(Long name);

    @Modifying
    @Query(value = "TRUNCATE TABLE people", nativeQuery = true)
    void truncateTable();
    
    public List<Person> findByNameLike(String name);
    public List<Person> findByIdIsNotNullOrderByIdDesc();
    public List<Person> findByAgeGreaterThan(Integer age);
    public List<Person> findByAgeBetween(Integer age1, Integer age2);
    
}