package dao.daoImpl;

import dao.UserDBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import pojo.IDCollection;
import pojo.User;

import java.util.List;

@Repository("UserDaoImpl")
public class UserDaoImpl implements UserDBDao {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User findOneUserByID(String id) {
        return mongoTemplate.findById(id,User.class);
    }

    public User findOneUserByName(String name) {
        return mongoTemplate.findOne(new Query(Criteria.where("userName").is(name)),User.class);
    }

    public User addOrUpdateUser(User user) {
        if(findOneUserByName(user.getUserName()) == null){
            IDCollection idCollection = mongoTemplate.findOne(new Query(Criteria.where("iDType").is("UserID")), IDCollection.class);
            Integer newID = idCollection.getUserId()+1;

            user.setUserID(String.valueOf(newID));
            mongoTemplate.updateFirst(new Query(Criteria.where("iDType").is("UserID")),new Update().set("userId",newID),IDCollection.class);
        }
        mongoTemplate.save(user);
        return findOneUserByID(user.getUserID());
    }

    public boolean removeUserByID(String id) {
        mongoTemplate.remove(findOneUserByID(id));
        return true;
    }

    public List<User> findAllUser() {
        return mongoTemplate.findAll(User.class);
    }
}
