package net.thumbtack.school.hospital.mapper;

import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.request.RegisterDoctorDtoRequest;
import net.thumbtack.school.hospital.dto.response.EditScheduleDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DoctorMapper {

    private static final String DATE_FORMAT = "ddMMyyyy";
    private static final String TIME_FORMAT = "HHmm";


    public static LoginDtoResponse convertToDto(Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        return new LoginDtoResponse(
                doctor.getId(),
                doctor.getUser().getFirstName(),
                doctor.getUser().getLastName(),
                doctor.getUser().getPatronymic(),
                doctor.getSpeciality().getName(),
                doctor.getRoom().getNumber()
        );
    }


    public static EditScheduleDtoResponse convertToDto(Doctor doctor, List<DaySchedule> dayScheduleList) {
        EditScheduleDtoResponse response = new EditScheduleDtoResponse();
        // Doctor info
        response.setId(doctor.getId());
        response.setFirstName(doctor.getUser().getFirstName());
        response.setLastName(doctor.getUser().getLastName());
        response.setPatronymic(doctor.getUser().getPatronymic());
        response.setSpeciality(doctor.getSpeciality().getName());
        response.setRoom(doctor.getRoom().getNumber());
        // Day schedule
        List<EditScheduleDtoResponse.Schedule> scheduleList = new ArrayList<>();
        EditScheduleDtoResponse.Schedule schedule = new EditScheduleDtoResponse().new Schedule();
        for (DaySchedule daySchedule : dayScheduleList) {
            response.setSchedule(scheduleList);
            scheduleList.add(schedule);
            schedule.setDate(daySchedule.getDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            List<EditScheduleDtoResponse.Schedule.TicketScheduleDto> ticketScheduleDtoDtoList = new ArrayList<>();
            schedule.setTicketScheduleDto(ticketScheduleDtoDtoList);
            // Ticket schedule
            for (TicketSchedule ticketSchedule : daySchedule.getTicketSchedule()) {
                EditScheduleDtoResponse.Schedule.TicketScheduleDto ticketScheduleDtoDto = new EditScheduleDtoResponse().new Schedule().new TicketScheduleDto();
                ticketScheduleDtoDto.setTime(ticketSchedule.getTimeStart().format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
                ticketScheduleDtoDtoList.add(ticketScheduleDtoDto);
                if (ticketSchedule.getPatient() != null) {
                    EditScheduleDtoResponse.Schedule.TicketScheduleDto.Patient patient = new EditScheduleDtoResponse().new Schedule().new TicketScheduleDto().new Patient();
                    ticketScheduleDtoDto.setPatient(patient);
                    patient.setAddress(ticketSchedule.getPatient().getAddress());
                    patient.setEmail(ticketSchedule.getPatient().getEmail());
                }
            }
        }
        return response;
    }


    public static List<EditScheduleDtoResponse> convertToDto(List<DaySchedule> dayScheduleList) {
        List<EditScheduleDtoResponse> responseList = new ArrayList<>();
        for (DaySchedule daySchedule : dayScheduleList) {
            EditScheduleDtoResponse response = new EditScheduleDtoResponse();
            responseList.add(response);
            response.setFirstName(daySchedule.getDoctor().getUser().getFirstName());
            response.setLastName(daySchedule.getDoctor().getUser().getLastName());
            response.setPatronymic(daySchedule.getDoctor().getUser().getPatronymic());
            response.setSpeciality(daySchedule.getDoctor().getSpeciality().getName());
            response.setRoom(daySchedule.getDoctor().getRoom().getNumber());

            List<EditScheduleDtoResponse.Schedule> scheduleList = new ArrayList<>();
            EditScheduleDtoResponse.Schedule schedule = new EditScheduleDtoResponse().new Schedule();
            scheduleList.add(schedule);
            response.setSchedule(scheduleList);
            schedule.setDate(daySchedule.getDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            List<EditScheduleDtoResponse.Schedule.TicketScheduleDto> ticketScheduleDtoList = new ArrayList<>();
            schedule.setTicketScheduleDto(ticketScheduleDtoList);
            for (TicketSchedule ticketSchedule : daySchedule.getTicketSchedule()) {
                EditScheduleDtoResponse.Schedule.TicketScheduleDto ticketScheduleDto = new EditScheduleDtoResponse().new Schedule().new TicketScheduleDto();
                ticketScheduleDtoList.add(ticketScheduleDto);
                ticketScheduleDto.setTime(ticketSchedule.getTimeStart().format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
                if (ticketSchedule.getPatient() != null) {
                    EditScheduleDtoResponse.Schedule.TicketScheduleDto.Patient patientDto = new EditScheduleDtoResponse().new Schedule().new TicketScheduleDto().new Patient();
                    ticketScheduleDto.setPatient(patientDto);
                    patientDto.setPatientId(ticketSchedule.getPatient().getId());
                    patientDto.setAddress(ticketSchedule.getPatient().getAddress());
                    patientDto.setEmail(ticketSchedule.getPatient().getAddress());
                    patientDto.setPhone(ticketSchedule.getPatient().getPhone());
                    patientDto.setFirstName(ticketSchedule.getPatient().getUser().getFirstName());
                    patientDto.setLastName(ticketSchedule.getPatient().getUser().getLastName());
                    patientDto.setPatronymic(ticketSchedule.getPatient().getUser().getPatronymic());
                }
            }
        }
        return responseList;
    }


    public static Doctor convertToEntity(RegisterDoctorDtoRequest dto) {
        if (dto == null) {
            return null;
        }
        Doctor doctor = new Doctor(
                new User(
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getPatronymic(),
                        UserType.DOCTOR,
                        dto.getLogin(),
                        dto.getPassword(),
                        null
                ),
                new Speciality(dto.getSpeciality()),
                new Room(dto.getRoom())
        );
        return doctor;
    }


}
