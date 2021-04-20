package db.manager;


import java.util.ArrayList;
import java.util.List;

public class DB {

    public static List<Person> addPerson(String code, List<Person> people){
        List<Person> newPeople = new ArrayList<>();
        for(Person person : people){
            if(person.getCodice().equals(code)){
               return null;
            }
            newPeople.add(person);
        }
        newPeople.add(new Person(code, Status.NOTREADY));
        return newPeople;
    }
    public static List<Person> setReady(String code, List<Person> people){
        List<Person> newPeople = new ArrayList<>();
        for(Person person : people){
            if(person.getCodice().equals(code)){
                person.setStatus(Status.READY);
            }
            newPeople.add(person);
        }
        return newPeople;
    }
    public static List<Person> setNotReady(String code, List<Person> people){
        List<Person> newPeople = new ArrayList<>();
        for(Person person : people){
            if(person.getCodice().equals(code)){
                person.setStatus(Status.NOTREADY);
            }
            newPeople.add(person);
        }
        return newPeople;
    }
    public static Status getStatus(String code, List<Person> people){
        for(Person person : people){
            if(person.getCodice().equals(code)){
                return person.getStatus();
            }
        }
        return null;
    }
}
