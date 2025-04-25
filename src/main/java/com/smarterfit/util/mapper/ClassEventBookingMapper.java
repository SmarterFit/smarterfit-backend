package com.smarterfit.util.mapper;


import com.smarterfit.dto.request.ClassEventBookingRequestDTO;
import com.smarterfit.dto.request.ClassEventBookingStatusDTO;
import com.smarterfit.dto.response.ClassEventBookingResponseDTO;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.ClassEvent;
import com.smarterfit.model.User;
import com.smarterfit.model.classEventBooking.ClassEventBooking;


public class ClassEventBookingMapper {

    private ClassEventBookingMapper() {
        // Private constructor to prevent instantiation
    }

    public static ClassEventBooking toEntity(ClassEventBookingRequestDTO classEventBookingRequestDTO,
                                               ClassEvent classEvent, ClassEventBooking classEventBooking, User user) {
        if(classEventBooking == null) {
            throw new ResourceNotFoundException("ClassEventBooking not found.");
        }

        classEventBooking.setClassEvent(classEvent);
        classEventBooking.setUser(user);
        classEventBooking.setBookingStatus(classEventBookingRequestDTO.bookingStatus());
        classEventBooking.setBookingDate(classEventBookingRequestDTO.bookingDate());


        return  classEventBooking;
    }

    public static ClassEventBooking toEntity(ClassEventBookingRequestDTO classEventBookingRequestDTO,
                                               ClassEvent classEvent, User user) {
        return toEntity(classEventBookingRequestDTO, classEvent, new ClassEventBooking(), user);
    }

    public static ClassEventBooking toEntityUpdateStatus(ClassEventBookingStatusDTO statusDTO,
                                                           ClassEventBooking classEventBooking) {

        classEventBooking.setBookingStatus(statusDTO.bookingStatus());
        return classEventBooking;
    }

    public static ClassEventBookingResponseDTO toResponse(ClassEventBooking classEventBooking) {
        return new ClassEventBookingResponseDTO(
                classEventBooking.getUser().getId(),
                classEventBooking.getClassEvent().getId(),
                classEventBooking.getBookingDate(),
                classEventBooking.getBookingStatus().toString()
        );
    }

}
