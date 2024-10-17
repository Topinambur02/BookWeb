package com.example.mapper;

import org.mapstruct.Mapper;
import org.springframework.mail.SimpleMailMessage;

import com.example.dto.EmailMessageDto;

@Mapper
public interface EmailMessageMapper {
    
    SimpleMailMessage toSimpleMailMessage(EmailMessageDto dto); 
    
}
