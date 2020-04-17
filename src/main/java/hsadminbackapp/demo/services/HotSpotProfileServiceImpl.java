package hsadminbackapp.demo.services;

import hsadminbackapp.demo.jpa.HotSpotProfileDAO;
import hsadminbackapp.demo.mikrotik.MikroTikService;
import hsadminbackapp.demo.models.HotSpotProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotSpotProfileServiceImpl implements HotSpotProfileService {

    HotSpotProfileDAO hotSpotProfileDAO;
    MikroTikService mikroTikService;
    @Autowired
    RouterService routerService;

    @Autowired
    public HotSpotProfileServiceImpl(HotSpotProfileDAO hotSpotProfileDAO, MikroTikService mikroTikService) {
        this.hotSpotProfileDAO = hotSpotProfileDAO;
        this.mikroTikService = mikroTikService;
    }

    @Override
    public void addHotSpotProfile(HotSpotProfile hotSpotProfile) {
        mikroTikService.addProfile(hotSpotProfile, routerService.getAllRouters());
        try{
        hotSpotProfileDAO.save(hotSpotProfile);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Duplikacja");
        }
    }

    @Override
    public List<HotSpotProfile> getHotSpotProfileList() {
        return hotSpotProfileDAO.findAll();
    }
}
