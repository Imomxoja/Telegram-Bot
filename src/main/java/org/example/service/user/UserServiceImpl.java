package org.example.service.user;

import org.example.domain.DTO.BaseResponse;
import org.example.domain.DTO.UserCreateDto;
import org.example.domain.DTO.UserStateDto;
import org.example.domain.model.User;
import org.example.domain.model.UserState;
import org.example.repository.user.UserRepository;
import org.example.repository.user.UserRepositoryImpl;
import org.example.util.BeanUtil;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private final ModelMapper modelMapper = BeanUtil.getModelMapper();

    @Override
    public BaseResponse save(UserCreateDto userCreateDto) {
        User user = modelMapper.map(userCreateDto, User.class);
        user.setState(UserState.START);

        int save = userRepository.save(user);

        if(save == 200) {
            return new BaseResponse("Success", 200);
        }
        return new BaseResponse("Fail", 500);
    }

    @Override
    public BaseResponse save(Object o) {
        return null;
    }

    @Override
    public BaseResponse getById(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<UserStateDto> getUserState(String chatId) {
        Optional<User> userByChatId = userRepository.findUserByChatId(chatId);
        if(userByChatId.isPresent()) {
            User user = userByChatId.get();
            UserStateDto userStateDTO = new UserStateDto(user.getChatId(), user.getState());
            return new BaseResponse<>("Success", 200, userStateDTO);
        }

        return new BaseResponse<>("Fail", 404);
    }

    @Override
    public void updateState(UserStateDto stateDTO) {
        userRepository.updateUserState(stateDTO.state(), stateDTO.chatId());
    }

    @Override
    public void updateUserState(String chatId, UserState state) {
        userRepository.updateUserState(state, chatId);
    }

    @Override
    public User getByChatId(String chatId) {
        return userRepository.getByChatId(chatId);
    }

    @Override
    public void setUserBalance(UUID id, Double totalPrice) {
        userRepository.updateUserBalance(id, totalPrice);
    }


}
