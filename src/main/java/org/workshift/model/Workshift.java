package org.workshift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "SHIFT")
@Data
@RequiredArgsConstructor
public class Workshift implements Serializable {
    @Id
    @Column(name = "SHIFT_ID")
    private String shiftId;
    @Column(name = "SHIFT_DESC")
    private String shiftDescription;
    @Column(name = "SHIFT_DURATION")
    private int shiftDuration;
    @Column(name = "SHOP_ID")
    private String shopId;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "START_TIME")
    private LocalDateTime startTime;
    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    public Workshift(ShiftRequest request){
        this.shiftId = request.getShiftId();
        this.shiftDescription = request.getShiftDescription();
        this.shopId = request.getShopId();
        this.userId = request.getUserId();
        this.shiftDuration = request.getShiftDuration();
//        this.startTime = request.getStartTime();
//        this.endTime = request.getEndTime();
    }
}
