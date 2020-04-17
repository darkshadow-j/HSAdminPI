package hsadminbackapp.demo.controllers;


import hsadminbackapp.demo.jpa.HotSpotUslugaDAO;
import hsadminbackapp.demo.models.HotSpotUsluga;
import hsadminbackapp.demo.services.HotSpotUslugaService;
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

    @GetMapping
    public ResponseEntity<List<HotSpotUsluga>> getHotSpotUslugi(){
        return new ResponseEntity<>(hotSpotUslugaDAO.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addUsluga(@RequestBody HotSpotUsluga hotSpotUsluga){
        hotSpotUslugaService.addHotSpotUsluga(hotSpotUsluga);
        System.out.println("xx " + hotSpotUslugaDAO.findAll().get(0).getProfile());
        return new ResponseEntity(HttpStatus.OK);
    }

}
