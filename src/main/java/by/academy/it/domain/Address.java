package by.academy.it.domain;

import java.io.Serializable;

/**
 * Created by alexanderleonovich on 16.05.15.
 */
public class Address implements Serializable {

    private Integer personId;
    private String city;
    private String street;
    private Integer building;
    private Person person;

    public Address() {
    }

    public Address(String city, String street, Integer building) {
        this.city = city;
        this.street = street;
        this.building = building;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getPersonId() {
        return this.personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(Integer building) {
        this.building = building;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        result = 31 * result + (person != null ? person.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Address)) return false;

        Address address = (Address) obj;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (personId != null ? !personId.equals(address.personId) : address.personId != null) return false;
        if (person != null ? !person.equals(address.person) : address.person != null) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Address{" +
                "personId=" + personId +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", building=" + building +
                '}';
    }
}
