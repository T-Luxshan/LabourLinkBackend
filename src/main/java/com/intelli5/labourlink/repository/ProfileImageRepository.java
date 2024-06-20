package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.ProfileImage;
import com.intelli5.labourlink.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    ProfileImage findByUser(User user);

    @Transactional
    void deleteByUser(User user);
}
