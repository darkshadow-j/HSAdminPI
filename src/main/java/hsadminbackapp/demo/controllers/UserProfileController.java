package hsadminbackapp.demo.controllers;

import hsadminbackapp.demo.models.HotSpotProfile;
import hsadminbackapp.demo.models.UserProfile;
import hsadminbackapp.demo.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/userprofile")
public class UserProfileController {

    private UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public ResponseEntity<UserProfile> getUserProfile(){
        return new ResponseEntity<>(userProfileService.getUserProfile(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity editUserProfile(@RequestBody UserProfile userProfile){
            userProfileService.editUserProfile(userProfile);
        return new ResponseEntity(HttpStatus.OK);
    }
}
