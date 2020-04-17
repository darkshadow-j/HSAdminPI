package hsadminbackapp.demo.services;

import hsadminbackapp.demo.models.Router;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RouterService {

    void addRouter(Router router);
    List<Router> getAllRouters();
    Integer contRouters();
}
