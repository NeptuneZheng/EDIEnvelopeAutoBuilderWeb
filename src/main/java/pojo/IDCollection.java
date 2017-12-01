package pojo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "IDCollection")
public class IDCollection {

    private String iDType;
    private Integer userId;

    public IDCollection() {
    }

    public IDCollection(String iDType, Integer userId) {
        this.iDType = iDType;
        this.userId = userId;
    }

    public String getIDType() {
        return iDType;
    }

    public void setIDType(String iDType) {
        this.iDType = iDType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
