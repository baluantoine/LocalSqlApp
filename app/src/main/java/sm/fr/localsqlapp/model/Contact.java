package sm.fr.localsqlapp.model;

/**
 * Created by Formation on 10/01/2018.
 */

public class Contact {
    private String name;
    private String firstname;
    private String mail;
    private Long id;

    public Contact() {

    }

    public Contact(String name, String firstname, String mail) {
        this.name = name;
        this.firstname = firstname;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public Contact setName(String name) {
        this.name = name;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public Contact setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public Contact setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Contact setId(Long id) {
        this.id = id;
        return this;
    }
}
