package hsadminbackapp.demo.controllers;

import hsadminbackapp.demo.jpa.UserDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/log")
public class LogsController {

    private UserDao userDao;

    public LogsController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping
    public ResponseEntity<String> getTelephoneByName(@RequestHeader("name") String name){
        return new ResponseEntity<>(userDao.getUserByUsername(name).getTelnum(), HttpStatus.OK);
    }


}
