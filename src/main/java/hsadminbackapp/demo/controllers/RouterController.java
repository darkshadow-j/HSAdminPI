package hsadminbackapp.demo.controllers;

import hsadminbackapp.demo.models.Router;
import hsadminbackapp.demo.services.RouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/router")
public class RouterController {


    private RouterService routerService;

    @Autowired
    public RouterController(RouterService routerService) {
        this.routerService = routerService;
    }


    @PostMapping
    public ResponseEntity addRouter(@RequestBody Router router) {
        routerService.addRouter(router);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Router>> getRouters(){
        return new ResponseEntity(routerService.getAllRouters(), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getRoutersCount(){
        return new ResponseEntity<>(routerService.contRouters(), HttpStatus.OK);
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<Router>> getUnavailableRouters(){
        return new ResponseEntity<>(routerService.getUnavailableRouters(), HttpStatus.OK);
    }

}
