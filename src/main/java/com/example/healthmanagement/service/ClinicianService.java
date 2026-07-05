package com.example.healthmanagement.service;

import com.example.healthmanagement.dtos.AppointmentListResponse;
import com.example.healthmanagement.dtos.AppointmentRequest;
import com.example.healthmanagement.exception.UserNotFoudException;
import com.example.healthmanagement.model.Appointment;
import com.example.healthmanagement.model.User;
import com.example.healthmanagement.model.enums.AppointmentStatus;
import com.example.healthmanagement.repository.AppointmentRepository;
import com.example.healthmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicianService {
    private final ModelMapper modelMapper;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    //see the list of appointment
    public List<AppointmentListResponse> appointmentListResponses(Long id){
        List<Appointment> appointmentList = appointmentRepository.findAll();
        User user =userRepository.findClinicianById(id);
        if(user==null){
            throw new UserNotFoudException("User not found");
        }

return appointmentList.stream()
        .map(list->modelMapper.map(list, AppointmentListResponse.class))
        .toList();

    }

    //accept the appointment *************************************************************************************

    //add the approve , cancel and completed function to change the status of the Appointment

}
