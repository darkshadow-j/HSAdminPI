package hsadminbackapp.demo.services;

import hsadminbackapp.demo.models.User;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CodeService {

    private User tempUser;
    private int code;

    public CodeService() {
        Random rand = new Random();
        this.code = rand.nextInt(9999)+1000;
    }

    public boolean checkCode(int givenCone){
        return code==givenCone ? true : false;
    }

    public int getCode() {
        return code;
    }

    public void setTempUser(User tempUser) {
        this.tempUser = tempUser;
    }

    public User getTempUser() {
        return tempUser;
    }
}
