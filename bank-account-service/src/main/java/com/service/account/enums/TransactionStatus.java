package com.service.account.enums;

public enum TransactionStatus {
    PENDING("Pending", "The transaction is currently being processed."),
    COMPLETE("Complete", "The transaction has been successfully completed."),
    FAILED("Failed", "The transaction has failed due to an error."),
    CANCELED("Canceled", "The transaction has been canceled by the user."),
    IN_PROGRESS("In Progress", "The transaction is currently in progress.");

    private final String displayName;
    private final String description;

    TransactionStatus(String displayName, String description) {
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
