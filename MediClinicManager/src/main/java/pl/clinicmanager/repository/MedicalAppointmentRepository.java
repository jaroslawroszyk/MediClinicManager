package pl.clinicmanager.repository;

import pl.clinicmanager.model.MedicalAppointment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicalAppointmentRepository {
    private final List<MedicalAppointment> appointments = new ArrayList<>();

    public void save(MedicalAppointment appointment) {
        appointments.add(appointment);
    }

    public List<MedicalAppointment> findByDoctorIdAndTime(int doctorId, LocalDateTime startTime) {
        List<MedicalAppointment> result = new ArrayList<>();
        for (MedicalAppointment appointment : appointments) {
            if (appointment.getDoctorId() == doctorId && appointment.getStartTime().equals(startTime)) {
                result.add(appointment);
            }
        }
        return result;
    }
}
