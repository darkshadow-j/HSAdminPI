package hsadminbackapp.demo.services;

import hsadminbackapp.demo.models.UserProfile;

public interface UserProfileService {

    void addUserProfile(UserProfile userProfile);
    UserProfile getUserProfile();
    void editUserProfile(UserProfile userProfile);
}
