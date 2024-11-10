package com.service.account.enums;

public enum TransactionType {
    DEPOSIT("Deposit", "Adding funds to an account."),
    WITHDRAWAL("Withdrawal", "Removing funds from an account."),
    TRANSFER("Transfer", "Moving funds from one account to another."),
    PAYMENT("Payment", "Paying for goods or services."),
    INTEREST("Interest", "Interest earned on a savings or investment account.");

    private final String displayName;
    private final String description;

    TransactionType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}