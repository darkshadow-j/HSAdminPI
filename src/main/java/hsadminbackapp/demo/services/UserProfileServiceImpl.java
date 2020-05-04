package hsadminbackapp.demo.services;

import hsadminbackapp.demo.jpa.UserProfileDAO;
import hsadminbackapp.demo.mikrotik.MikroTikService;
import hsadminbackapp.demo.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserProfileServiceImpl implements UserProfileService {

    UserProfileDAO userProfileDAO;
    @Autowired
    MikroTikService mikroTikService;

    @Autowired
    public UserProfileServiceImpl(UserProfileDAO userProfileDAO) {
        this.userProfileDAO = userProfileDAO;
        UserProfile userProfile = new UserProfile();
        userProfile.setName("default");
          //  this.addUserProfile(userProfile);
    }

    @Override
    public void addUserProfile(UserProfile userProfile) {

        userProfileDAO.save(userProfile);
    }

    @Override
    public UserProfile getUserProfile() {
        return userProfileDAO.getByName("default");
    }

    @Override
    public void editUserProfile(UserProfile userProfile) {
        userProfileDAO.save(userProfile);
        this.mikroTikService.UpdateUserProfiles();
    }
}
