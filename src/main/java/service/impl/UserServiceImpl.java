package service.impl;

import dao.UserDBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.User;
import service.UserService;

import java.util.List;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {
    private final UserDBDao userDBDao;

    @Autowired
    public UserServiceImpl(UserDBDao userDBDao) {
        this.userDBDao = userDBDao;
    }

    public User findOneUserByID(String id) {
        return userDBDao.findOneUserByID(id);
    }

    public User findOneUserByName(String name) {
        return userDBDao.findOneUserByName(name);
    }

    public User addOrUpdateUser(User user) {
        return userDBDao.addOrUpdateUser(user);
    }

    public boolean removeUserByID(String id) {
        return userDBDao.removeUserByID(id);
    }

    public List<User> findAllUser() {
        return userDBDao.findAllUser();
    }
}
