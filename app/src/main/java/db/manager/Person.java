package db.manager;

public class Person {
    private String codice;
    private Status status;

    public Person(){}
    public Person(String codice, Status status){
        this.codice = codice;
        this.status = status;
    }
    public String getCodice(){
        return this.codice;
    }
    public Status getStatus(){
        return this.status;
    }
    public void setCodice(String codice){
        this.codice = codice;
    }
    public void setStatus(Status status){
        this.status = status;
    }
}
