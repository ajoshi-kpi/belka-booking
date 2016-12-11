package com.example.controllers.registration;

import com.example.domain.User;
import com.example.dto.UserDto;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/users/register", method = RequestMethod.POST)
    @ResponseBody
    public PersistentEntityResource register(@RequestBody UserDto userDto, PersistentEntityResourceAssembler asm) throws UsernameExistsException {
        User founded = userRepository.findByUsername(userDto.getUsername());
        if(founded != null) throw new UsernameExistsException();
        User user = new User();
        user.setUsername(userDto.getUsername());
        return asm.toFullResource(userRepository.save(user));
    }
}
