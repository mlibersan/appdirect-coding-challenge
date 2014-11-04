package com.martinlibersan.appdirect.codingchallenge.web.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.martinlibersan.appdirect.codingchallenge.web.model.Person;

@Service
public class PersonServiceImpl implements PersonService {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void addPerson(Person person) {
		em.persist(person);
	}

	@Transactional
	public void updatePerson(Person person) {
		em.merge(person);
	}

	@Transactional
	public List<Person> listPeople() {
		CriteriaQuery<Person> c = em.getCriteriaBuilder().createQuery(Person.class);
		c.from(Person.class);
		return em.createQuery(c).getResultList();
	}

	@Transactional
	public void removePerson(Integer id) {
		Person person = em.find(Person.class, id);
		if (null != person) {
			em.remove(person);
		}
	}

	@Transactional
	public Person findPersonById(Integer id) {
		Person person = em.find(Person.class, id);
		return person;
	}

	@Transactional
	public Person findPersonByOpenId(String openId) {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Person.class);
		Person person = (Person) criteria.add(Restrictions.eq("openId", openId)).uniqueResult();
		return person;
	}

}
