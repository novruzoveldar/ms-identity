package com.guavapay.model.mapper;

import com.guavapay.model.entity.Account;
import com.guavapay.model.request.CreateAccountRequest;
import com.guavapay.model.type.GenderType;
import org.mapstruct.*;
import org.springframework.util.StringUtils;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(target = "mobile", source = "login")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "mapGender")
    Account toAccountEntity(CreateAccountRequest createAccountRequest);

    @Named(value = "mapGender")
    default GenderType mapGender(String gender) {
        if (StringUtils.hasText(gender)) return GenderType.valueOf(gender);
        return null;
    }
}
