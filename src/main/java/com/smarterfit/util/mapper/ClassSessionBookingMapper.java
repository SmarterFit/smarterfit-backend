package com.smarterfit.util.mapper;


import com.smarterfit.dto.request.ClassSessionBookingRequestDTO;
import com.smarterfit.dto.request.ClassSessionBookingStatusDTO;
import com.smarterfit.dto.response.ClassSessionBookingResponseDTO;
import com.smarterfit.enums.Status;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.ClassSession;
import com.smarterfit.model.User;
import com.smarterfit.model.classSessionBooking.ClassSessionBooking;
import com.smarterfit.util.Converter;

public class ClassSessionBookingMapper {

    private ClassSessionBookingMapper() {
        // Private constructor to prevent instantiation
    }

    public static ClassSessionBooking toEntity(ClassSessionBookingRequestDTO classSessionBookingRequestDTO,
                                               ClassSession classSession, ClassSessionBooking classSessionBooking, User user) {
        if(classSessionBooking == null) {
            throw new ResourceNotFoundException("ClassSessionBooking not found.");
        }

        classSessionBooking.setClassSession(classSession);
        classSessionBooking.setUser(user);
        classSessionBooking.setStatus(Converter.stringToEnum(Status.class, classSessionBookingRequestDTO.bookingStatus()));
        classSessionBooking.setBookingDate(classSessionBookingRequestDTO.bookingDate());


        return  classSessionBooking;
    }

    public static ClassSessionBooking toEntity(ClassSessionBookingRequestDTO classSessionBookingRequestDTO,
                                               ClassSession classSession, User user) {
        return toEntity(classSessionBookingRequestDTO, classSession, new ClassSessionBooking(), user);
    }

    public static ClassSessionBooking toEntityUpdateStatus(ClassSessionBookingStatusDTO statusDTO,
                                                           ClassSessionBooking classSessionBooking) {

        classSessionBooking.setStatus(Converter.stringToEnum(Status.class, statusDTO.bookingStatus()));
        return classSessionBooking;
    }

    public static ClassSessionBookingResponseDTO toResponse(ClassSessionBooking classSessionBooking) {
        return new ClassSessionBookingResponseDTO(
                classSessionBooking.getUser().getId(),
                classSessionBooking.getClassSession().getId(),
                classSessionBooking.getBookingDate(),
                classSessionBooking.getStatus().toString()
        );
    }

}
