package org.example.service.user;

import org.example.domain.DTO.BaseResponse;
import org.example.domain.DTO.UserCreateDto;
import org.example.domain.DTO.UserStateDto;
import org.example.domain.model.User;
import org.example.domain.model.UserState;
import org.example.service.BaseService;

import java.util.UUID;

public interface UserService extends BaseService {
    BaseResponse save(UserCreateDto userCreateDto);

    BaseResponse<UserStateDto> getUserState(String chatId);

    void updateState(UserStateDto stateDTO);
    void updateUserState(String chatId, UserState state);
    User getByChatId(String chatId);

    void setUserBalance(UUID id, Double totalPrice);
}
