package net.thumbtack.school.hospital.dto.response.getticket;

/*{
[
"ticket": “номер талона”,
"room": ”номер кабинета”,
"date": "дата",
"time": "время",
// для случая приема у одного врача
"doctorId": идентификатор врача,
"firstName": "имя врача",
"lastName": "фамилия врача",
"patronymic": "отчество врача",
"speciality": "специальность врача",
// для случая приема на врачебной комиссии
[
{
"doctorId": идентификатор врача,
"firstName": "имя врача",
"lastName": "фамилия врача",
"patronymic": "отчество врача",
"speciality": "специальность врача",
}
...
]
...
]
}*/

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetTicketListDtoResponse {

    private List<GetTicketDto> listDto;

    @JsonValue
    public List<GetTicketDto> getListDto() {
        return listDto;
    }

    public void setListDto(List<GetTicketDto> listDto) {
        this.listDto = listDto;
    }

    public GetTicketListDtoResponse(List<GetTicketDto> listDto) {
        setListDto(listDto);
    }

    public GetTicketListDtoResponse() {
    }


}
