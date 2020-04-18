package hsadminbackapp.demo.services;

import hsadminbackapp.demo.models.Router;
import org.springframework.stereotype.Service;
import org.springframework.util.RouteMatcher;

import java.util.List;

public interface RouterService {

    List<Router> getUnavailableRouters();
    void updateRouter(Router router);
    void addRouter(Router router);
    List<Router> getAllRouters();
    Integer contRouters();
}
