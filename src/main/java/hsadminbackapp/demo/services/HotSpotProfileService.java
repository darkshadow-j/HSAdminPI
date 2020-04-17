package hsadminbackapp.demo.services;

import hsadminbackapp.demo.models.HotSpotProfile;

import java.util.List;

public interface HotSpotProfileService {

    void addHotSpotProfile(HotSpotProfile hotSpotProfile);
    List<HotSpotProfile> getHotSpotProfileList();

}
