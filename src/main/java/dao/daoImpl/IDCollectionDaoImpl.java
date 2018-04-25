package dao.daoImpl;

import dao.IDCollectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import pojo.IDCollection;

@Repository("IDCollectionDaoImpl")
public class IDCollectionDaoImpl implements IDCollectionDao {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public IDCollectionDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Integer getLastMaxId(String iDType) {
        return mongoTemplate.findOne(new Query(Criteria.where("iDType").is(iDType)), IDCollection.class).getUserId();
    }

    public Integer updateMaxId(IDCollection idCollection) {
         mongoTemplate.updateFirst(new Query(Criteria.where("iDType").is(idCollection.getIDType())), Update.update("userId",idCollection.getUserId()), IDCollection.class);
         return getLastMaxId(idCollection.getIDType());
    }

    public void addNewType(IDCollection idCollection) {
        mongoTemplate.save(idCollection);
    }
}
