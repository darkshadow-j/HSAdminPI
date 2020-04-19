package hsadminbackapp.demo.services;

import hsadminbackapp.demo.models.User;

import java.util.List;

public interface UserService {

    void addNewUser(User user);
    List<User> getUsersList();

}
