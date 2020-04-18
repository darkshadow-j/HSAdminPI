package hsadminbackapp.demo.network;

import hsadminbackapp.demo.models.Router;

public interface ReachableDevice {

    void checkRoutersAvailable();
    void RouterStateChange(Router router, NetworkState networkState);

}
