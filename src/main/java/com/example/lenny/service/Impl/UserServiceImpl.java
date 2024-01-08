package com.example.lenny.service.Impl;

import com.example.lenny.dto.UserDTO;
import com.example.lenny.entity.Customer;
import com.example.lenny.entity.Merchant;
import com.example.lenny.entity.User;
import com.example.lenny.exception.UserAlreadyExistsException;
import com.example.lenny.repository.CustomerRepository;
import com.example.lenny.repository.MerchantRepository;
import com.example.lenny.repository.UserRepository;
import com.example.lenny.service.EmailService;
import com.example.lenny.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MerchantRepository authorRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    @Override
    public void saveUser(UserDTO userDTO, String type) {

        if (isUserExists(userDTO.getEmail())) {
            throw new UserAlreadyExistsException();
        } else {
            String customerString = "customer";
            String merchantString = "merchant";
            User user = new User();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setActive(true);


            if (type.equals(customerString)) {

                user.setRole("ROLE_CUSTOMER");
                userRepository.save(user);

                Customer customer = new Customer();
                customer.setUser(user);
                customerRepository.save(customer);

            } else if (type.equals(merchantString)) {
                user.setRole("ROLE_MERCHANT");
                userRepository.save(user);

                Merchant merchant = new Merchant();
                merchant.setUser(user);
                authorRepository.save(merchant);
            }
            emailService.sendEmail(userDTO.getEmail(), "Successful Registration", "Hey, "+userDTO.getName()+" welcome to  lenny.");
        }
    }

    @Override
    public boolean isUserExists(String email) {
        if (userRepository.findByEmail(email) != null && userRepository.findByEmail(email).isActive() ) {
            return true;
        }
        else
            return false;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}