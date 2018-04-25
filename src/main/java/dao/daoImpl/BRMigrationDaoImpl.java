package dao.daoImpl;

import dao.BRMigrationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pojo.BRMigrationFileSystem;

import java.util.List;

@Repository("BRMigrationDaoImpl")
public class BRMigrationDaoImpl implements BRMigrationDao {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public BRMigrationDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public boolean saveNewRecord(BRMigrationFileSystem br) {
        try {
            mongoTemplate.save(br);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public BRMigrationFileSystem findAndRemove(int idx) {
        return mongoTemplate.findAndRemove(new Query(Criteria.where("_id").is(idx)),BRMigrationFileSystem.class);
    }

    public BRMigrationFileSystem findOneBRDataRecord(String csBookingNum) {
        BRMigrationFileSystem br =null;
        try{
            br = mongoTemplate.findOne(new Query(Criteria.where("csBookingRefNumber").is(csBookingNum)),BRMigrationFileSystem.class);
        }catch (Exception e){
            System.out.println("findOneBRDataRecord Exception");
        }
        return br;
    }

    public List<BRMigrationFileSystem> findDuplicateBRDateRecordByBKG(String csBookingNum) {
        return mongoTemplate.find(new Query(Criteria.where("csBookingRefNumber").is(csBookingNum)),BRMigrationFileSystem.class);
    }

    public List<BRMigrationFileSystem> findDuplicateBRDateRecordByDuplicateFlag(String flag) {
        return mongoTemplate.find(new Query(Criteria.where("Duplicate_flag").is(flag)),BRMigrationFileSystem.class);
    }

    public List<BRMigrationFileSystem> findDuplicateBRDateRecordByMatchFlag(String flag) {
        return mongoTemplate.find(new Query(Criteria.where("Match_flag").is(flag)),BRMigrationFileSystem.class);
    }

    public List<BRMigrationFileSystem> findDuplicateBRDateRecordByDuplicateFlagAndMatchFlag(String d_flag, String m_flag) {
        return mongoTemplate.find(new Query(Criteria.where("Duplicate_flag").is(d_flag).andOperator(Criteria.where("Match_flag").is(m_flag))),BRMigrationFileSystem.class);
    }

    public List<BRMigrationFileSystem> findMatchButNotDuplicateRecord(String dataVersion) {
        return mongoTemplate.find(new Query(Criteria.where("Duplicate_flag").is("N").andOperator(Criteria.where("Match_flag").is("YYY").andOperator(Criteria.where("Data_version").is(dataVersion)))),BRMigrationFileSystem.class);
    }
}
