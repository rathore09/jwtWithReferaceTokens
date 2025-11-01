package com.jwtAuthentication.jwtAuthentication.service;

import com.jwtAuthentication.jwtAuthentication.entity.Customer;
import com.jwtAuthentication.jwtAuthentication.entity.Role;
import com.jwtAuthentication.jwtAuthentication.repository.CustomerRepository;
import com.jwtAuthentication.jwtAuthentication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder  passwordEncoder;

    @Override
    public Customer add(Customer customer) {

        Role role  =  roleRepository.findByRole("USER");
        customer.getRoles().add(role);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer  customer = customerRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("No user found by that username"));

        List<GrantedAuthority> list =  new ArrayList<>();

        for(Role r : customer.getRoles()){
                list.add(new SimpleGrantedAuthority("ROLE_"+r.getRole()));
        }

        return User.builder()
                .username(customer.getUsername())
                .password(customer.getPassword())
                .authorities(list)
                .build();
    }
}
