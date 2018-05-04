package pojo;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "BRMigrationFileSystem")
public class BRMigrationFileSystem {

    @Id
    private Integer id;
    private String O_input_file_name;
    private String N_input_file_name;
    private String csBookingRefNumber;
    private String Prod_UIF_name;
    private String Prod_EDI_name;
    private String Data_version;
    private String Action_type;
    @CreatedDate
    @DateTimeFormat(style = "yyyy-MMdd-HH:mm:SS")
    private Date Create_date;
    @LastModifiedDate
    @DateTimeFormat(style = "yyyy-MMdd-HH:mm:SS")
    private Date Update_date;
    private String Duplicate_flag;
    private String Match_flag;

    public BRMigrationFileSystem() {
    }

    public BRMigrationFileSystem( String o_input_file_name, String n_input_file_name, String csBookingRefNumber, String prod_UIF_name, String prod_EDI_name, String data_version, String action_type, Date create_date, Date update_date, String duplicate_flag, String match_flag) {
        O_input_file_name = o_input_file_name;
        N_input_file_name = n_input_file_name;
        this.csBookingRefNumber = csBookingRefNumber;
        Prod_UIF_name = prod_UIF_name;
        Prod_EDI_name = prod_EDI_name;
        Data_version = data_version;
        Action_type = action_type;
        Create_date = create_date;
        Update_date = update_date;
        Duplicate_flag = duplicate_flag;
        Match_flag = match_flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getO_input_file_name() {
        return O_input_file_name;
    }

    public void setO_input_file_name(String o_input_file_name) {
        O_input_file_name = o_input_file_name;
    }

    public String getN_input_file_name() {
        return N_input_file_name;
    }

    public void setN_input_file_name(String n_input_file_name) {
        N_input_file_name = n_input_file_name;
    }

    public String getcsBookingRefNumber() {
        return csBookingRefNumber;
    }

    public void setcsBookingRefNumber(String csBookingRefNumber) {
        this.csBookingRefNumber = csBookingRefNumber;
    }

    public String getProd_UIF_name() {
        return Prod_UIF_name;
    }

    public void setProd_UIF_name(String prod_UIF_name) {
        Prod_UIF_name = prod_UIF_name;
    }

    public String getProd_EDI_name() {
        return Prod_EDI_name;
    }

    public void setProd_EDI_name(String prod_EDI_name) {
        Prod_EDI_name = prod_EDI_name;
    }

    public String getData_version() {
        return Data_version;
    }

    public void setData_version(String data_version) {
        Data_version = data_version;
    }

    public String getAction_type() {
        return Action_type;
    }

    public void setAction_type(String action_type) {
        Action_type = action_type;
    }

    public Date getCreate_date() {
        return Create_date;
    }

    public void setCreate_date(Date create_date) {
        Create_date = create_date;
    }

    public Date getUpdate_date() {
        return Update_date;
    }

    public void setUpdate_date(Date update_date) {
        Update_date = update_date;
    }

    public String getDuplicate_flag() {
        return Duplicate_flag;
    }

    public void setDuplicate_flag(String duplicate_flag) {
        Duplicate_flag = duplicate_flag;
    }

    public String getMatch_flag() {
        return Match_flag;
    }

    public void setMatch_flag(String match_flag) {
        Match_flag = match_flag;
    }
}
