package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.mail.SimpleMailMessage;

import com.example.dto.EmailMessageDto;

@Mapper
public interface EmailMessageMapper {
    
    @Mapping(target = "to", source = ".", qualifiedByName = "toArrayString")
    SimpleMailMessage toSimpleMailMessage(EmailMessageDto dto);
    
    @Named("toArrayString")
    default String[] toArrayString(EmailMessageDto dto) {
        return new String[] { dto.getTo() };
    }
    
}
