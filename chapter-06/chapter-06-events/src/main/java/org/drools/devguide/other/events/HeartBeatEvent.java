package org.drools.devguide.other.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("heartBeatTime")
@Expires("10m")
public class HeartBeatEvent implements Serializable {

    private Date heartBeatTime;
    

    public HeartBeatEvent() {
        super();
    }

    public Date getHeartBeatTime() {
        return heartBeatTime;
    }

    public void setHeartBeatTime(Date heartBeatTime) {
        this.heartBeatTime = heartBeatTime;
    }
}

