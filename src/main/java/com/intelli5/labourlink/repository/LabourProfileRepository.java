package com.intelli5.labourlink.repository;


import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.LabourProfile;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LabourProfileRepository extends JpaRepository<LabourProfile, String> {
  List<LabourProfile> findByAboutMeContaining(String aboutMe);
  List<LabourProfile> findByGender(String gender);
  List<LabourProfile> findByLanguagesIn(List<String> languages);
//  List<LabourProfile> findByLocation(String location);


  Optional<Object> findByLabour(Labour labour);

  @Transactional
  void deleteByLabour(Labour labour);
}
