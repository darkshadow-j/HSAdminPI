package hsadminbackapp.demo.services;

import hsadminbackapp.demo.jpa.RouterDAO;
import hsadminbackapp.demo.mikrotik.MikroTikService;
import hsadminbackapp.demo.models.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouterServiceImpl implements RouterService {

    RouterDAO routerDAO;
    MikroTikService mikroTikService;

    public RouterServiceImpl(RouterDAO routerDAO, MikroTikService mikroTikService) {
        this.routerDAO = routerDAO;
        this.mikroTikService = mikroTikService;
    }

    @Override
    public void addRouter(Router router) {
        mikroTikService.getRouterPorts(router);
        try {
            routerDAO.save(router);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Duplikacja");
        }


    }

    @Override
    public List<Router> getAllRouters() {
        return routerDAO.findAll();
    }

    @Override
    public Integer contRouters() {
        return routerDAO.findAll().size();
    }
}
