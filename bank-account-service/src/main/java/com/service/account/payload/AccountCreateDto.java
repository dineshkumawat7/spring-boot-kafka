package com.service.account.payload;

import com.service.account.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCreateDto {
    @NotBlank(message = "user is mandatory")
    private String userId;
    @NotNull(message = "account type is mandatory")
    private AccountType accountType;
    @NotNull(message = "balance is mandatory")
    private Double balance;
}
