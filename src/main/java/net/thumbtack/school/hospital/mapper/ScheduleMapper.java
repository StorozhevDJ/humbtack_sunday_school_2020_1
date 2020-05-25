package net.thumbtack.school.hospital.mapper;

import net.thumbtack.school.hospital.database.model.Commission;
import net.thumbtack.school.hospital.database.model.DaySchedule;
import net.thumbtack.school.hospital.database.model.Doctor;
import net.thumbtack.school.hospital.dto.request.AddCommissionDtoRequest;
import net.thumbtack.school.hospital.dto.response.AddCommissionDtoResponse;
import net.thumbtack.school.hospital.dto.response.AddTicketDtoResponse;
import net.thumbtack.school.hospital.dto.response.getticket.DoctorsCommission;
import net.thumbtack.school.hospital.dto.response.getticket.GetTicketCommissionDto;
import net.thumbtack.school.hospital.dto.response.getticket.GetTicketDto;
import net.thumbtack.school.hospital.dto.response.getticket.GetTicketListDtoResponse;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScheduleMapper {

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String TIME_FORMAT = "HH:mm";

    public static AddTicketDtoResponse convertToDto(DaySchedule daySchedule) {
        return new AddTicketDtoResponse(
                daySchedule.getTicketSchedule().get(0).getTicket(),
                daySchedule.getDoctor().getId(),
                daySchedule.getDoctor().getUser().getFirstName(),
                daySchedule.getDoctor().getUser().getLastName(),
                daySchedule.getDoctor().getUser().getPatronymic(),
                daySchedule.getDoctor().getSpeciality().getName(),
                daySchedule.getDoctor().getRoom().getNumber(),
                daySchedule.getDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                daySchedule.getTicketSchedule().get(0).getTimeStart().format(DateTimeFormatter.ofPattern(TIME_FORMAT))
        );
    }

    public static GetTicketListDtoResponse convertToDto(List<DaySchedule> dayScheduleList, List<Commission> commissionList) {
        List<GetTicketDto> dtoList = new ArrayList<>();
        for (DaySchedule daySchedule : dayScheduleList) {
            dtoList.add(new GetTicketDto(
                    daySchedule.getTicketSchedule().get(0).getTicket(),
                    daySchedule.getDoctor().getRoom().getNumber(),
                    daySchedule.getDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                    daySchedule.getTicketSchedule().get(0).getTimeStart().format(DateTimeFormatter.ofPattern(TIME_FORMAT)),
                    daySchedule.getDoctor().getId(),
                    daySchedule.getDoctor().getUser().getFirstName(),
                    daySchedule.getDoctor().getUser().getLastName(),
                    daySchedule.getDoctor().getUser().getPatronymic(),
                    daySchedule.getDoctor().getSpeciality().getName()
            ));
        }
        for (Commission commission : commissionList) {
            List<GetTicketCommissionDto> doctorsCommissionList = new ArrayList<>();
            GetTicketCommissionDto ticketCommissionDto;
            for (Doctor doctor : commission.getDoctorSet()) {
                doctorsCommissionList.add(new GetTicketCommissionDto(new DoctorsCommission(
                        doctor.getId(),
                        doctor.getUser().getFirstName(),
                        doctor.getUser().getLastName(),
                        doctor.getUser().getPatronymic(),
                        doctor.getSpeciality().getName()
                )));
            }
            dtoList.add(new GetTicketDto(
                    commission.getTicket(),
                    commission.getRoom(),
                    commission.getDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                    commission.getTimeStart().format(DateTimeFormatter.ofPattern(TIME_FORMAT)),
                    doctorsCommissionList
            ));
        }
        return new GetTicketListDtoResponse(dtoList);
    }

    public static AddCommissionDtoResponse convertToDto(AddCommissionDtoRequest commissionDto, Commission commission) {

        return new AddCommissionDtoResponse(
                commission.getTicket(),
                commissionDto.getPatientId(),
                commissionDto.getDoctorIds(),
                commissionDto.getRoom(),
                commissionDto.getDate(),
                commissionDto.getTime(),
                commissionDto.getDuration()
        );
    }
}
