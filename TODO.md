# TODO (BlackboxAI)

- [ ] Implement Step 3 (Appointment): set BOOKED status + generate queueNumber automatically + validate future date.
- [ ] Update AppointmentRepository to support queue number calculation (clinician + date).
- [ ] Update PatientService.makeAppointment to use queue number + BOOKED.
- [ ] Implement Step 4 (New Visit) endpoint/service method to create Visit from an Appointment.
- [ ] Add DTO(s) for creating Visit (chiefComplaint/duration/severity/associatedSymptoms/onset/visitDate/triageLevel optional).
- [ ] Wire Clinician flow: add method in ClinicianService to create Visit; add controller endpoint.
- [ ] Ensure exceptions are thrown correctly and responses are consistent.
- [ ] Run mvn test (or mvn -q test) to confirm compilation.

