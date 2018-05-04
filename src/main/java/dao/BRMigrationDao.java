package dao;

import pojo.BRMigrationFileSystem;

import java.util.List;

public interface BRMigrationDao {
    public boolean saveNewRecord(BRMigrationFileSystem br);
    public BRMigrationFileSystem findAndRemove(int idx);
    public BRMigrationFileSystem findOneBRDataRecordBycsBookingNum(String csBookingNum);
    public BRMigrationFileSystem findOneBRDataRecordBycsBookingNumAndActionType(String csBookingNum, String actionType);
    public List<BRMigrationFileSystem> findDuplicateBRDateRecordByBKG(String csBookingNum);
    public List<BRMigrationFileSystem> findDuplicateBRDateRecordByDuplicateFlag(String flag);
    public List<BRMigrationFileSystem> findDuplicateBRDateRecordByMatchFlag(String flag);
    public List<BRMigrationFileSystem> findDuplicateBRDateRecordByDuplicateFlagAndMatchFlag(String d_flag,String m_flag);
    public List<BRMigrationFileSystem> findMatchButNotDuplicateRecord(String dataVersion);
}
