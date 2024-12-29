package com.rita.product_management.entrypoint.api.mapper;

import com.rita.product_management.core.usecase.user.command.AuthCommand;
import com.rita.product_management.entrypoint.api.dto.request.AuthRequest;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true), injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuthMapper {
    AuthCommand fromRequestToCommand(AuthRequest request);
}
