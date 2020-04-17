package hsadminbackapp.demo.mikrotik;

import hsadminbackapp.demo.models.HotSpotProfile;
import hsadminbackapp.demo.models.HotSpotUsluga;
import hsadminbackapp.demo.models.Port;
import hsadminbackapp.demo.models.Router;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MikroTikService {

    private static final String MIKROTIK_INTERFACE_CMD = "/interface/print";
    private static final String MIKROTIK_PROFILE_ADD = "/ip/hotspot/profile/add ";

    public Router getRouterPorts(Router router){
        List<Port> portList = new ArrayList<>();
        ApiConnection con = null;
        try {
            con = ApiConnection.connect(router.getIpAddress());
            con.login(router.getUsername(), router.getPassword());
            List<Map<String, String>> rs = con.execute(MIKROTIK_INTERFACE_CMD);
            rs.forEach(p->{
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
                if(e.getMessage().contains("failure: server profile with such name already exists")){
                    System.out.println("jest juz");
                }
            }
        });

    }
}
