package com.sistemas.pdv.service;

import com.sistemas.pdv.dto.UserDTO;
import com.sistemas.pdv.entity.User;
import com.sistemas.pdv.exceptions.NoItemException;
import com.sistemas.pdv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll(){
        return userRepository.findAll().stream().map(
                user -> new UserDTO(user.getId(),user.getName(), user.isEnabled())).collect(Collectors.toList());
    }
    public UserDTO save(User user){
        userRepository.save(user);
        return new UserDTO(user.getId(),user.getName(), user.isEnabled());
    }
    public UserDTO findById(Long id) {
        Optional<User> optional = userRepository.findById(id);

        if(!optional.isPresent()){
            throw new NoItemException("Usuário não encontrado.");
        }
        User user = optional.get();
        return new UserDTO(user.getId(),user.getName(), user.isEnabled());
    }

    public UserDTO update(User user){
        Optional<User> userToEdit = userRepository.findById(user.getId());

        if(!userToEdit.isPresent()){
            throw new NoItemException("Usuário não encontrado.");
        }
        userRepository.save(user);
        return new UserDTO(user.getId(),user.getName(), user.isEnabled());
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
