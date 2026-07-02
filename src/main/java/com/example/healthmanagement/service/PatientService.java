package com.example.healthmanagement.service;

import com.example.healthmanagement.dtos.MedicalHistoryRequest;
import com.example.healthmanagement.exception.UserNotFoudException;
import com.example.healthmanagement.model.MedicalHistory;
import com.example.healthmanagement.model.User;
import com.example.healthmanagement.repository.MedicalHistoryRepository;
import com.example.healthmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;

    //adding the medical report &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    @PreAuthorize("hasAuthority('PATIENT')")
    public String addMedicalReport(MedicalHistoryRequest request,String phone){
        User findUser = userRepository.findByContactPhone(phone);
        if(findUser==null){
            throw new UserNotFoudException("User Not Foud");
        }
        MedicalHistory medicalHistory = modelMapper.map(request,MedicalHistory.class);
        medicalHistory.setUser(findUser);
        medicalHistoryRepository.save(medicalHistory);

        return "Medical Report Added Successfully";
    }


    //see the medical report ***************************************************************************************&&&&&
    @PreAuthorize("hasAuthority('PATIENT')")
    public MedicalHistoryRequest getMedicalReport(Long id){
        User findUser = userRepository.findById(id).orElseThrow(()->new UserNotFoudException("User Not Foud"));

        //get the report
         MedicalHistory medicalHistory = medicalHistoryRepository.findByUserIdReport(id);

         return  modelMapper.map(medicalHistory,MedicalHistoryRequest.class);

    }

}
