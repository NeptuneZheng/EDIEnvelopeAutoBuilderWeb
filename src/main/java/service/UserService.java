package service;

import pojo.User;

import java.util.List;

public interface UserService {
    public User findOneUserByID(String id);
    public User findOneUserByName(String id);
    public User addOrUpdateUser(User user);
    public boolean removeUserByID(String id);
    public List<User> findAllUser();
}
