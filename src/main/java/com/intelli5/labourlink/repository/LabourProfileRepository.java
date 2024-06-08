package com.intelli5.labourlink.repository;


import com.intelli5.labourlink.entity.LabourProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LabourProfileRepository extends JpaRepository<LabourProfile, String> {
  List<LabourProfile> findByAboutMeContaining(String aboutMe);
  List<LabourProfile> findByGender(String gender);
  List<LabourProfile> findByLanguagesIn(List<String> languages);
  List<LabourProfile> findByLocation(String location);
  Optional<LabourProfile> findLabour(String email);

}
