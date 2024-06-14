package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.Image;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.ImageRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LabourRepository labourRepository;


    public Image create(Image image) {
        return imageRepository.save(image);
    }

    public List<Image> viewAll() {
        return (List<Image>) imageRepository.findAll();
    }

    public Image viewByEmail(String email) {
        Optional<User> customer =customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            Image image = imageRepository.findByUser(customer);
            if (image != null) {
                return imageRepository.findByUser(customer);
            }
        }
        Optional<User> labour =labourRepository.findByEmail(email);
        if (labour.isPresent()) {
            Image image = imageRepository.findByUser(labour);
            if (image != null) {
                return imageRepository.findByUser(labour);
            }
        }
        return null; // Or throw an exception if preferred
    }
}
