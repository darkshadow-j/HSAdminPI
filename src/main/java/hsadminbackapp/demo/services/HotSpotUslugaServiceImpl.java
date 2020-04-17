package hsadminbackapp.demo.services;

import hsadminbackapp.demo.jpa.HotSpotUslugaDAO;
import hsadminbackapp.demo.models.HotSpotUsluga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotSpotUslugaServiceImpl implements HotSpotUslugaService {
    HotSpotUslugaDAO hotSpotUslugaDAO;

    @Autowired
    public HotSpotUslugaServiceImpl(HotSpotUslugaDAO hotSpotUslugaDAO) {
        this.hotSpotUslugaDAO = hotSpotUslugaDAO;
    }

    @Override
    public void addHotSpotUsluga(HotSpotUsluga hotSpotUsluga) {
        hotSpotUslugaDAO.save(hotSpotUsluga);
    }
}
