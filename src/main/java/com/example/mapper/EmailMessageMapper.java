package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.mail.SimpleMailMessage;

import com.example.dto.kafka.KafkaEmailMessageDto;
import com.example.dto.rest.EmailMessageDto;

@Mapper
public interface EmailMessageMapper {
    
    @Mapping(target = "cc", ignore = true)
    @Mapping(target = "bcc", ignore = true)
    @Mapping(target = "from", ignore = true)
    @Mapping(target = "replyTo", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "sentDate", ignore = true)
    @Mapping(target = "to", source = ".", qualifiedByName = "toArrayString")
    SimpleMailMessage toSimpleMailMessage(EmailMessageDto dto);
    
    @Named("toArrayString")
    default String[] toArrayString(EmailMessageDto dto) {
        return new String[] { dto.getTo() };
    }

    @Mapping(target = "cc", ignore = true)
    @Mapping(target = "bcc", ignore = true)
    @Mapping(target = "from", ignore = true)
    @Mapping(target = "replyTo", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "sentDate", ignore = true)
    @Mapping(target = "to", source = ".", qualifiedByName = "toArrayString")
    SimpleMailMessage toSimpleMailMessage(KafkaEmailMessageDto dto);
    
    @Named("toArrayString")
    default String[] toArrayString(KafkaEmailMessageDto dto) {
        return new String[] { dto.getTo() };
    }

}
