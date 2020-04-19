package hsadminbackapp.demo.services;

import hsadminbackapp.demo.jpa.UserDao;
import hsadminbackapp.demo.mikrotik.MikroTikService;
import hsadminbackapp.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private MikroTikService mikroTikService;
    private  UserDao userDao;

    @Autowired
    public UserServiceImpl(MikroTikService mikroTikService, UserDao userDao) {
        this.mikroTikService = mikroTikService;
        this.userDao = userDao;
    }

    @Override
    public void addNewUser(User user) {
        userDao.save(user);
        mikroTikService.UpdateUsers();
    }

    @Override
    public List<User> getUsersList() {
        return userDao.findAll();
    }
}
