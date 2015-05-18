package by.academy.it.domain.peopleentity;

import java.io.Serializable;

/**
 * Created by alexanderleonovich on 17.05.15.
 * Cущность человек. Имеются поля Имя и фамилия. Человек может быть либо работником (employee) либо
 * студентом (student) либо еще кем-то на усмотрение разработчика
 */
public class People implements Serializable{

    private Integer id;
    private String name;
    private String surname;

    public People() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof People)) return false;

        People people = (People) obj;

        if (id != null ? !id.equals(people.id) : people.id != null) return false;
        if (name != null ? !name.equals(people.name) : people.name != null) return false;
        if (surname != null ? !surname.equals(people.surname) : people.surname != null) return false;
        return true;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
