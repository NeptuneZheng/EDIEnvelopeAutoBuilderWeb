package dao;

import pojo.IDCollection;

public interface IDCollectionDao {
    public Integer getLastMaxId(String iDType);
    public Integer updateMaxId(IDCollection idCollection);
    public void addNewType(IDCollection idCollection);
}
