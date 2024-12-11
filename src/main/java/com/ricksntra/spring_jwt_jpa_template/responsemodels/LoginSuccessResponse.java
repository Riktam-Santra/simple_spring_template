package com.ricksntra.spring_jwt_jpa_template.responsemodels;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginSuccessResponse {
    String session;
    String refresh;
}
