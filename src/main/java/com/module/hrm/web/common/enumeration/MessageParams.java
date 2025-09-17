package com.module.hrm.web.common.enumeration;

public enum MessageParams {
    // user key
    user("user"),
    userCode("userCode"),
    group("group"),
    groupCode("groupCode"),
    role("role"),
    roleCode("roleCode"),
    login("login"),
    username("username"),
    password("password"),
    email("email"),
    employeeCode("employeeCode"),
    id("id"),
    ids("ids"),

    details("details"),

    deviceLog("deviceLog"),
    fileUpload("fileUpload"),
    originalName("originalName"),
    keyName("keyName"),
    entryDatetime("entryDatetime"),

    // common code
    outletCareType("outletCareType"),
    visitRemoteType("visitRemoteType"),
    inventoryEntryType("inventoryEntryType"),
    inventoryReasonType("inventoryReasonType"),
    distributorView("distributorView"),
    materialType("materialType"),
    materialGroup("materialGroup"),
    productUom("productUom"),
    catalogType("catalogType"),
    detailGroup("detailGroup"),
    promotionType("promotionType"),
    promotionLevel("promotionLevel"),
    promotionResult("promotionResult"),
    loyaltyType("loyaltyType"),
    loyaltyLevel("loyaltyLevel"),
    loyaltyResult("loyaltyResult"),
    image("image"),
    costDetail("costDetail"),
    invoice("invoice"),
    value("value"),
    promotionGroup("promotionGroup"),
    applyRegion("applyRegion"),
    applyDetail("applyDetail"),

    promotionDetail("promotionDetail"),
    loyaltyDetail("loyaltyDetail"),

    // search key
    query("query"),

    // object key
    customer("customer"),
    customerGps("customerGps"),
    customerCode("customerCode"),
    customerName("customerName"),
    customerShortName("customerShortName"),
    customerSizeType("customerSizeType"),
    customerSizeType2("customerSizeType2"),
    businessType("businessType"),

    customerOnRoute("customerOnRoute"),
    customerLoyalty("customerLoyalty"),
    customerImage("customerImage"),
    loyaltyImage("loyaltyImage"),
    reactionIcon("reactionIcon"),

    customerModify("customerModify"),
    recordType("recordType"),

    distributor("distributor"),
    distributorCode("distributorCode"),
    distributorName("distributorName"),
    distributorTruck("distributorTruck"),
    vehicleNo("vehicleNo"),

    openDate("openDate"),
    openDateFrom("openDateFrom"),
    openDateTo("openDateFrom"),
    activeDateFrom("activeDateFrom"),
    activeDateTo("activeDateTo"),

    latitude("latitude"),
    longitude("longitude"),
    phoneNo("phoneNo"),
    taxNo("taxNo"),
    address("address"),
    address2("address2"),
    contactPerson("contactPerson"),
    representativePerson("representativePerson"),
    identifyCardNo("identifyCardNo"),
    birthday("birthday"),

    subTradeChannelCode("subTradeChannelCode"),
    subTradeChannel("subTradeChannel"),
    tradeChannel("tradeChannel"),
    channelCategory("channelCategory"),

    sequence("sequence"),

    routeDetail("routeDetail"),
    routeFromDate("routeFromDate"),
    routeToDate("routeToDate"),

    salesRoute("salesRoute"),
    salesRouteCode("salesRouteCode"),
    salesRouteName("salesRouteName"),
    validFromDate("validFromDate"),

    salesPerson("salesPerson"),
    salesPersonCode("salesPersonCode"),
    salesPersonName("salesPersonName"),

    salesTeam("salesTeam"),
    salesHierarchy("salesHierarchy"),
    salesDistributorHierarchy("salesDistributorHierarchy"),
    salesUserHierarchy("salesUserHierarchy"),

    mcp("mcp"),
    visitDate("visitDate"),
    visitPattern("visitPattern"),

    product("product"),
    productPrice("productPrice"),
    catalog("catalog"),

    hierarchyL01("hierarchyL01"),
    hierarchyL02("hierarchyL02"),
    hierarchyL03("hierarchyL03"),
    hierarchyL04("hierarchyL04"),
    hierarchyL05("hierarchyL05"),
    hierarchyL06("hierarchyL06"),
    hierarchyL07("hierarchyL07"),
    hierarchyL08("hierarchyL08"),
    hierarchyL09("hierarchyL09"),

    salesUom("salesUom"),
    secondSalesUom("secondSalesUom"),

    inventoryJournal("inventoryJournal"),

    salesOut("salesOut"),
    coupon("coupon"),
    orderDate("orderDate"),
    deliveryNote("deliveryNote"),
    recordDate("recordDate"),
    lineNo("lineNo"),

    program("program"),
    programSettlement("programSettlement"),
    invoiceCode("invoiceCode"),

    purchaseDate("purchaseDate"),
    invoiceDate("invoiceDate"),
    dueDate("dueDate"),

    promotion("promotion"),
    loyaltyEarning("loyaltyEarning"),
    loyaltyCode("loyaltyCode"),
    //merchandising("merchandising"),

    // status
    activated("activated"),
    inActivated("inActivated"),
    routeAssigned("routeAssigned"),
    status("status"),
    statusOpen("status.open"),
    statusConfirmed("status.confirmed"),
    statusApproval1("status.approval1"),
    statusApproval("status.approval"),
    statusStarted("status.started"),
    statusInRunning("status.inRunning"),
    statusRejected("status.rejected"),
    statusClosed("status.closed"),
    statusExpired("status.expired"),
    statusRecorded("status.recorded"),
    statusCancel("status.cancel"),

    // master key
    regionCode("regionCode"),
    regionName("regionName"),
    provinceCode("provinceCode"),
    provinceName("provinceName"),
    districtCode("districtCode"),
    districtName("districtName"),
    wardCode("wardCode"),
    wardName("wardName"),

    day("day"),
    weekNo("weekNo"),

    // survey
    versionName("versionName"),
    description("description"),
    note("note"),

    visitType("visitType"),
    //visitRemoteType("visitRemoteType"),
    //careType("careType"),

    surveyVersion("surveyVersion"),
    surveySetCode("surveySetCode"),
    surveySet("surveySet"),
    surveySetType("surveySetType"),
    questionNo("questionNo"),
    question("question"),
    questionType("questionType"),
    answerRequiredFlag("answerRequiredFlag"),
    photoFlag("photoFlag"),
    photoNumMin("photoNumMin"),
    option1("option1"),
    option2("option2"),
    option3("option3"),
    option4("option4"),
    option5("option5"),
    correctOption("correctOption"),
    fromDate("fromDate"),
    toDate("toDate"),
    checkInTime("checkInTime"),
    checkOutTime("checkOutTime"),
    surveyTime("surveyTime"),
    orderTime("orderTime"),
    entryDate("entryDate"),
    entryDateFrom("entryDateFrom"),
    entryDateTo("entryDateTo"),
    registerFromDate("registerFromDate"),
    registerToDate("registerToDate"),
    paidFromDate("paidFromDate"),
    paidToDate("paidToDate"),
    maxRate("maxRate"),

    //coaching
    coaching("coaching"),
    topic("topic"),
    salesman("salesman"),

    // DisplayEntry
    quantity("quantity"),

    // purchase
    purchase("purchase"),
    purchaseInvoice("purchaseInvoice"),

    // coaching
    coachingType("coachingType"),
    topicType("topicType"),
    topicDetailType("topicDetailType"),

    bodyNotification("bodyNotification"),
    imageNotification("imageNotification"),

    locationDisplay("locationDisplay"),

    notificationCode("notificationCode"),
    imageTop("imageTop"),
    imageBottom("imageBottom"),

    period("period"),
    supermarketCode("supermarketCode"),
    usmData("usmData"),

    coachingDate("coachingDate"),
    coachingNote("coachingNote"),
    salesTarget("salesTarget"),
    targetPercentile("targetPercentile"),

    targetAso("targetAso"),

    // target
    targetCode("targetCode"),
    targetType("targetType"),
    parentCode("parentCode"),
    seasoning("seasoning"),
    beverage("beverage"),
    instant("instant"),

    code("code"),
    total("total"),
    cycle("cycle"),

    skill1("skill1"),
    skill2("skill2"),
    distance("distance"),
    workAddress("workAddress"),

    versionCode("versionCode"),

    imageHashTag("imageHashTag"),
    applyType("applyType"),
    sampling("sampling"),

    vanSales("vanSales"),

    vanSalesNote("vanSalesNote"),

    vanSalesSettlement("vanSalesSettlement"),
    samplingNote("samplingNote"),
    samplingSettlement("samplingSettlement"),

    // Fieldwork note
    fieldWorkNote("fieldWorkNote"),

    customerNoteTopicType("customerNoteTopicType"),
    distirbutorNoteTopicType("customerNoteTopicType"),
    salesManNoteTopicType("customerNoteTopicType"),

    secondQty("secondQty"),

    // z retailer
    zaloId("zaloId"),
    zaloAccessToken("zaloAccessToken"),

    luckyDraw("luckyDraw"),

    luckyDrawCode("luckyDrawCode"),

    luckyDrawReward("luckyDrawReward"),

    qrCode("qrCode"),
    imageFontCard("imageFontCard"),
    imageBackCard("imageBackCard"),

    timekeeping("timekeeping"),

    // expenseRequest
    expenseRequest("expenseRequest"),

    approvalConfig("approvalConfig"),

    approval("approval"),

    lidInfo("lidInfo"),

    coolerCode("coolerCode"),

    budget("budget"),

    claimProgram("claimProgram"),

    shipToCode("shipToCode"),
    productBarcode("productBarcode"),

    paymentNote("paymentNote");

    /**
     * The message id.
     */
    private final String messageId;

    MessageParams(String messageId) {
        this.messageId = messageId;
    }

    public String getValue() {
        return messageId;
    }
}
