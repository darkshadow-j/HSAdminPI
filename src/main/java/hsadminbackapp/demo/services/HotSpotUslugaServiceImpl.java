package hsadminbackapp.demo.services;

import hsadminbackapp.demo.jpa.HotSpotUslugaDAO;
import hsadminbackapp.demo.mikrotik.MikroTikService;
import hsadminbackapp.demo.models.HotSpotUsluga;
import hsadminbackapp.demo.models.IpPool;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class HotSpotUslugaServiceImpl implements HotSpotUslugaService {
    HotSpotUslugaDAO hotSpotUslugaDAO;

    MikroTikService mikroTikService;

    @Autowired
    public HotSpotUslugaServiceImpl(HotSpotUslugaDAO hotSpotUslugaDAO, MikroTikService mikroTikService) {
        this.hotSpotUslugaDAO = hotSpotUslugaDAO;
        this.mikroTikService = mikroTikService;
    }

    @Override
    public void addHotSpotUsluga(HotSpotUsluga hotSpotUsluga) throws MikrotikApiException {
        IpPool ipPool = hotSpotUsluga.getIpPool();
        Random rand = new Random();
        ipPool.setName(ipPool.getName()+rand.nextInt(9999)+1000);
        hotSpotUslugaDAO.save(hotSpotUsluga);
        mikroTikService.configurePort(hotSpotUsluga);
        mikroTikService.configurePool(hotSpotUsluga);
        mikroTikService.addService(hotSpotUsluga);
    }
}
