package com.martinlibersan.appdirect.codingchallenge.web.service;


import java.util.List;

import com.martinlibersan.appdirect.codingchallenge.web.model.Person;

/**
 * Class responsible to manage user in database
 *
 */
public interface PersonService {
    
    public void addPerson(Person person);
	public void updatePerson(Person person);
    public List<Person> listPeople();
    public void removePerson(Integer id);
	public Person findPersonById(Integer id);
    public Person findPersonByOpenId(String openId);    
}
