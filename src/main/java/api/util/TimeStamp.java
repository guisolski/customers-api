package api.util;

import java.sql.Date;
import java.util.Calendar;

public class TimeStamp {
    private Date createdAt, updateAt;

    public TimeStamp(Date createat, Date updateat) {
        this.createdAt = createat;
        this.updateAt = updateat;
    }

    public TimeStamp() {

    }

    public Date getUpdateAt() {
        if (updateAt != null) return updateAt;
        //get current date
        Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        this.setUpdateAt(date);
        return date;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreatedAt() {
        if (createdAt != null) return createdAt;
        //get current date
        Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        this.setCreatedAt(date);
        return date;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
