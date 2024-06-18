package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.GetUserEmailFromTokenDTO;
import com.intelli5.labourlink.dto.UserDTO;
import com.intelli5.labourlink.dto.UserStatusUpdateDTO;
import com.intelli5.labourlink.entity.*;
import com.intelli5.labourlink.repository.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LabourRepository labourRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SuspendUserRepository suspendUserRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public void saveUser(User user, CustomerRepository customerRepository) {
        user.setStatus(Status.ONLINE);
        customerRepository.save(user);
    }

    public void saveUser(User user, LabourRepository labourRepository) {
        user.setStatus(Status.ONLINE);
        labourRepository.save(user);
    }

    public void disconnect(User user, @Qualifier("customerRepository") UserRepository repository) {
        User storedUser = repository.findById(user.getEmail()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }
    }

    public List<User> findConnectedCustomers() {
        return customerRepository.findAllByStatusAndRole(Status.ONLINE, UserRole.CUSTOMER);
    }

    public List<User> findConnectedLabours() {
        return labourRepository.findAllByStatusAndRole(Status.ONLINE, UserRole.LABOUR);
    }

    public User getCustomerById(String email) {
        // Try to find the user in the customer repository
        Optional<User> customerUserOptional = customerRepository.findById(email);
        if (customerUserOptional.isPresent()) {
            return customerUserOptional.get();
        }
        // If the user is not found in the customer repository, return null
        return null;
    }

    public User getLaborById(String email) {
        // Try to find the user in the labor repository
        Optional<User> laborUserOptional = labourRepository.findById(email);
        if (laborUserOptional.isPresent()) {
            return laborUserOptional.get();
        }
        // If the user is not found in the labor repository, return null
        return null;
    }

    public User getUserById(String id) {
        User user = getCustomerById(id);
        if (user == null) {
            user = getLaborById(id);
        }
        return user;
    }


    public User updateCustomer(String email, UserStatusUpdateDTO updateUserStatusDTO) {
        Optional<User> optionalUser = Optional.ofNullable(getUserById(email));
        User existingUser = optionalUser
                .orElseThrow(() -> new ResourceNotFoundException("User not found for given email: " + email));

        existingUser.setStatus(updateUserStatusDTO.getStatus());
        return customerRepository.save(existingUser);
    }

    public User updateLabor(String email, UserStatusUpdateDTO updateUserStatusDTO) {
        User existingUser = labourRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for given email: " + email));

        existingUser.setStatus(updateUserStatusDTO.getStatus());
        return labourRepository.save(existingUser);
    }


    public User updateUser(String email, User updateUser) {
        User existingUser = customerRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for given email: " + email));

        existingUser.setStatus(updateUser.getStatus());
        return customerRepository.save(existingUser);
    }


    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(getAllCustomers());
        allUsers.addAll(getAllLabors());
        return allUsers;
    }

    public List<User> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<User> getAllLabors() {
        return labourRepository.findAll();
    }

    public GetUserEmailFromTokenDTO getUserByEmail(String currentPrincipalName) {
        User user = getLaborById(currentPrincipalName);
        return GetUserEmailFromTokenDTO.builder()
                .email(user.getEmail())
                .build();
    }


    //------------------------------------+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++----------------------------------------

    public List<UserDTO>getAllUser(){
        List <UserDTO> userDtos=new ArrayList<>();
       List<User> users=userRepository.findAll(Sort.by(Sort.Order.desc("joinDate"), Sort.Order.desc("joinTime")));
        for(User user:users) {
            if ((user.getRole() == UserRole.CUSTOMER || user.getRole() == UserRole.LABOUR)&&(user.isAccountNonExpired())) {
            UserDTO userDto = new UserDTO();
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setJoinDate(user.getJoinDate());
            userDto.setRole(user.getRole());
            userDtos.add(userDto);
        }}
        return userDtos;
    }
    public int getAllUserCount() {
        return getAllUser().size();

    }

    public Optional<User> findUserByEmail(String email) {
        Optional<User> users=userRepository .findByEmail(email);
        if(users.isPresent()&&(users.get().isAccountNonExpired())){
            return users;
        }
        return null;
    }
    @Transactional
    public void removeUserByEmail(String email,String removalPurpose) {
        Optional<User> targetUser = userRepository.findByEmail(email);
        if (targetUser.isPresent()) {
            User user = targetUser.get();
            SuspendUser suspenduser=new SuspendUser();
            suspenduser.setEmail(user.getEmail());
            suspenduser.setName(user.getName());
            suspenduser.setPassword(user.getPassword());
            suspenduser.setMobileNumber(user.getMobileNumber());
            suspenduser.setRole(user.getRole());
            suspenduser.setJoinDate(user.getJoinDate());
            suspenduser.setJoinTime(user.getJoinTime());
            suspenduser.setPresent(false);
            suspenduser.setVerified(true);
            suspenduser.setEnabled(true);
            suspenduser.setEnabled(true);
            suspenduser.setAccountNonExpired(true);
            suspenduser.setAccountNonLocked(true);
            suspenduser.setCredentialsNonExpired(true);
            suspenduser.setReason(removalPurpose);
            suspenduser.setSuspendedDate(LocalDate.now());
            suspendUserRepository.save(suspenduser);
            refreshTokenRepository.deleteByUserEmail(email);
            userRepository.delete(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

        public List<UserDTO> getDeactivatedUser() {
            List<UserDTO> userDtos = new ArrayList<>();
            List<User> users = userRepository.findAll();
             for (User user : users) {
                if (!user.isAccountNonExpired()) {
                    if (user.getRole() == UserRole.CUSTOMER || user.getRole() == UserRole.LABOUR) {
                        UserDTO userDto = new UserDTO();
                        userDto.setName(user.getName());
                        userDto.setEmail(user.getEmail());
                        userDto.setJoinDate(user.getJoinDate());
                        userDto.setRole(user.getRole());
                        userDtos.add(userDto);
                    }
                }
            }
            return userDtos;
        }

    public int getDeactivateCount() {
        return getDeactivatedUser().size();

    }
    public Optional<User> findDeactivateUserByEmail(String email) {
        Optional<User> users=userRepository .findByEmail(email);
        if(users.isPresent()){
        if(!users.get().isAccountNonExpired()){
            return users;
        }}
        return null;
    }
    
    

//    public List<Appointment> get_UserBy_Email(String email) {
//        Optional<User> optionaluser = userRepository.findByEmail(email);
//        if (!optionaluser.isEmpty()) {
//            User user = optionaluser.get();
//            if (user instanceof Customer) {
//                return appointmentRepository.findByCustomer((Customer) user);
//            } else if (user instanceof Labour) {
//                return appointmentRepository.findByLabour((Labour) user);
//            } else {
//                throw new RuntimeException("Invalid" + email);
//            }
//        } else {
//            throw new RuntimeException("not found" + email);
//        }
//    }

//-------------------------Adding deactivate user : My purpose -----------------------------
//    public void getUserBy_Email_(String email) {
//        Optional<User> users=userRepository .findByEmail(email);
//        if (users.isPresent()) {
//            User user = users.get();
//            user.setAccountNonExpired(false);  // Set the field to false
//            userRepository.save(user);         // Save the updated user
//        }
//            }

}





