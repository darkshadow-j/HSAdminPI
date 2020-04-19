package hsadminbackapp.demo.services;

import hsadminbackapp.demo.jpa.UserDao;
import hsadminbackapp.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private  UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addNewUser(User user) {
        userDao.save(user);
    }
}
