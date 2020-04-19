package hsadminbackapp.demo.mikrotik;

import hsadminbackapp.demo.jpa.HotSpotProfileDAO;
import hsadminbackapp.demo.jpa.RouterDAO;
import hsadminbackapp.demo.jpa.UserDao;
import hsadminbackapp.demo.jpa.UserProfileDAO;
import hsadminbackapp.demo.models.*;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MikroTikService {

    RouterDAO routerDAO;
    HotSpotProfileDAO hotSpotProfileDAO;
    UserProfileDAO userProfileDAO;
    UserDao userDao;

    public MikroTikService(RouterDAO routerDAO, HotSpotProfileDAO hotSpotProfileDAO, UserProfileDAO userProfileDAO, UserDao userDao) {
        this.routerDAO = routerDAO;
        this.hotSpotProfileDAO = hotSpotProfileDAO;
        this.userProfileDAO = userProfileDAO;
        this.userDao = userDao;
    }

    private static final String MIKROTIK_INTERFACE_CMD = "/interface/print";
    private static final String MIKROTIK_PROFILE_ADD = "/ip/hotspot/profile/add ";
    private static final String MIKROTIK_USER_ADD = "/ip/hotspot/user/add ";
    private static final String MIKROTIK_ADD_IP = "/ip/address/add interface=";
    private static final String MIKROTIK_ADD_IP_POOL = "/ip/pool/add name= ";
    private static final String MIKROTIK_HOTSPOT_ENABLE = "/ip/hotspot/enable .id=";

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

    @Async
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

    public void UpdateUserProfiles() {
        UserProfile userProfile = userProfileDAO.getByName("default");
        this.routerDAO.findAll().forEach(router -> {
            try {
                ApiConnection con = ApiConnection.connect(router.getIpAddress());
                con.login(router.getUsername(), router.getPassword());
                con.execute("/ip/hotspot/user/profile/set numbers=" + userProfile.getName() + " session-timeout=" + userProfile.getSessionTimeout());
            } catch (MikrotikApiException e) {
                e.printStackTrace();
            }
        });
    }

    public void UpdateHSProfileOnRouter(Router router) {
        List<HotSpotProfile> hotSpotProfiles = this.hotSpotProfileDAO.findAll();
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
    }

    @Async
    public void UpdateUsers() {
        List<User> users = this.userDao.findAll();
        this.routerDAO.findAll().forEach(router -> {
            try {
                ApiConnection con = ApiConnection.connect(router.getIpAddress());
                con.login(router.getUsername(), router.getPassword());
                users.forEach(user -> {
                    try {
                        con.execute(MIKROTIK_USER_ADD + "name=" + user.getUsername() + " password=" + user.getPassword());
                    } catch (MikrotikApiException e) {
                        if (e.getMessage().contains("failure: already have user with this name for this server")) {
                        }
                    }
                });

            } catch (MikrotikApiException e) {
                e.printStackTrace();
            }
        });
    }

    public void configurePort(HotSpotUsluga hotSpotUsluga) throws MikrotikApiException {
        Router router = routerDAO.getRouterByPortListIs(hotSpotUsluga.getPort());
        ApiConnection con = ApiConnection.connect(router.getIpAddress());
        con.login(router.getUsername(), router.getPassword());
        con.execute(MIKROTIK_ADD_IP + hotSpotUsluga.getPort().getName() + " address=" + hotSpotUsluga.getIpAdress());
        con.close();
    }

    public void configurePool(HotSpotUsluga hotSpotUsluga) throws MikrotikApiException {
        Router router = routerDAO.getRouterByPortListIs(hotSpotUsluga.getPort());
        ApiConnection con = ApiConnection.connect(router.getIpAddress());
        con.login(router.getUsername(), router.getPassword());
        con.execute(MIKROTIK_ADD_IP_POOL + hotSpotUsluga.getIpPool().getName() + " ranges=" + hotSpotUsluga.getIpPool().getIpPool());
        con.close();

    }

    public void addService(HotSpotUsluga hotSpotUsluga) throws MikrotikApiException {
        Router router = routerDAO.getRouterByPortListIs(hotSpotUsluga.getPort());
        ApiConnection con = ApiConnection.connect(router.getIpAddress());
        con.login(router.getUsername(), router.getPassword());
        con.execute("/ip/hotspot/add name=" + hotSpotUsluga.getName() + " interface=" + hotSpotUsluga.getPort().getName() + " profile=" + hotSpotUsluga.getProfile().getName() + "  address-pool=" + hotSpotUsluga.getIpPool().getName());
        con.execute(MIKROTIK_HOTSPOT_ENABLE + hotSpotUsluga.getName());
        con.close();
    }
}


