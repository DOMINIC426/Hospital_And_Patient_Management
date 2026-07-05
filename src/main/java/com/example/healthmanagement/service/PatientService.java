package com.example.healthmanagement.service;

import com.example.healthmanagement.dtos.AppointmentRequest;
import com.example.healthmanagement.dtos.AppointmentResponse;
import com.example.healthmanagement.dtos.MedicalHistoryRequest;
import com.example.healthmanagement.exception.DayIsNotValidException;
import com.example.healthmanagement.exception.UserNotFoudException;
import com.example.healthmanagement.model.Appointment;
import com.example.healthmanagement.model.MedicalHistory;
import com.example.healthmanagement.model.User;
import com.example.healthmanagement.model.enums.AppointmentStatus;
import com.example.healthmanagement.model.enums.Role;
import com.example.healthmanagement.repository.AppointmentRepository;
import com.example.healthmanagement.repository.MedicalHistoryRepository;
import com.example.healthmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final AppointmentRepository appointmentRepository;

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

    // ##################################### Making Appointment
    @PreAuthorize("hasAuthority('PATIENT')")
    public AppointmentResponse makeAppointment(String phone, AppointmentRequest request,Long clinicianID){
        //find if user exist
        User findUser = userRepository.findByContactPhone(phone);
        if(findUser==null||findUser.getRole()!= Role.PATIENT){
            throw new UserNotFoudException("User Not Foud");
        }
        //find the clinician if exit by id
        User clinician =userRepository.findClinicianById(clinicianID);
        if(clinician==null||clinician.getRole()!= Role.CLINICIAN){
            throw new UserNotFoudException("User Not Foud");
        }

        //making appointment
        Appointment appointment = modelMapper.map(request,Appointment.class);

        //save appointment and connect with user
        appointment.setPatient(findUser);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setClinician(clinician);
        if(appointment.getDateTime().isBefore(LocalDate.now())){
            throw new DayIsNotValidException("The Date is Not in The Range Make sure the date is well checked and the year is in range");
        }
       Appointment saved= appointmentRepository.save(appointment);

        return  modelMapper.map(saved,AppointmentResponse.class);
    }

}
