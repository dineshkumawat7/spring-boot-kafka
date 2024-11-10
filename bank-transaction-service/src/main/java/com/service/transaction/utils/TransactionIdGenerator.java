package com.service.transaction.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TransactionIdGenerator {
    private static final Random random = new Random();

    public static String generateTransactionId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String dateTime = dateFormat.format(new Date());
        long randomNumber = Math.abs(random.nextLong()) % 10000000000L;
        return "T" + dateTime + randomNumber;
    }
}
