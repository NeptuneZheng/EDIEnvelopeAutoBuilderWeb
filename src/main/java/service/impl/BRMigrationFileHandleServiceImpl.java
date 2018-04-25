package service.impl;

import dao.BRMigrationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.BRMigrationFileSystem;

import java.util.List;

@Service("BRMigrationFileHandleServiceImpl")
public class BRMigrationFileHandleServiceImpl {
    private final BRMigrationDao brMigrationDao;

    @Autowired
    public BRMigrationFileHandleServiceImpl(BRMigrationDao brDao) {
        this.brMigrationDao = brDao;
    }

    public boolean saveNewRecord(BRMigrationFileSystem br) {
       return brMigrationDao.saveNewRecord(br);
    }

    public BRMigrationFileSystem findOneBRDataRecord(String csBookingNum) {
        BRMigrationFileSystem br =null;
        try{
            br = brMigrationDao.findOneBRDataRecord(csBookingNum);
        }catch (Exception e){
            System.out.println("findOneBRDataRecord Exception");
        }
        return br;
    }

    public List<BRMigrationFileSystem> findDuplicateBRDateRecordByBKG(String csBookingNum) {
        return brMigrationDao.findDuplicateBRDateRecordByBKG(csBookingNum);
    }

    public List<BRMigrationFileSystem> findDuplicateBRDateRecordByDuplicateFlag(String flag) {
        return brMigrationDao.findDuplicateBRDateRecordByDuplicateFlag(flag);
    }

    public List<BRMigrationFileSystem> findDuplicateBRDateRecordByMatchFlag(String flag) {
        return brMigrationDao.findDuplicateBRDateRecordByMatchFlag(flag);
    }

    public List<BRMigrationFileSystem> findDuplicateBRDateRecordByDuplicateFlagAndMatchFlag(String d_flag, String m_flag) {
        return brMigrationDao.findDuplicateBRDateRecordByDuplicateFlagAndMatchFlag(d_flag,m_flag);
    }
}
