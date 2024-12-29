package com.rita.product_management.entrypoint.api.mapper;

import com.rita.product_management.core.usecase.user.command.AuthCommand;
import com.rita.product_management.entrypoint.api.dto.request.AuthRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-28T21:44:26-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public AuthCommand fromRequestToCommand(AuthRequest request) {
        if ( request == null ) {
            return null;
        }

        String username = null;
        String password = null;

        username = request.username();
        password = request.password();

        AuthCommand authCommand = new AuthCommand( username, password );

        return authCommand;
    }
}
