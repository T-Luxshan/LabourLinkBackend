package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.Image;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.ImageRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/profilePhoto")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LabourRepository labourRepository;


    // display image
    @GetMapping("/display/{userId}")
    public ResponseEntity<byte[]> displayImage(@PathVariable("userId") String email) throws IOException, SQLException
    {
        Image image = imageService.viewByEmail(email);
        byte [] imageBytes = null;
        imageBytes = image.getImage().getBytes(1,(int) image.getImage().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    // view All images
    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("index");
        List<Image> imageList = imageService.viewAll();
        mv.addObject("imageList", imageList);
        return mv;
    }

    // add image - get
    @GetMapping("/add")
    public ModelAndView addImage(){
        return new ModelAndView("addimage");
    }

    // add image - post
    @PostMapping("/addProfilePhotoCustomer/{userId}")
    public String addImagePostCustomer(HttpServletRequest request,@PathVariable("userId") String userId ,@RequestParam("image") MultipartFile file) throws IOException, SerialException, SQLException
    {
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        User customer=customerRepository.findCustomer(userId);

        Image image = new Image();
        image.setImage(blob);
        image.setUser(customer);
        imageService.create(image);
        return "redirect:/";
    }

    @PostMapping("/addProfilePhotoLabour/{userId}")
    public String addImagePostLabour(HttpServletRequest request,@PathVariable("userId") String userId ,@RequestParam("image") MultipartFile file) throws IOException, SerialException, SQLException
    {
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        User labour=labourRepository.findLabour(userId);

        Image image = new Image();
        image.setImage(blob);
        image.setUser(labour);
        imageService.create(image);
        return "redirect:/";
    }
}
