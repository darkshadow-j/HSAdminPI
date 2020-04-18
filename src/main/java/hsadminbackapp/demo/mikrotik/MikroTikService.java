package hsadminbackapp.demo.mikrotik;

import hsadminbackapp.demo.jpa.HotSpotProfileDAO;
import hsadminbackapp.demo.jpa.RouterDAO;
import hsadminbackapp.demo.models.HotSpotProfile;
import hsadminbackapp.demo.models.HotSpotUsluga;
import hsadminbackapp.demo.models.Port;
import hsadminbackapp.demo.models.Router;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MikroTikService {

    RouterDAO routerDAO;
    HotSpotProfileDAO hotSpotProfileDAO;

    @Autowired
    public MikroTikService(RouterDAO routerDAO, HotSpotProfileDAO hotSpotProfileDAO) {
        this.routerDAO = routerDAO;
        this.hotSpotProfileDAO = hotSpotProfileDAO;
    }

    private static final String MIKROTIK_INTERFACE_CMD = "/interface/print";
    private static final String MIKROTIK_PROFILE_ADD = "/ip/hotspot/profile/add ";

    public Router getRouterPorts(Router router) {
        List<Port> portList = new ArrayList<>();
        ApiConnection con = null;
        try {
            con = ApiConnection.connect(router.getIpAddress());
            con.login(router.getUsername(), router.getPassword());
            List<Map<String, String>> rs = con.execute(MIKROTIK_INTERFACE_CMD);
            rs.forEach(p -> {
                portList.add(new Port(p.get("name")));
            });
            router.setPortList(portList);
        } catch (MikrotikApiException e) {
            System.out.println("Blad polaczenia z mikrotik");
        }
        return router;
    }

    public void addProfile(HotSpotProfile hotSpotProfile, List<Router> routers) {
        routers.forEach(router -> {
            ApiConnection con = null;
            try {
                con = ApiConnection.connect(router.getIpAddress());
                con.login(router.getUsername(), router.getPassword());
                con.execute(MIKROTIK_PROFILE_ADD + "name=" + hotSpotProfile.getName() + " rate-limit=" + hotSpotProfile.getRateLimit() + " dns-name=" + hotSpotProfile.getDnsName() + " use-radius=yes");
                con.close();
            } catch (MikrotikApiException e) {
                if (e.getMessage().contains("failure: server profile with such name already exists")) {
                    System.out.println("jest juz");
                }
            }
        });

    }

    public void UpdateHSProfiles() {
        List<HotSpotProfile> hotSpotProfiles = this.hotSpotProfileDAO.findAll();
        this.routerDAO.findAll().forEach(router -> {
            try {
                ApiConnection con = ApiConnection.connect(router.getIpAddress());
                con.login(router.getUsername(), router.getPassword());
                hotSpotProfiles.forEach(hotSpotProfile -> {
                    try {
                        con.execute(MIKROTIK_PROFILE_ADD + "name=" + hotSpotProfile.getName() + " rate-limit=" + hotSpotProfile.getRateLimit() + " dns-name=" + hotSpotProfile.getDnsName() + " use-radius=yes");
                    } catch (MikrotikApiException e) {
                        if (e.getMessage().contains("failure: server profile with such name already exists")) {
                            try {
                                con.execute("/ip/hotspot/profile/set numbers=" + hotSpotProfile.getName() + " rate-limit=" + hotSpotProfile.getRateLimit() + " dns-name=" + hotSpotProfile.getDnsName() + " use-radius=yes");
                            } catch (MikrotikApiException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });

            } catch (MikrotikApiException e) {
                e.printStackTrace();
            }

        });
    }
}
