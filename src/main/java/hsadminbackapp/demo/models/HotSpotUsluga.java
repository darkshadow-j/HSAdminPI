package hsadminbackapp.demo.models;

import javax.persistence.*;

@Entity
public class HotSpotUsluga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(fetch = FetchType.EAGER)
    private HotSpotProfile profile;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Port port;


    public HotSpotUsluga() {
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

    public HotSpotProfile getProfile() {
        return profile;
    }

    public void setProfile(HotSpotProfile profile) {
        this.profile = profile;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "HotSpotUsluga{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profile=" + profile +
                ", port=" + port +
                '}';
    }
}
