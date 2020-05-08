package net.thumbtack.school.hospital.mapper;

import net.thumbtack.school.hospital.database.model.Commission;
import net.thumbtack.school.hospital.database.model.DaySchedule;
import net.thumbtack.school.hospital.dto.request.AddCommissionDtoRequest;
import net.thumbtack.school.hospital.dto.response.AddCommissionDtoResponse;
import net.thumbtack.school.hospital.dto.response.AddTicketDtoResponse;
import net.thumbtack.school.hospital.dto.response.getticket.GetTicketDto;
import net.thumbtack.school.hospital.dto.response.getticket.GetTicketListDtoResponse;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScheduleMapper {

    public static AddTicketDtoResponse convertToDto(DaySchedule daySchedule) {
        return new AddTicketDtoResponse(
                daySchedule.getTicketSchedule().get(0).getTicket(),
                daySchedule.getDoctor().getId(),
                daySchedule.getDoctor().getUser().getFirstName(),
                daySchedule.getDoctor().getUser().getLastName(),
                daySchedule.getDoctor().getUser().getPatronymic(),
                daySchedule.getDoctor().getSpeciality().getName(),
                daySchedule.getDoctor().getRoom().getNumber(),
                daySchedule.getDate().format(DateTimeFormatter.ofPattern("ddMMyyyy")),
                daySchedule.getTicketSchedule().get(0).getTimeStart().format(DateTimeFormatter.ofPattern("HHmm"))
        );
    }

    public static GetTicketListDtoResponse convertToDto(List<DaySchedule> dayScheduleList) {
        List<GetTicketDto> dtoList = new ArrayList<>();
        for (DaySchedule daySchedule : dayScheduleList) {
            dtoList.add(new GetTicketDto(
                    daySchedule.getTicketSchedule().get(0).getTicket(),
                    daySchedule.getDoctor().getRoom().getNumber(),
                    daySchedule.getDate().format(DateTimeFormatter.ofPattern("ddMMyyyy")),
                    daySchedule.getTicketSchedule().get(0).getTimeStart().format(DateTimeFormatter.ofPattern("HHmm")),
                    daySchedule.getDoctor().getId(),
                    daySchedule.getDoctor().getUser().getFirstName(),
                    daySchedule.getDoctor().getUser().getLastName(),
                    daySchedule.getDoctor().getUser().getPatronymic(),
                    daySchedule.getDoctor().getSpeciality().getName()
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
