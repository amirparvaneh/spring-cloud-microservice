package com.cloud.deposit.constant;

public class ErrorMessage {

    public static final String ERROR_DEPOSIT_CREATION = "deposit assigning failed! ";
    public static final String ERROR_NATIONAL_CODE_NEEDED = "at least one national code needed for assigning deposit! ";
    public static final String ERROR_DEPOSIT_NUMEBR_MANDATORY = "the deposit number for transfering is mandatory!";
    public static final String ERROR_MINIMUM_TRANSFER_AMOUNT = "100 is the minimum of money transfer!";
    public static final String ERROR_MINUMUM_BALANCE_OPENING = "1000 is the minumum balance amount to create deposit.";
    public static final String ERROR_OPEN_DEPOSIT = "deposit with open status can't be deleted.";
    public static final String ERROR_MANDATORY = "mandatory field to create deposit.";
    public static final String ERROR_NO_DEPOSIT = "no deposit found with this deposit number : ";
    public static final String ERROR_CUSTOMER_NATIONAL_CODE = "there is no response from customer microservice with this " +
            "national code : ";
}
