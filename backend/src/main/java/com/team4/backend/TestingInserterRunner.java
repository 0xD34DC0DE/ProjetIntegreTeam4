package com.team4.backend;

import com.team4.backend.model.*;
import com.team4.backend.model.enums.NotificationType;
import com.team4.backend.model.enums.Role;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.repository.*;
import com.team4.backend.util.PBKDF2Encoder;
import com.team4.backend.util.SemesterUtil;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Order(2)
public class TestingInserterRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TestingInserterRunner.class);

    private final UserRepository userRepository;

    private final MonitorRepository monitorRepository;

    private final InternshipOfferRepository internshipOfferRepository;

    private final StudentRepository studentRepository;

    private final SupervisorRepository supervisorRepository;

    private final InternshipContractRepository internshipContractRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    private final ProfileImageRepository profileImageRepository;

    private final FileMetaDataRepository fileMetaDataRepository;

    private final InternshipRepository internshipRepository;

    private final NotificationRepository notificationRepository;

    private final EvaluationRepository evaluationRepository;

    private final InternshipManagerRepository internshipManagerRepository;

    private final SemesterRepository semesterRepository;

    private final Lorem lorem;

    private final Set<String> studentSet;

    private String internshipOfferId;

    private final Set<LocalDate> evaluationsDates;

    public TestingInserterRunner(UserRepository userRepository,
                                 MonitorRepository monitorRepository,
                                 InternshipOfferRepository internshipOfferRepository,
                                 StudentRepository studentRepository,
                                 SupervisorRepository supervisorRepository,
                                 EvaluationRepository evaluationRepository,
                                 InternshipContractRepository internshipContractRepository,
                                 PBKDF2Encoder pbkdf2Encoder,
                                 ProfileImageRepository profileImageRepository, FileMetaDataRepository fileMetaDataRepository,
                                 InternshipManagerRepository internshipManagerRepository,
                                 InternshipRepository internshipRepository,
                                 NotificationRepository notificationRepository,
                                 SemesterRepository semesterRepository) {
        this.userRepository = userRepository;
        this.monitorRepository = monitorRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.studentRepository = studentRepository;
        this.supervisorRepository = supervisorRepository;
        this.internshipContractRepository = internshipContractRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.profileImageRepository = profileImageRepository;
        this.fileMetaDataRepository = fileMetaDataRepository;
        this.internshipRepository = internshipRepository;
        this.evaluationRepository = evaluationRepository;
        this.internshipManagerRepository = internshipManagerRepository;
        this.semesterRepository = semesterRepository;
        this.notificationRepository = notificationRepository;
        this.evaluationsDates = new TreeSet<>();
        this.evaluationsDates.add(LocalDate.of(2019, 4, 4));
        this.evaluationsDates.add(LocalDate.of(2021, 9, 4));
        this.evaluationsDates.add(LocalDate.now());
        this.lorem = LoremIpsum.getInstance();
        this.studentSet = new HashSet<>();
        this.studentSet.add("123456789@gmail.com");
        this.studentSet.add("3643283423@gmail.com");
        this.studentSet.add("123667713@gmail.com");
    }

    @Override
    public void run(final ApplicationArguments args) {
        userRepository.deleteAllByRoleEquals(Role.STUDENT).subscribe();
        userRepository.deleteAllByRoleEquals(Role.MONITOR).subscribe();
        userRepository.deleteAllByRoleEquals(Role.SUPERVISOR).subscribe();
        fileMetaDataRepository.deleteAll().subscribe(System.err::println);
        internshipOfferRepository.deleteAll().subscribe();
        internshipRepository.deleteAll().subscribe();
        evaluationRepository.deleteAll().subscribe();
        internshipContractRepository.deleteAll().subscribe();
        semesterRepository.deleteAll().subscribe();
        notificationRepository.deleteAll().subscribe();
        profileImageRepository.deleteAll().subscribe();

        insertSemesters();
        insertInternshipOffersInternshipManagerView();
        insertStudents();
        insertMonitors();
        insertSupervisors();
        insertCvs();
        insertNotifications();
        insertInternships();
        insertProfileImages();
    }

    private void insertProfileImages() {
        List<ProfileImage> profileImages = Arrays.asList(
                ProfileImage.profileImageBuilder()
                        .fileName("filename1")
                        .uploaderEmail("123")
                        .image(null)
                        .build(),
                ProfileImage.profileImageBuilder()
                        .fileName("filename2")
                        .uploaderEmail("abc")
                        .image(null)
                        .build()
        );

        profileImageRepository.saveAll(profileImages).subscribe();
    }

    private void insertSemesters() {
        semesterRepository.saveAll(SemesterUtil.getSemesters(LocalDateTime.now())).subscribe();
    }

    private void insertNotifications() {
        List<Notification> notifications = Arrays.asList(
                Notification.notificationBuilder()
                        .content("CV refusé!")
                        .title("Notification")
                        .receiverIds(Set.of(Objects.requireNonNull(studentRepository.findByEmail("student@gmail.com").map(User::getId).block())))
                        .creationDate(LocalDateTime.now())
                        .notificationType(NotificationType.SHOW_CV)
                        .seenIds(Set.of())
                        .data(Collections.emptyMap())
                        .build(),
                Notification.notificationBuilder()
                        .content("CV Accepté!")
                        .title("Notification")
                        .seenIds(Set.of())
                        .notificationType(NotificationType.SHOW_CV)
                        .receiverIds(Set.of(Objects.requireNonNull(studentRepository.findByEmail("123456789@gmail.com").map(User::getId).block())))
                        .data(Collections.singletonMap("id", "test"))
                        .creationDate(LocalDateTime.now())
                        .build(),
                Notification.notificationBuilder()
                        .content("Nouvelle offre de stage disponible")
                        .title("Offre de stage")
                        .seenIds(Set.of())
                        .notificationType(NotificationType.NEW_INTERNSHIP_OFFER)
                        .receiverIds(Set.of(Objects.requireNonNull(studentRepository.findByEmail("123456789@gmail.com").map(User::getId).block())))
                        .data(Collections.singletonMap("id", "test"))
                        .creationDate(LocalDateTime.now())
                        .build()
        );

        notificationRepository
                .saveAll(notifications).subscribe(notification -> log.info("Notifications has been saved: {}", notification));
    }

    private void insertInternships() {
        List<Internship> internships = Arrays.asList(
                Internship.builder()
                        .monitorEmail("9182738492@gmail.com")
                        .internshipManagerEmail("manager1@gmail.com")
                        .studentEmail("studentInternFound@gmail.com")
                        .beginningDate(LocalDate.now().plusDays(15))
                        .endingDate(LocalDate.now().plusMonths(4))
                        .build()
        );
        internshipRepository.saveAll(internships)
                .subscribe(internship -> log.info("Internship has been saved : {}", internship));
        insertInternshipContract();
    }

    private void insertInternshipContract() {
        String studentId = studentRepository.findByEmail("student@gmail.com").block().getId();
        String studentId1 = studentRepository.findByEmail("123456789@gmail.com").block().getId();
        String studentId2 = studentRepository.findByEmail("123667713@gmail.com").block().getId();
        String monitorId = monitorRepository.findByEmail("monitor@gmail.com").block().getId();
        String monitorId1 = monitorRepository.findByEmail("monitor1@gmail.com").block().getId();
        String internshipManagerId = internshipManagerRepository.findByEmail("manager1@gmail.com")
                .block().getId();
        String internshipManagerId1 = internshipManagerRepository.findByEmail("manager2@gmail.com")
                .block().getId();

        List<InternshipContract> internshipContracts = Arrays.asList(InternshipContract.builder()
                        .internshipOfferId(internshipOfferId)
                        .address("123, Somewhere St., Montreal, Quebec")
                        .beginningDate(LocalDate.now().plusWeeks(1))
                        .endingDate(LocalDate.now().plusWeeks(5))
                        .dailySchedule("8:00 à 16:00")
                        .hourlyRate(21.50f)
                        .hoursPerWeek(40.0f)
                        .internTasks("Tasks")
                        .studentSignature(Signature.builder().userId(studentId).hasSigned(false).build())
                        .monitorSignature(Signature.builder().signDate(LocalDate.now()).userId(monitorId).hasSigned(true).build())
                        .internshipManagerSignature(Signature.builder().userId(internshipManagerId).hasSigned(false).build())
                        .build(),
                InternshipContract.builder()
                        .address("456, Perdu à la campagne, St-Isidore, Quebec")
                        .beginningDate(LocalDate.now())
                        .endingDate(LocalDate.now().plusWeeks(2))
                        .dailySchedule("4:00 à 21:00")
                        .hourlyRate(520.0f)
                        .hoursPerWeek(80.0f)
                        .internTasks("Work")
                        .studentSignature(Signature.builder().userId(studentId1).hasSigned(true).build())
                        .monitorSignature(Signature.builder().signDate(LocalDate.now()).userId(monitorId1).hasSigned(true).build())
                        .internshipManagerSignature(Signature.builder().userId(internshipManagerId1).hasSigned(true).build())
                        .build(),
                InternshipContract.builder()
                        .address("789, Quelque part, Montréal, Québec")
                        .beginningDate(LocalDate.now())
                        .endingDate(LocalDate.now().plusWeeks(2))
                        .dailySchedule("10:00 à 15:00")
                        .hourlyRate(10.0f)
                        .hoursPerWeek(15.0f)
                        .internTasks("Work")
                        .studentSignature(Signature.builder().userId(studentId2).hasSigned(true).build())
                        .monitorSignature(Signature.builder().signDate(LocalDate.now()).userId(monitorId1).hasSigned(true).build())
                        .internshipManagerSignature(Signature.builder().userId(internshipManagerId1).hasSigned(true).build())
                        .build()
        );

        internshipContractRepository.saveAll(internshipContracts).subscribe(i -> log.info("Internship has been saved : {}", i));
    }

    private void insertStudents() {
        List<Student> students = Arrays.asList(
                Student.studentBuilder()
                        .id("61943f31f44ecd30d4f0b470")
                        .email("123456789@gmail.com")
                        .firstName("Travis")
                        .lastName("Scott")
                        .phoneNumber("4387650987")
                        .password(pbkdf2Encoder.encode("travis123"))
                        .hasValidCv(true)
                        .hasCv(true)
                        .appliedOffersId(new HashSet<>())
                        .exclusiveOffersId(new HashSet<>())
                        .interviewsDate(new TreeSet<>(Arrays.asList(LocalDate.now().plusWeeks(2))))
                        .studentState(StudentState.WAITING_FOR_RESPONSE)
                        .evaluationsDates(evaluationsDates)
                        .build(),
                Student.studentBuilder()
                        .id("1e0dd146524a11ecbf630242ac130002")
                        .email("3643283423@gmail.com")
                        .firstName("Jean")
                        .lastName("Jordan")
                        .phoneNumber("5143245678")
                        .password(pbkdf2Encoder.encode("jean123"))
                        .hasValidCv(false)
                        .hasCv(true)
                        .evaluationsDates(new TreeSet<>())
                        .appliedOffersId(new HashSet<>())
                        .exclusiveOffersId(new HashSet<>())
                        .interviewsDate(new TreeSet<>(Arrays.asList(LocalDate.now().plusWeeks(2))))
                        .studentState(StudentState.WAITING_INTERVIEW)
                        .build(),
                Student.studentBuilder()
                        .id("524a11ecbf63021ecbf630242ac130002")
                        .email("123667713@gmail.com")
                        .firstName("Farid")
                        .lastName("Shalom")
                        .phoneNumber("4385738764")
                        .password(pbkdf2Encoder.encode("farid123"))
                        .hasValidCv(false)
                        .hasCv(true)
                        .appliedOffersId(new HashSet<>())
                        .exclusiveOffersId(new HashSet<>())
                        .evaluationsDates(new TreeSet<>())
                        .interviewsDate(new TreeSet<>(Arrays.asList(LocalDate.now().plusWeeks(2))))
                        .studentState(StudentState.WAITING_INTERVIEW)
                        .build(),
                Student.studentBuilder()
                        .id("399628a0524a11ecbf630242ac130002")
                        .email("902938912@gmail.com")
                        .firstName("Kevin")
                        .lastName("Alphonse")
                        .phoneNumber("4385738764")
                        .password(pbkdf2Encoder.encode("kevin123"))
                        .appliedOffersId(new HashSet<>())
                        .exclusiveOffersId(new HashSet<>())
                        .interviewsDate(new TreeSet<>())
                        .evaluationsDates(new TreeSet<>())
                        .studentState(StudentState.WAITING_INTERVIEW)
                        .hasValidCv(false)
                        .hasCv(true)
                        .build(),
                Student.studentBuilder()
                        .id("39962524a11ecbf63020242ac130002")
                        .email("nocv@gmail.com")
                        .firstName("no")
                        .lastName("cv")
                        .phoneNumber("4385738764")
                        .password(pbkdf2Encoder.encode("student"))
                        .appliedOffersId(new HashSet<>())
                        .exclusiveOffersId(new HashSet<>())
                        .evaluationsDates(new TreeSet<>())
                        .studentState(StudentState.WAITING_INTERVIEW)
                        .hasValidCv(false)
                        .hasCv(true)
                        .build(),
                Student.studentBuilder()
                        .id("5b1765ca0524a11ecbf630242ac130002")
                        .email("student@gmail.com")
                        .password(pbkdf2Encoder.encode("student"))
                        .firstName("Shia")
                        .lastName("LaBeouf").registrationDate(LocalDate.now())
                        .studentState(StudentState.NO_INTERVIEW)
                        .hasValidCv(true)
                        .hasCv(true)
                        .phoneNumber("123-123-1234")
                        .appliedOffersId(new HashSet<>())
                        .evaluationsDates(new TreeSet<>())
                        .interviewsDate(new TreeSet<>())
                        .exclusiveOffersId(new HashSet<>() {
                            {
                                add(insertInternshipOffersStudentView());
                            }
                        }).build(),
                Student.studentBuilder()
                        .id("5b176e760524a11ecbf630242ac130002")
                        .email("studentInternFound@gmail.com")
                        .firstName("Maxime")
                        .lastName("Dupuis")
                        .phoneNumber("438-422-3344")
                        .password(pbkdf2Encoder.encode("maxime123"))
                        .hasValidCv(true)
                        .hasCv(true)
                        .evaluationsDates(new TreeSet<>())
                        .interviewsDate(new TreeSet<>())
                        .appliedOffersId(new HashSet<>())
                        .exclusiveOffersId(new HashSet<>())
                        .studentState(StudentState.INTERNSHIP_FOUND)
                        .build());


        studentRepository.saveAll(students)
                .subscribe(student -> log.info("Student has been saved : {}", student));
    }

    private void insertMonitors() {
        Monitor monitor = Monitor.monitorBuilder().email("monitor@gmail.com")
                .password(pbkdf2Encoder.encode("monitor"))
                .firstName("Giorno")
                .companyName("Google")
                .phoneNumber("438-998-7654")
                .lastName("Giovanna")
                .build();

        Monitor monitor2 = Monitor.monitorBuilder().email("monitor1@gmail.com")
                .password(pbkdf2Encoder.encode("monitor1"))
                .firstName("Amir")
                .companyName("CAE")
                .phoneNumber("438-529-0976")
                .lastName("Fernandez")
                .build();

        monitorRepository.saveAll(Arrays.asList(monitor, monitor2)).subscribe(user -> log.info("Monitor has been saved: {}", user));
    }

    private void insertSupervisors() {
        List<Supervisor> supervisorList = Arrays.asList(
                Supervisor.supervisorBuilder()
                        .email("supervisor@gmail.com").password(pbkdf2Encoder.encode("supervisor"))
                        .firstName("Ginette")
                        .lastName("Renaud")
                        .phoneNumber("514-334-5667")
                        .studentTimestampedEntries(new HashSet<>()).build(),
                Supervisor.supervisorBuilder()
                        .email("supervisor1@gmail.com")
                        .password(pbkdf2Encoder.encode("supervisor1"))
                        .firstName("Michel")
                        .lastName("Lamarck")
                        .phoneNumber("514-324-5667")
                        .studentTimestampedEntries(new TreeSet<>(Arrays.asList(
                                new TimestampedEntry("3643283423@gmail.com", LocalDateTime.now())))).build(),
                Supervisor.supervisorBuilder()
                        .email("supervisor2@gmail.com")
                        .password(pbkdf2Encoder.encode("supervisor1"))
                        .firstName("Kendrick")
                        .lastName("Lamar")
                        .phoneNumber("514-545-5667")
                        .studentTimestampedEntries(new TreeSet<>(Arrays.asList(
                                new TimestampedEntry("studentInternFound@gmail.com", LocalDateTime.now()),
                                new TimestampedEntry("123456789@gmail.com", LocalDateTime.now())))).build()
        );

        supervisorRepository.saveAll(supervisorList).subscribe();
    }

    private String insertInternshipOffersStudentView() {
        InternshipOffer internshipOffer1 = InternshipOffer.builder()
                .beginningDate(LocalDate.now().plusMonths(1))
                .endingDate(LocalDate.now().plusMonths(2))
                .limitDateToApply(LocalDate.now().plusDays(15))
                .companyName("BestCo.")
                .title("Développeur Senior Java")
                .description(lorem.getParagraphs(5, 5))
                .isExclusive(false)
                .isValidated(true)
                .maxSalary(17.50f)
                .minSalary(16.25f)
                .monitorEmail("monitor@gmail.com")
                .listEmailInterestedStudents(new HashSet<>())
                .build();

        InternshipOffer internshipOffer2 = InternshipOffer.builder()
                .beginningDate(LocalDate.now().plusMonths(3))
                .endingDate(LocalDate.now().plusMonths(6))
                .limitDateToApply(LocalDate.now().plusDays(11))
                .companyName("CAE")
                .title("Développeur Unity")
                .description("Some Description")
                .isExclusive(true)
                .isValidated(true).maxSalary(19.50f)
                .minSalary(19.50f)
                .monitorEmail("monitor@gmail.com")
                .listEmailInterestedStudents(new HashSet<>())
                .build();

        for (int i = 0; i < 7; i++) {
            internshipOffer1.setDescription(internshipOffer1.getDescription() + i);
            internshipOfferRepository.save(internshipOffer1).block();
            internshipOffer1.setId(null);
        }
        return internshipOfferRepository.save(internshipOffer2).block().getId();
    }

    private void insertInternshipOffersInternshipManagerView() {
        Set<String> offer1InterestedStudent = new HashSet<>();
        offer1InterestedStudent.add("student@gmail.com");

        InternshipOffer firstInternshipOffer = InternshipOffer.builder()
                .limitDateToApply(LocalDate.now())
                .beginningDate(LocalDate.now().plusWeeks(1))
                .endingDate(LocalDate.now().plusWeeks(4))
                .monitorEmail("monitor@gmail.com")
                .title("Développeur Senior Cobol")
                .companyName("Google")
                .description(lorem.getParagraphs(2, 5))
                .minSalary(19.0f)
                .maxSalary(22.0f)
                .isValidated(true)
                .isExclusive(false)
                .listEmailInterestedStudents(offer1InterestedStudent)
                .emailOfApprovingInternshipManager("manager1@gmail.com")
                .build();

        List<InternshipOffer> internshipOffers = Arrays.asList(
                InternshipOffer.builder().limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .monitorEmail("monitor@gmail.com")
                        .title("Développeur React")
                        .companyName("Umaknow")
                        .description(lorem.getParagraphs(2, 5))
                        .minSalary(19.0f)
                        .maxSalary(22.0f)
                        .isValidated(false)
                        .isExclusive(false)
                        .listEmailInterestedStudents(new HashSet<>())
                        .build(),
                InternshipOffer.builder().limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .monitorEmail("monitor@gmail.com")
                        .title("Développeur .NET")
                        .companyName("Desjardins")
                        .description(lorem.getParagraphs(2, 5))
                        .minSalary(15.0f)
                        .maxSalary(20.0f)
                        .isValidated(false)
                        .isExclusive(false)
                        .listEmailInterestedStudents(new HashSet<>())
                        .build(),
                InternshipOffer.builder().limitDateToApply(LocalDate.now().plusMonths(5))
                        .beginningDate(LocalDate.now().plusMonths(5).plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(8))
                        .monitorEmail("monitor@gmail.com")
                        .title("Analyste de données")
                        .companyName("CGI")
                        .description(lorem.getParagraphs(2, 5))
                        .minSalary(20.0f)
                        .maxSalary(25.0f)
                        .isValidated(false)
                        .validationDate(null)
                        .isExclusive(false)
                        .listEmailInterestedStudents(new HashSet<>())
                        .build(),
                InternshipOffer.builder().limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .monitorEmail("monitor@gmail.com")
                        .title("Développeur Senior Java")
                        .companyName("CGI")
                        .description(lorem.getParagraphs(2, 5))
                        .minSalary(20.0f)
                        .maxSalary(25.0f)
                        .isValidated(true)
                        .validationDate(null)
                        .isExclusive(false)
                        .listEmailInterestedStudents(new HashSet<>())
                        .emailOfApprovingInternshipManager("manager1@gmail.com")
                        .build(),
                InternshipOffer.builder().limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .monitorEmail("monitor@gmail.com")
                        .title("Développeur Fullstack")
                        .companyName("Desjardins")
                        .description(lorem.getParagraphs(2, 5))
                        .minSalary(16.0f)
                        .maxSalary(18.0f)
                        .isValidated(true)
                        .validationDate(null)
                        .isExclusive(false)
                        .listEmailInterestedStudents(studentSet)
                        .emailOfApprovingInternshipManager("manager1@gmail.com")
                        .build(),
                InternshipOffer.builder().limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .monitorEmail("monitor@gmail.com")
                        .title("Développeur Junior Angular")
                        .companyName("Banque National")
                        .description(lorem.getParagraphs(10, 15))
                        .minSalary(15.0f)
                        .maxSalary(20.0f)
                        .isValidated(false)
                        .validationDate(null)
                        .isExclusive(false)
                        .listEmailInterestedStudents(new HashSet<>())
                        .build());

        internshipOfferRepository.save(firstInternshipOffer)
                .subscribe(internshipOffer -> internshipOfferId = internshipOffer.getId());
        internshipOfferRepository.saveAll(internshipOffers).subscribe();
    }

    private void insertCvs() {
        List<FileMetaData> fileMetaDataList = Arrays.asList(
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("123456789@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(true)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusDays(2))
                        .build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("123456789@gmail.com")
                        .filename("cv2.pdf")
                        .isValid(true)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusWeeks(2)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("123456789@gmail.com")
                        .filename("cv3.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusDays(3)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("123456789@gmail.com")
                        .filename("cv4.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusMonths(1)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("123456789@gmail.com")
                        .filename("cv5.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusMonths(1)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("123667713@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(true)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusWeeks(2)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("123667713@gmail.com")
                        .filename("cv2.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now())
                        .build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("3643283423@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusDays(6)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("3643283423@gmail.com")
                        .filename("cv2.pdf")
                        .isValid(true)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusDays(2)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("902938912@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now())
                        .build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("student@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(true)
                        .isSeen(true)
                        .uploadDate(LocalDateTime.now().minusWeeks(2))
                        .build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("student@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(true)
                        .isSeen(true)
                        .uploadDate(LocalDateTime.now().minusDays(3))
                        .build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("902938912@gmail.com")
                        .filename("cv2.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusWeeks(1))
                        .build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("student@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(false)
                        .isSeen(true)
                        .rejectionExplanation("Rejet CV étudiant")
                        .uploadDate(LocalDateTime.now())
                        .build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("student@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(false)
                        .isSeen(true)
                        .rejectionExplanation("Rejet CV test2")
                        .uploadDate(LocalDateTime.now())
                        .build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("student@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(false)
                        .isSeen(true)
                        .rejectionExplanation("Rejet Cv par Manager")
                        .uploadDate(LocalDateTime.now().minusMinutes(10))
                        .build(), FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("student@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(true)
                        .isSeen(true)
                        .rejectionExplanation("Rejet CV test")
                        .uploadDate(LocalDateTime.now().minusHours(2))
                        .build(), FileMetaData.builder()
                        .assetId("123456789@gmail.com/340942a5-b54f-4611-8d68-6cff6f303121")
                        .userEmail("student@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusDays(5))
                        .build()
        );


        fileMetaDataRepository.saveAll(fileMetaDataList)
                .subscribe(f -> log.info("new cv file has been created: {}", f));
    }

}
