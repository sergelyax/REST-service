package service;
import entity.User;
import dto.UserDTO;
import dao.UserDAO;

import mapper.UserMapper;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserDAO userDAO;
    private final UserMapper userMapper;
    private UserService userService;


    public UserService(UserDAO userDAO, UserMapper userMapper) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
    }

    public UserDTO getUserById(int id) {
        User user = userDAO.getUserById(id);
        return user != null ? userMapper.userToUserDTO(user) : null;
    }


    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        return users.stream().map(userMapper::userToUserDTO).collect(Collectors.toList());
    }

    public void createUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        userDAO.createUser(user);
        userDTO.setId(user.getId());
    }

    public void updateUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        userDAO.updateUser(user);
    }



    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
}

