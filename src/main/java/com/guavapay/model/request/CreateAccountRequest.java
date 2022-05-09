package com.guavapay.model.request;

import com.guavapay.error.validation.constraint.ValidPassword;
import com.guavapay.model.type.CourierType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateAccountRequest {

    @NotNull(message = "Birthday must be filled")
    private Date birthday;
    @NotNull(message = "Phone number must be filled")
    private String login;
    @NotNull(message = "Email must be filled")
    private String email;
    @NotNull(message = "Name must be filled")
    private String name;
    @NotNull(message = "Surname must be filled")
    private String surname;
    @NotNull(message = "Gender must be filled")
    private String gender;
//    @ValidPassword
    private String password;
    private CourierType courierType;
}
