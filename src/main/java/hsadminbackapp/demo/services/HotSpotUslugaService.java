package hsadminbackapp.demo.services;

import hsadminbackapp.demo.models.HotSpotUsluga;
import me.legrange.mikrotik.MikrotikApiException;

public interface HotSpotUslugaService {

    void addHotSpotUsluga(HotSpotUsluga hotSpotUsluga) throws MikrotikApiException;
}
