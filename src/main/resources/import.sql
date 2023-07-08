-- This file allow to write SQL commands that will be emitted in test and dev.

create table appointment (
    id serial primary key,
    "date" date,
    "time" time,
    doctor_id integer,
    patient_id integer,
    event_type varchar(40)
);

create table appointment_slot (
    id serial primary key,
    "datetime" timestamp,
    doctor_id integer,
    patient_id integer,
    event_type varchar(40)
);

insert into appointment ("date", "time", doctor_id, patient_id, event_type) values('2023-06-14', '11:00:00.000', 3, 1, 'OPEN');
insert into appointment ("date", "time", doctor_id, patient_id, event_type) values('2023-06-15', '12:00:00.000', 2, 2, 'OPEN');
insert into appointment ("date", "time", doctor_id, patient_id, event_type) values('2023-06-16', '13:00:00.000', 3, 3, 'CLOSE');
insert into appointment ("date", "time", doctor_id, patient_id, event_type) values('2023-06-17', '14:00:00.000', 4, 4, 'CLOSE');
insert into appointment ("date", "time", doctor_id, patient_id, event_type) values('2023-06-14', '15:00:00.000', 3, 5, 'OPEN');
insert into appointment ("date", "time", doctor_id, patient_id, event_type) values('2023-06-19', '16:00:00.000', 1, 6, 'CLOSE');
insert into appointment ("date", "time", doctor_id, patient_id, event_type) values('2023-06-20', '17:00:00.000', 3, 7, 'OPEN');
insert into appointment ("date", "time", doctor_id, patient_id, event_type) values('2023-06-21', '18:00:00.000', 2, 8, 'OPEN');
insert into appointment ("date", "time", doctor_id, patient_id, event_type) values('2023-06-22', '19:00:00.000', 3, 9, 'CLOSE');
insert into appointment ("date", "time", doctor_id, patient_id, event_type) values('2023-06-23', '20:00:00.000', 4, 10, 'OPEN');
insert into appointment ("date", "time", doctor_id, patient_id, event_type) values('2023-06-24', '21:00:00.000', 3, 11, 'OPEN');
insert into appointment ("date", "time", doctor_id, patient_id, event_type) values('2023-06-25', '22:00:00.000', 5, 12, 'CLOSE');

insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-14 11:00:00.000', 3, 1, 'OPEN');
insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-15 12:00:00.000', 2, 2, 'OPEN');
insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-16 13:00:00.000', 3, 3, 'CLOSE');
insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-17 14:00:00.000', 4, 4, 'CLOSE');
insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-14 15:00:00.000', 3, 5, 'OPEN');
insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-19 16:00:00.000', 1, 6, 'CLOSE');
insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-14 17:00:00.000', 3, 7, 'OPEN');
insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-20 17:00:00.000', 3, 7, 'OPEN');
insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-21 18:00:00.000', 2, 8, 'OPEN');
insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-22 19:00:00.000', 3, 9, 'CLOSE');
insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-23 20:00:00.000', 4, 10, 'OPEN');
insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-24 21:00:00.000', 3, 11, 'OPEN');
insert into appointment_slot ("datetime", doctor_id, patient_id, event_type) values('2023-06-25 22:00:00.000', 5, 12, 'CLOSE');