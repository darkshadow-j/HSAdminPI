package hsadminbackapp.demo.network;

import hsadminbackapp.demo.mikrotik.MikroTikService;
import hsadminbackapp.demo.models.Router;
import hsadminbackapp.demo.services.MailService;
import hsadminbackapp.demo.services.RouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ReachableDeviceImpl implements ReachableDevice {

    RouterService routerService;
    MikroTikService mikroTikService;
    MailService mailService;

    @Autowired
    public ReachableDeviceImpl(RouterService routerService, MikroTikService mikroTikService, MailService mailService) {
        this.routerService = routerService;
        this.mikroTikService = mikroTikService;
        this.mailService = mailService;
    }

    @Scheduled(cron = "*/10 * * * * *")
    @Override
    public void checkRoutersAvailable() {
        routerService.getAllRouters().forEach(router -> {
            InetAddress inet = null;
            try {
                inet = InetAddress.getByName(router.getIpAddress());
            if (inet.isReachable(1000)) {
                    // avaiableStatisticDAO.save(new AvaiableStatistic(router, NetworkState.AVAILABLE, null));
                    this.RouterStateChange(router, NetworkState.AVAILABLE);
                } else {
                    //avaiableStatisticDAO.save(new AvaiableStatistic(router, NetworkState.UNAVAILABLE, new Timestamp(System.currentTimeMillis())));
                    this.RouterStateChange(router, NetworkState.UNAVAILABLE);
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void RouterStateChange(Router router, NetworkState networkState) {
        if (router.getNetworkState().equals(networkState)) {
        } else {
            router.setNetworkState(networkState);
            routerService.updateRouter(router);
            if(networkState.equals(NetworkState.AVAILABLE)){
                mikroTikService.UpdateHSProfileOnRouter(router);
                mikroTikService.UpdateUsers();
                mikroTikService.UpdateUserProfiles();
            }
            //mailService.sendEmail(subject, router.toString());
        }

    }
}
