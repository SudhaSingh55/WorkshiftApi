package org.workshift.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.workshift.model.*;
import org.workshift.repository.ShiftRepository;
import org.workshift.repository.ShopRepository;
import org.workshift.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RequiredArgsConstructor
@Service
@Transactional
public class WorkShiftService {

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ShiftRepository shiftRepository;

    public static final String DATETIME_FORMAT = "yyyy-MM-dd['T']HH:mm:ss.SSSSSS";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    public String createUser(UserRequest request){
        User user = new User(request);
        user = userRepository.save(user);
        if(user !=null){
            return "User Created Successfully";
        }else{
            return "User not Created Successfully";
        }
    }

    public String createShop(ShopRequest request){
        Shop shop = new Shop(request);
        shop = shopRepository.save(shop);
        if(shop !=null){
            return "Shop Created Successfully";
        }else{
            return "Shop not Created Successfully";
        }
    }


    public String addUserToShop(ShopRequest request) {
        if(isBlank(request.getUserId()))
            return "User Id cannot be null";

        User user = userRepository.findById(request.getUserId()).orElse(null);
        if(user==null)
           return "Not a valid userId!!!";

        Shop shop = shopRepository.findByIdWithoutJoin(request.getShopId());
        if(shop == null){
            createShop(request);
        }else{
            shop.setUserId(request.getUserId());
           // shopRepository.save(shop);
            shopRepository.updateUserId(request.getUserId(), request.getShopId());
        }
       return "UserId added to shop successfully!!";
    }

    public String createShift(ShiftRequest request){
        Workshift workshift = new Workshift(request);

        workshift =  shiftRepository.save(workshift);

        if(workshift !=null){
            return "Shift Created Successfully";
        }else{
            return "Shift not Created Successfully";
        }
    }

    public String addUserByShopInShift(ShiftRequest request) {
        /** Validation ***/
       String validationResult = validateShiftRequest(request);
        if(validationResult!=null)
            return validationResult;
        /** Validation ***/

        Workshift workshift = shiftRepository.findById(request.getShiftId()).orElse(null);
        if(workshift==null){
            return "Shift Id is not created !!!!";
        }else{
            if(workshift.getStartTime()!=null && workshift.getEndTime()!=null)
                return "Already created shift cannot be updated !!!!";

            List<Workshift> workshiftList = shiftRepository.findByUserId(request.getUserId());
            String reason =  validateAddUserToShiftCases(workshiftList,request);
            if(CollectionUtils.isEmpty(workshiftList) || reason.equalsIgnoreCase("true")){
                if(request.getStartTime()!=null) {
                    workshift.setStartTime(LocalDateTime.parse(request.getStartTime(), dateTimeFormatter));
                }else{
                    workshift.setStartTime(LocalDateTime.now());
                }
                workshift.setEndTime(workshift.getStartTime().plusHours(request.getShiftDuration()));
                workshift.setShiftDuration(request.getShiftDuration());
                workshift.setUserId(request.getUserId());
                workshift.setShopId(request.getShopId());
                shiftRepository.save(workshift);
                return "User added successfully to shift for a shop";
            }else{
                return reason;
            }



        }
    }

    private String validateAddUserToShiftCases(List<Workshift> workshiftList, ShiftRequest request){
         boolean isUserToBeAddedInShop = true;
         String description = "true";
       // Map<String,List<Workshift>> workshiftByShopIdMap = workshiftList.stream().collect(groupingBy(Workshift::getShopId));
        List<Workshift> shiftListOfSameShop = workshiftList.stream().
                filter(work ->  work.getShopId()!=null && work.getShopId().equalsIgnoreCase(request.getShopId())).collect(Collectors.toList());

        LocalDateTime startTime = LocalDateTime.now();
        if(request.getStartTime()!=null){
            startTime = LocalDateTime.parse(request.getStartTime(), dateTimeFormatter);
        }
        LocalDateTime requestStartTime = startTime;

        if(!CollectionUtils.isEmpty(shiftListOfSameShop)){
            List<LocalDate> shiftStartTimeList = new ArrayList<>();

            //CASE1 : No user is allowed to work in the same shop for more than 8 hours, within a 24 hour window.
            for(Workshift shift: shiftListOfSameShop ){
                shiftStartTimeList.add(shift.getStartTime().toLocalDate());

                Duration duration = Duration.between(shift.getStartTime(),requestStartTime );
                if (duration.toHours() < 24) {
                    Duration actualDuration = Duration.between( shift.getStartTime(),shift.getEndTime());
                    if (actualDuration.toHours() >= 8) {
                       // isUserToBeAddedInShop = false;
                        description = "User is not allowed to work more than 8hour in same shop";
                        return description;
                    } else {
                        request.setShiftDuration((int) actualDuration.toHours());
                    }
                }
            }


            //CASE2 : No user can work more than 5 days in a row in the same shop.
            LocalDate currentDate = requestStartTime.toLocalDate();
            for(int i=1;i<=5;i++){
                if(!shiftStartTimeList.contains(currentDate.minusDays(i))){
                    break;
                }
                if(i==5){
                   // isUserToBeAddedInShop = false;
                    description = "User cannot work more than 5 days in same shop";
                    return description;
                }
            }
        }

        //CASE3 : A user can not work in multiple shops at the same time.

        List<Workshift> shiftResultForOtherShops = workshiftList.stream()
                .filter(work -> work.getEndTime()!=null)
                .filter(work -> requestStartTime.isBefore(work.getEndTime()))
                .collect(Collectors.toList());
      if(!CollectionUtils.isEmpty(shiftResultForOtherShops)){
         // isUserToBeAddedInShop = false;
          description = "User cannot work in multiple shop at the same time";
          return description;
      }

        return description;

    }

    private String validateShiftRequest(ShiftRequest request){
        if(isBlank(request.getUserId()))
            return "User Id cannot be null";

        if(isBlank(request.getShopId()))
            return "Shop Id cannot be null";

        if(request.getShiftDuration()>8)
            return "Shift Duration cannot be greater than 8 hours";

        User user = userRepository.findById(request.getUserId()).orElse(null);
        if(user==null)
            return "Not a valid user Id!!!";

        Shop shop = shopRepository.findByIdWithoutJoin(request.getShopId());
        if(shop==null)
            return "Not a valid shop Id!!!";

        return null;
    }
}
