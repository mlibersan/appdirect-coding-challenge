package com.martinlibersan.appdirect.codingchallenge.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Integer id;

    private String openId;

    private String firstName;

    private String lastName;
    
    private String compagny;

	@Lob
	private byte[] xml;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public byte[] getXml() {
		return xml;
	}

	public void setXml(byte[] xml) {
		this.xml = xml;
	}

	public String getCompagny() {
		return compagny;
	}

	public void setCompagny(String compagny) {
		this.compagny = compagny;
	}    
	
	

}
