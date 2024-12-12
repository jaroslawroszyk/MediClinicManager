package pl.clinicmanager.reqTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import pl.clinicmanager.model.*;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.IDoctorScheduleRepository;
import pl.clinicmanager.model.IPatientRepository;
import pl.clinicmanager.model.IDoctorRepository;
import pl.clinicmanager.model.DoctorSpecialty;
import pl.clinicmanager.repository.MedicalAppointmentRepository;
import pl.clinicmanager.service.DoctorScheduleService;
import pl.clinicmanager.service.DoctorService;
import pl.clinicmanager.service.MedicalAppointmentService;
import pl.clinicmanager.service.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;


public class ReqPatientTest {
    private PatientService patientService;
    private IPatientRepository patientRepository;
    private DoctorService doctorService;
    private IDoctorRepository doctorRepository;
    private DoctorScheduleService doctorScheduleService;
    private IDoctorScheduleRepository doctorScheduleRepository;
    private MedicalAppointmentRepository medicalAppointmentRepository;
    private MedicalAppointmentService medicalAppointmentService;
    private MedicalAppointment medicalAppointment;

    @BeforeEach
    void setUp() {
        patientRepository = mock(IPatientRepository.class);
        patientService = new PatientService(patientRepository);
        doctorRepository = mock(IDoctorRepository.class);
        doctorService = new DoctorService(doctorRepository);

        doctorScheduleRepository = mock(IDoctorScheduleRepository.class);
        doctorScheduleService = new DoctorScheduleService(doctorScheduleRepository, doctorRepository);
        medicalAppointmentRepository = mock(MedicalAppointmentRepository.class);
        medicalAppointmentService = mock(MedicalAppointmentService.class);
        medicalAppointment = mock(MedicalAppointment.class);
    }

    @Test
    void receptionistCreateNewPatientProfile() // 1.1
    {
        /*
        As a receptionist, I want to be able to create a new patient profile containing their personal and contact information needed to provide medical services.
        */
        Patient patient = new Patient(1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        patientService.addPatient(patient);

        verify(patientRepository, times(1)).save(patient);

        assertNotNull(patient.getPersonalInfo().getFirstName());
        assertNotNull(patient.getPersonalInfo().getLastName());
        assertNotNull(patient.getPesel());
        assertNotNull(patient.getPersonalInfo().getPhoneNumber());
        assertNotNull(patient.getPersonalInfo().getEmail());
    }

    @Test
    void receptionistFindByPesel() { // 1.2
        /*
         As a receptionist, I want to be able to find a patient by PESEL number and display all of his data.
         */
        Patient patient = new Patient(1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);

        when(patientRepository.findByPesel("44051401359")).thenReturn(Optional.of(patient));

        Optional<Patient> foundPatient = Optional.ofNullable(patientService.findPatientByPesel("44051401359"));

        assertTrue(foundPatient.isPresent());
        assertEquals(patient, foundPatient.get());
        assertEquals("John", foundPatient.get().getPersonalInfo().getFirstName());
        assertEquals("Doe", foundPatient.get().getPersonalInfo().getLastName());
        assertEquals("44051401359", foundPatient.get().getPesel());

        verify(patientRepository, times(1)).findByPesel("44051401359");
    }

    @Test
    void receptionistFindBySurnameAllMatchingPatients() { // 1.3
        /*
        Correct test
        As a receptionist I want to be able to search for all matching patients with a given last name and display all the details of the patients found.
         */
        Patient patient1 =
                new Patient(1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        Patient patient2 =
                new Patient(1, new PersonalInfo("Ada", "Doe", "+48123456789", "du.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 28);
        List<Patient> patients = List.of(patient1, patient2);

        when(patientRepository.findPatientsByLastName("Doe")).thenReturn(Optional.of(patients));

        List<Patient> foundPatients = patientService.findPatientByLastName("Doe");

        assertNotNull(foundPatients);
        assertEquals(2, foundPatients.size());
        assertTrue(foundPatients.contains(patient1));
        assertTrue(foundPatients.contains(patient2));

        verify(patientRepository, times(1)).findPatientsByLastName("Doe");
    }

    @Test
    void hrCreateNewDoctorProfile() { // 2.1
        /*
         Jako pracownik działu HR chcę mieć możliwość utworzenia profilu zatrudnionego lekarza uwzględniając wszystkie jego specjalizacje.
         */
        Set<DoctorSpecialty> specialties = Set.of(DoctorSpecialty.CARDIOLOGY, DoctorSpecialty.DERMATOLOGY);
        Doctor doctor = new Doctor(1, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), specialties);

        doctorService.createDoctor(doctor);

        verify(doctorRepository, times(1)).save(doctor);

        assertNotNull(doctor.getSpecialties());
        assertTrue(doctor.getSpecialties().contains(DoctorSpecialty.CARDIOLOGY));
        assertTrue(doctor.getSpecialties().contains(DoctorSpecialty.DERMATOLOGY));
    }

    @Test
    void hrAddSpecialtyToExistingDoctor() { // 2.2
        /*
         Jako pracownik działu HR chcę mieć możliwość dodania nowej specjalizacji lekarzowi, który istnieje już w systemie.
         */
        Set<DoctorSpecialty> specialties = Set.of(DoctorSpecialty.CARDIOLOGY);
        Doctor doctor = new Doctor(1, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), specialties);

        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));

        doctorService.addSpecialtyToDoctor(1, DoctorSpecialty.DERMATOLOGY);
        verify(doctorRepository, times(1)).save(doctor);

        assertEquals(2, doctor.getSpecialties().size());
        assertTrue(doctor.getSpecialties().contains(DoctorSpecialty.DERMATOLOGY));
    }

    @Test
    void receptionistFindDoctorById() { // 2.3
        /*
         Jako recepcjonista chcę mieć możliwość znalezienia lekarza po jego numerze ID oraz wyświetlenia jego danych oraz specjalizacji.
         */
        Set<DoctorSpecialty> specialties = Set.of(DoctorSpecialty.CARDIOLOGY);
        Doctor doctor = new Doctor(1, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), specialties);

        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));

        Optional<Doctor> foundDoctor = doctorService.findDoctorById(1);

        assertTrue(foundDoctor.isPresent());
        assertEquals(doctor, foundDoctor.get());
        assertEquals("Anna", foundDoctor.get().getPersonalInfo().getFirstName());
        assertEquals("Nowak", foundDoctor.get().getPersonalInfo().getLastName());
        assertTrue(foundDoctor.get().getSpecialties().contains(DoctorSpecialty.CARDIOLOGY));

        verify(doctorRepository, times(1)).findById(1);
    }

    @Test
    void receptionistFindDoctorsBySpecialty() { // 2.4
        /*
         Jako recepcjonista chcę mieć możliwość znalezienia wszystkich lekarzy o określonej specjalizacji oraz wyświetlenia ich danych oraz ID.
         */
        Doctor doctor1 = new Doctor(1, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), Set.of(DoctorSpecialty.CARDIOLOGY));
        Doctor doctor2 = new Doctor(2, new PersonalInfo("Jan", "Kowalski", "987654321", "jan.kowalski@example.com", "Warszawa"), Set.of(DoctorSpecialty.CARDIOLOGY));
        Set<Doctor> doctors = Set.of(doctor1, doctor2);

        when(doctorRepository.findBySpecialty(DoctorSpecialty.CARDIOLOGY)).thenReturn(doctors);

        Set<Doctor> foundDoctors = doctorService.findDoctorsBySpecialty(DoctorSpecialty.CARDIOLOGY);

        assertNotNull(foundDoctors);
        assertEquals(2, foundDoctors.size());
        assertTrue(foundDoctors.contains(doctor1));
        assertTrue(foundDoctors.contains(doctor2));

        verify(doctorRepository, times(1)).findBySpecialty(DoctorSpecialty.CARDIOLOGY);
    }

    @Test
    void managerCreateDoctorSchedule() { // 4.1
        /*
        Jako kierownik placówki chcę mieć możliwość tworzenia grafików dla wszystkich pracujących lekarzy.
        Pojedynczy grafik powinien zawierać godziny pracy wybranego lekarza w wybranym dniu.
        */
        Doctor doctor = new Doctor(1, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), Set.of(DoctorSpecialty.CARDIOLOGY));
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        int doctorId = 1;
        LocalDateTime startTime = LocalDateTime.of(2024, 12, 10, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 12, 10, 17, 0);
        DoctorSchedule schedule = new DoctorSchedule(doctor, startTime, endTime);

        doctorScheduleService.createSchedule(schedule);

        verify(doctorScheduleRepository, times(1)).save(schedule);

        assertEquals(doctorId, schedule.getDoctorId());
        assertEquals(startTime, schedule.getStartTime());
        assertEquals(endTime, schedule.getEndTime());
    }

    @Test
    void receptionistGetSchedulesForDoctorForNextWeek() {
    /*
     4.2 Jako pracownik recepcji chcę móc pobrać wszystkie utworzone grafiki wybranego lekarza na najbliższy tydzień,
     abym mógł sprawdzić w jakich godzinach przyjmuje pacjentów.
     */
        int doctorId = 1;

        Doctor doctor = new Doctor(doctorId, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), Set.of(DoctorSpecialty.CARDIOLOGY));
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        LocalDateTime weekStart = LocalDate.now().atStartOfDay();

        List<DoctorSchedule> schedules = List.of(
                new DoctorSchedule(doctor, LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0), LocalDateTime.now().plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0)),
                new DoctorSchedule(doctor, LocalDateTime.now().plusDays(3).withHour(10).withMinute(0).withSecond(0).withNano(0), LocalDateTime.now().plusDays(3).withHour(14).withMinute(0).withSecond(0).withNano(0)),
                new DoctorSchedule(doctor, LocalDateTime.now().plusDays(5).withHour(8).withMinute(0).withSecond(0).withNano(0), LocalDateTime.now().plusDays(5).withHour(12).withMinute(0).withSecond(0).withNano(0))
        );

        when(doctorScheduleRepository.findSchedulesByDoctorIdAndWeek(doctorId, weekStart)).thenReturn(schedules);

        List<DoctorSchedule> foundSchedules = doctorScheduleService.getSchedulesForNextWeek(doctorId, weekStart);

        assertNotNull(foundSchedules);
        assertEquals(3, foundSchedules.size());
        assertEquals(doctorId, foundSchedules.get(0).getDoctorId());

        LocalDateTime expectedStartTime = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime expectedEndTime = LocalDateTime.now().plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0);
        assertEquals(expectedStartTime, foundSchedules.get(0).getStartTime());
        assertEquals(expectedEndTime, foundSchedules.get(0).getEndTime());

        verify(doctorScheduleRepository, times(1)).findSchedulesByDoctorIdAndWeek(doctorId, weekStart);
    }

//    @Test
//    void receptionistCreateMedicalAppointment() { // 5.1
//    /*
//     Jako recepcjonista chcę móc umówić pacjenta na wizytę lekarską, wybierając dostępnego lekarza, termin oraz pacjenta.
//     */
//
//        // Tworzenie danych testowych
//        Patient patient = new Patient(1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
//        Doctor doctor = new Doctor(1, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), Set.of(DoctorSpecialty.CARDIOLOGY));
//        LocalDateTime appointmentTime = LocalDateTime.of(2024, 12, 15, 10, 0);
//
//        // Konfiguracja mocków
//        when(patientService.findPatientByPesel("44051401359")).thenReturn(patient); // Mockowanie metody w PatientService
//        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
//
//        // Wywołanie metody do zarejestrowania wizyty
//        medicalAppointmentService.bookAppointment(patient.getPesel(), doctor.getId(), appointmentTime);
//
//        // Weryfikacja, czy metoda save() została wywołana na mocku repozytorium
//        verify(medicalAppointmentRepository, times(1)).save(any(MedicalAppointment.class));
//
//        // Sprawdzenie, czy wywołana wizyta ma poprawne dane
//        ArgumentCaptor<MedicalAppointment> captor = ArgumentCaptor.forClass(MedicalAppointment.class);
//        verify(medicalAppointmentRepository).save(captor.capture());
//        MedicalAppointment capturedAppointment = captor.getValue();
//
//        assertNotNull(capturedAppointment);
//        assertEquals(patient, capturedAppointment.getPatient());
//        assertEquals(doctor, capturedAppointment.getDoctor());
//        assertEquals(appointmentTime, capturedAppointment.getStartTime());
//    }

//    @Test
//    void receptionistCreateMedicalAppointment() { // 5.1
//    /*
//     Jako recepcjonista chcę móc umówić pacjenta na wizytę lekarską, wybierając dostępnego lekarza, termin oraz pacjenta.
//     */
//
//        // Tworzenie danych testowych
//        Patient patient = new Patient(1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
//        Doctor doctor = new Doctor(1, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), Set.of(DoctorSpecialty.CARDIOLOGY));
//        LocalDateTime appointmentTime = LocalDateTime.of(2024, 12, 15, 10, 0);
//
//        // Konfiguracja mocków
//        when(patientRepository.findByPesel("44051401359")).thenReturn(Optional.of(patient));
//        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
//
//        // Wywołanie metody do zarejestrowania wizyty
//        MedicalAppointment appointment = new MedicalAppointment(patient, doctor, appointmentTime);
//        medicalAppointmentService.bookAppointment(patient.getPesel(), doctor.getId(), appointmentTime);
//
//        // Weryfikacja, czy metoda save() została wywołana na mocku repozytorium
//        verify(medicalAppointmentRepository, times(1)).save(appointment);
//
//        // Sprawdzenie, czy obiekt appointment został utworzony i ma odpowiednie dane
//        assertNotNull(appointment);
////        assertEquals(patient.getId(), appointment.getPatientId());
////        assertEquals(doctor.getId(), appointment.getDoctorId());
//        assertEquals(appointmentTime, appointment.getStartTime());
//    }


}
