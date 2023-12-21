Project has h2 in memory database,

1) Create User : Post
    url : http://localhost:8080/workshift/create-user
    Body : {
       "userId":1,
       "userName":"john",
       "address":"green-wood",
       "phoneNumber":1123456,
       "email":"john@test.com"
   }
2) Create Shop : Post
   Post Url : http://localhost:8080/workshift/create-shop
   {
   "shopId":102,
   "shopName":"shop1",
   "address":"green-wood"
   }
3) Add user to shop : Post
   Post Url : http://localhost:8080/workshift/add-user-shop
   {
   "shopId":"102",
   "shopName":"shop1",
   "address":"green-wood",
   "userId":"1"
   }
4) Create Shift : Post
   Post Url : http://localhost:8080/workshift/create-shift
   {
   "shiftId":"201",
   "shiftDescription":"dayshift"
   }
5) Add user to shift at a shop : Post
   Post Url : http://localhost:8080/workshift/add-user-shift-shop
   {
   "shiftId":"201",
   "userId":"1",
   "shopId":"102",
   "shiftDuration":"8",
   "startTime":"2023-12-21T12:03:56.235-0700"
   }
    Note :
    * Shift starttime and endtime is auto calculated by code in case not provided in request
      StartTime : CurrentTime
      EndTime : ccurrentTime + shiftDuration
    * ShiftDuration cannot be more than 8 hours
    * Basic validation of shop,user and shif id is handled by code
    * WorkShiftServiceTest - Has one test cases which handles all cases in a flow.
    * H2 Console url : http://localhost:8080/h2-console/
