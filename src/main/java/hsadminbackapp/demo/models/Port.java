package hsadminbackapp.demo.models;

import javax.persistence.*;

@Entity
public class Port {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(fetch = FetchType.EAGER,  mappedBy = "port", cascade = CascadeType.PERSIST)
    private HotSpotUsluga hotSpotUsluga;


    public Port() {
    }


    public Port(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
