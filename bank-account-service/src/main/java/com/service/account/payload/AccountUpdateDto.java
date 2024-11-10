package com.service.account.payload;

import com.service.account.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountUpdateDto {
    @NotNull(message = "account type is mandatory")
    private AccountType accountType;
    @NotNull(message = "balance is mandatory")
    private Double balance;
}
