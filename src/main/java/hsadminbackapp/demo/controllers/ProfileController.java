package hsadminbackapp.demo.controllers;

import hsadminbackapp.demo.jpa.RouterDAO;
import hsadminbackapp.demo.mikrotik.MikroTikService;
import hsadminbackapp.demo.models.HotSpotProfile;
import hsadminbackapp.demo.network.NetworkState;
import hsadminbackapp.demo.services.HotSpotProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private HotSpotProfileService hotSpotProfileService;
    @Autowired
    private RouterDAO routerDAO;

    @Autowired
    public ProfileController(HotSpotProfileService hotSpotProfileService) {
        this.hotSpotProfileService = hotSpotProfileService;
    }

    @GetMapping
    public ResponseEntity<List<HotSpotProfile>> getHotSpotProfileList() {
        return new ResponseEntity<>(hotSpotProfileService.getHotSpotProfileList(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addHotSpotProfile(@RequestBody HotSpotProfile hotSpotProfile) {
        hotSpotProfileService.addHotSpotProfile(hotSpotProfile);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity editHotSpotProfile(@RequestBody HotSpotProfile hotSpotProfile) {
        hotSpotProfileService.editHotSpotProfile(hotSpotProfile);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteHotSpotProfile(@RequestBody HotSpotProfile hotSpotProfile) {
        hotSpotProfileService.deleteHotSpotProfile(hotSpotProfile);
        return new ResponseEntity(HttpStatus.OK);
    }
}
