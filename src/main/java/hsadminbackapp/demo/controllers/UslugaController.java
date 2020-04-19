package hsadminbackapp.demo.controllers;


import hsadminbackapp.demo.jpa.HotSpotUslugaDAO;
import hsadminbackapp.demo.jpa.RouterDAO;
import hsadminbackapp.demo.models.HotSpotUsluga;
import hsadminbackapp.demo.services.HotSpotUslugaService;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/usluga")
public class UslugaController {

    @Autowired
    HotSpotUslugaService hotSpotUslugaService;
    @Autowired
    HotSpotUslugaDAO hotSpotUslugaDAO;

    @Autowired
    RouterDAO routerDAO;

    @GetMapping
    public ResponseEntity<List<HotSpotUsluga>> getHotSpotUslugi(){
        return new ResponseEntity<>(hotSpotUslugaDAO.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addUsluga(@RequestBody HotSpotUsluga usluga){
        try {
            hotSpotUslugaService.addHotSpotUsluga(usluga);
        } catch (MikrotikApiException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
