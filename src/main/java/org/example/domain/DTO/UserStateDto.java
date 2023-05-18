package org.example.domain.DTO;

import org.example.domain.model.UserState;

public record UserStateDto (String chatId, UserState state){
}
