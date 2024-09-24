package com.sistemas.pdv.service;

import com.sistemas.pdv.dto.UserDTO;
import com.sistemas.pdv.dto.UserResponseDTO;
import com.sistemas.pdv.entity.User;
import com.sistemas.pdv.exceptions.NoItemException;
import com.sistemas.pdv.repository.UserRepository;
import com.sistemas.pdv.security.SecurityConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private ModelMapper mapper = new ModelMapper();

    public List<UserResponseDTO> findAll(){
        return userRepository.findAll().stream().map(
                user -> new UserResponseDTO(user.getId(),user.getName(), user.getUsername(), user.isEnabled())).collect(Collectors.toList());
    }
    public UserDTO save(UserDTO user){
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        User entity = mapper.map(user, User.class);

        userRepository.save(entity);
        return new UserDTO(entity.getId(),entity.getName(), user.getUsername(), user.getPassword(), entity.isEnabled());
    }
    public UserDTO findById(Long id) {
        Optional<User> optional = userRepository.findById(id);

        if(!optional.isPresent()){
            throw new NoItemException("Usuário não encontrado.");
        }
        User user = optional.get();
        return new UserDTO(user.getId(),user.getName(), user.getUsername(), user.getPassword(), user.isEnabled());
    }

    public UserDTO update(UserDTO user){

        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));

        User entity = mapper.map(user, User.class);

        Optional<User> userToEdit = userRepository.findById(entity.getId());

        if(!userToEdit.isPresent()){
            throw new NoItemException("Usuário não encontrado.");
        }
        userRepository.save(entity);
        return new UserDTO(entity.getId(),entity.getName(), entity.getUsername(), entity.getPassword(), entity.isEnabled());
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User getByUserName(String username){
        return userRepository.findUserByUserName(username);
    }
}
