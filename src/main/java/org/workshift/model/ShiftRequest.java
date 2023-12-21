package org.workshift.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ShiftRequest implements Serializable {
    private String shiftId;
    private String shiftDescription;
    private int shiftDuration;
    private String shopId;
    private String userId;
    private String startTime;
    private String endTime;
}
