package com.service.account.payload;

import com.service.account.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCreateDto {
    @NotBlank(message = "user is mandatory")
    private String userId;
    @NotBlank(message = "account type is mandatory")
    private AccountType accountType;
    @NotBlank(message = "balance is mandatory")
    private Double balance;
}
