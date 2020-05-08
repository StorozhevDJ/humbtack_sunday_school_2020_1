package net.thumbtack.school.hospital.dto.response;

/*

{
        "id": идентификационный номер,
        "firstName": "имя",
        "lastName": "фамилия",
        "patronymic": "отчество",
        "speciality": "специальность",
        "room": ”номер кабинета”,
         "schedule":  [
           {
            "date" :"дата приема",
            "daySchedule": [
                {
                  time : "время",
                  patient :
                    {
                      "patientId": идентификационный номер пациента, получившего талон на это время,
                      "firstName": "имя",
                      "lastName": "фамилия",
                      "patronymic": "отчество",  // необязателен
                      "email": "адрес",
                      "address": "домашний адрес, одной строкой",
                      "phone": "номер",
                    }
               }
              ...
             ]
         }
        ...
        ]
}

*/


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class EditScheduleDtoResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String speciality;
    private String room;
    private List<Schedule> schedule;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    public class Schedule {
        private String date;
        private List<TicketScheduleDto> ticketScheduleDto;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<TicketScheduleDto> getTicketScheduleDto() {
            return ticketScheduleDto;
        }

        public void setTicketScheduleDto(List<TicketScheduleDto> ticketScheduleDto) {
            this.ticketScheduleDto = ticketScheduleDto;
        }

        public class TicketScheduleDto {
            private String time;
            private Patient patient;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public Patient getPatient() {
                return patient;
            }

            public void setPatient(Patient patient) {
                this.patient = patient;
            }

            @JsonInclude(JsonInclude.Include.NON_NULL)
            public class Patient {
                private Integer patientId;
                private String firstName;
                private String lastName;
                private String patronymic;
                private String email;
                private String address;
                private String phone;

                public Integer getPatientId() {
                    return patientId;
                }

                public void setPatientId(Integer patientId) {
                    this.patientId = patientId;
                }

                public String getFirstName() {
                    return firstName;
                }

                public void setFirstName(String firstName) {
                    this.firstName = firstName;
                }

                public String getLastName() {
                    return lastName;
                }

                public void setLastName(String lastName) {
                    this.lastName = lastName;
                }

                public String getPatronymic() {
                    return patronymic;
                }

                public void setPatronymic(String patronymic) {
                    this.patronymic = patronymic;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }
            }

        }
    }

}
