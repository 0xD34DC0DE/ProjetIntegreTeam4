package com.team4.backend;

import com.team4.backend.model.*;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.repository.*;
import com.team4.backend.util.PBKDF2Encoder;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Order(1)
public class TestingInserterRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TestingInserterRunner.class);

    private final MonitorRepository monitorRepository;

    private final InternshipOfferRepository internshipOfferRepository;

    private final StudentRepository studentRepository;

    private final SupervisorRepository supervisorRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    private final FileMetaDataRepository fileMetaDataRepository;

    private final Lorem lorem;

    private final Set<String> studentSet;

    public TestingInserterRunner(MonitorRepository monitorRepository,
                                 InternshipOfferRepository internshipOfferRepository, StudentRepository studentRepository,
                                 SupervisorRepository supervisorRepository, PBKDF2Encoder pbkdf2Encoder,
                                 FileMetaDataRepository fileMetaDataRepository) {
        this.monitorRepository = monitorRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.studentRepository = studentRepository;
        this.supervisorRepository = supervisorRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.fileMetaDataRepository = fileMetaDataRepository;
        this.lorem = LoremIpsum.getInstance();
        this.studentSet = new HashSet<>();
        this.studentSet.add("123456789@gmail.com");
        this.studentSet.add("3643283423@gmail.com");
        this.studentSet.add("123667713@gmail.com");
    }

    @Override
    public void run(final ApplicationArguments args) throws IOException {
        studentRepository.deleteAll().subscribe();
        monitorRepository.deleteAll().subscribe();
        studentRepository.deleteAll().subscribe();
        supervisorRepository.deleteAll().subscribe();
        fileMetaDataRepository.deleteAll().subscribe();
        internshipOfferRepository.deleteAll().subscribe();

        insertInternshipOffersInternshipManagerView();
        insertStudents();
        insertMonitors();
        insertSupervisors();
        insertCvs();
    }

    private void insertStudents() {
        List<Student> students = Arrays.asList(
                Student.studentBuilder()
                        .email("123456789@gmail.com")
                        .firstName("Travis")
                        .lastName("Scott")
                        .phoneNumber("4387650987")
                        .password(pbkdf2Encoder.encode("travis123"))
                        .hasValidCv(true)
                        .appliedOffersId(new HashSet<>())
                        .exclusiveOffersId(new HashSet<>())
                        .studentState(StudentState.WAITING_FOR_RESPONSE)
                        .build(),
                Student.studentBuilder()
                        .email("3643283423@gmail.com")
                        .firstName("Jean")
                        .lastName("Jordan")
                        .phoneNumber("5143245678")
                        .password(pbkdf2Encoder.encode("jean123"))
                        .hasValidCv(false)
                        .appliedOffersId(new HashSet<>()).exclusiveOffersId(new HashSet<>())
                        .studentState(StudentState.INTERNSHIP_NOT_FOUND)
                        .build(),
                Student.studentBuilder()
                        .email("123667713@gmail.com")
                        .firstName("Farid")
                        .lastName("Shalom")
                        .phoneNumber("4385738764")
                        .password(pbkdf2Encoder.encode("farid123"))
                        .hasValidCv(false)
                        .appliedOffersId(new HashSet<>())
                        .exclusiveOffersId(new HashSet<>())
                        .studentState(StudentState.INTERNSHIP_NOT_FOUND)
                        .build(),
                Student.studentBuilder()
                        .email("902938912@gmail.com")
                        .firstName("Kevin")
                        .lastName("Alphonse")
                        .phoneNumber("4385738764")
                        .password(pbkdf2Encoder.encode("kevin123"))
                        .appliedOffersId(new HashSet<>())
                        .exclusiveOffersId(new HashSet<>())
                        .studentState(StudentState.INTERNSHIP_NOT_FOUND)
                        .hasValidCv(false)
                        .build(),
                Student.studentBuilder()
                        .email("student@gmail.com")
                        .password(pbkdf2Encoder.encode("student"))
                        .firstName("John")
                        .lastName("Doe").registrationDate(LocalDate.now())
                        .studentState(StudentState.REGISTERED)
                        .phoneNumber("123-123-1234")
                        .appliedOffersId(new HashSet<>())
                        .exclusiveOffersId(new HashSet<>() {
                            {
                                add(insertInternshipOffersStudentView());
                            }
                        }).build());

        studentRepository.saveAll(students)
                .subscribe(student -> log.info("Student has been saved : {}", student));
    }

    private void insertMonitors() {
        Monitor monitor = Monitor.monitorBuilder().email("9182738492@gmail.com")
                .password(pbkdf2Encoder.encode("lao@dkv23")).build();

        monitorRepository.save(monitor).subscribe(user -> log.info("Monitor has been saved: {}", user));
    }

    private void insertSupervisors() {
        List<Supervisor> supervisorList = Arrays.asList(
                Supervisor.supervisorBuilder()
                        .email("45673234@gmail.com").password(pbkdf2Encoder.encode("sasuke123"))
                        .firstName("Ginette")
                        .lastName("Renaud")
                        .studentEmails(new HashSet<>()).build(),
                Supervisor.supervisorBuilder()
                .email("supervisor1@gmail.com")
                        .password(pbkdf2Encoder.encode("supervisor123"))
                        .firstName("Michel")
                        .lastName("Lamarck")
                        .studentEmails(new HashSet<>(Arrays.asList("123456789@gmail.com"))).build()
        );

        supervisorRepository.saveAll(supervisorList).subscribe();
    }

    private String insertInternshipOffersStudentView() {
        InternshipOffer internshipOffer1 = InternshipOffer.builder()
                .beginningDate(LocalDate.now().plusMonths(1))
                .endingDate(LocalDate.now().plusMonths(2))
                .limitDateToApply(LocalDate.now().plusDays(15))
                .companyName("BestCo.")
                .description(lorem.getParagraphs(5, 5))
                .isExclusive(false)
                .isValidated(true)
                .maxSalary(17.50f)
                .minSalary(16.25f)
                .emailOfMonitor("9182738492@gmail.com")
                .listEmailInterestedStudents(new HashSet<>())
                .build();

        InternshipOffer internshipOffer2 = InternshipOffer.builder()
                .beginningDate(LocalDate.now().plusMonths(3))
                .endingDate(LocalDate.now().plusMonths(6))
                .limitDateToApply(LocalDate.now().plusDays(11))
                .companyName("CAE")
                .description("Some Description")
                .isExclusive(true)
                .isValidated(true).maxSalary(19.50f)
                .minSalary(19.50f)
                .emailOfMonitor("9182738492@gmail.com")
                .listEmailInterestedStudents(new HashSet<>())
                .build();

        for (int i = 0; i < 7; i++) {
            internshipOffer1.setDescription(internshipOffer1.getDescription() + i);
            internshipOfferRepository.save(internshipOffer1).block();
            internshipOffer1.setId(null);
        }
        return internshipOfferRepository.save(internshipOffer2).block().getId();
    }

    private void insertInternshipOffersInternshipManagerView() throws IOException {
        List<InternshipOffer> internshipOffers = Arrays.asList(
                InternshipOffer.builder()
                        .limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .emailOfMonitor("9182738492@gmail.com")
                        .companyName("Umaknow")
                        .description("React FrontEnd Developper")
                        .minSalary(19.0f)
                        .maxSalary(22.0f)
                        .isValidated(false)
                        .isExclusive(false)
                        .listEmailInterestedStudents(new HashSet<>())
                        .build(),
                InternshipOffer.builder().limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .emailOfMonitor("9182738492@gmail.com")
                        .companyName("Umaknow")
                        .description(".NET Developper")
                        .minSalary(19.0f)
                        .maxSalary(22.0f)
                        .isValidated(false)
                        .isExclusive(false)
                        .listEmailInterestedStudents(new HashSet<>())
                        .build(),
                InternshipOffer.builder().limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .emailOfMonitor("9182738492@gmail.com")
                        .companyName("Desjardins")
                        .description("Cobol Developper")
                        .minSalary(15.0f)
                        .maxSalary(20.0f)
                        .isValidated(false)
                        .isExclusive(false)
                        .listEmailInterestedStudents(new HashSet<>())
                        .build(),
                InternshipOffer.builder().limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .emailOfMonitor("9182738492@gmail.com")
                        .companyName("CGI")
                        .description("Fullstack Developper")
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
                        .emailOfMonitor("9182738492@gmail.com")
                        .companyName("CGI")
                        .description("Backend Developper")
                        .minSalary(20.0f)
                        .maxSalary(25.0f)
                        .isValidated(true)
                        .validationDate(null)
                        .isExclusive(false)
                        .listEmailInterestedStudents(new HashSet<>())
                        .build(),
                InternshipOffer.builder().limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .emailOfMonitor("9182738492@gmail.com")
                        .companyName("Ubisoft")
                        .description("Game Tester")
                        .minSalary(16.0f)
                        .maxSalary(18.0f)
                        .isValidated(true)
                        .validationDate(null)
                        .isExclusive(false)
                        .listEmailInterestedStudents(studentSet)
                        .build(),
                InternshipOffer.builder().limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .emailOfMonitor("9182738492@gmail.com")
                        .companyName("Banque National")
                        .description(lorem.getHtmlParagraphs(10, 15))
                        .minSalary(15.0f)
                        .maxSalary(20.0f)
                        .isValidated(false)
                        .validationDate(null)
                        .isExclusive(false)
                        .listEmailInterestedStudents(new HashSet<>())
                        .build());

        internshipOfferRepository.saveAll(internshipOffers).subscribe();
    }

    private void insertCvs() {
        List<FileMetaData> fileMetaDataList = Arrays.asList(
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/06708b00-52fe-4054-90d0-a1cd4579b0e9")
                        .userEmail("123456789@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusDays(2))
                        .build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/3b134033-2463-41b2-b9d8-05238856bfef")
                        .userEmail("123456789@gmail.com")
                        .filename("cv2.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusWeeks(2)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/8164ae59-a072-4bfe-8f03-2f350dd8086e")
                        .userEmail("123456789@gmail.com")
                        .filename("cv3.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusDays(3)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/c31a51c5-74b0-4ecb-87a3-554bf5290dac")
                        .userEmail("123456789@gmail.com")
                        .filename("cv4.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusMonths(1)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/c31a51c5-74b0-4ecb-87a3-554bf5290dac")
                        .userEmail("123456789@gmail.com")
                        .filename("cv5.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusMonths(1)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/06708b00-52fe-4054-90d0-a1cd4579b0e9")
                        .userEmail("123667713@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusWeeks(2)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/3b134033-2463-41b2-b9d8-05238856bfef")
                        .userEmail("123667713@gmail.com")
                        .filename("cv2.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now())
                        .build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/8164ae59-a072-4bfe-8f03-2f350dd8086e")
                        .userEmail("3643283423@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusDays(6)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/06708b00-52fe-4054-90d0-a1cd4579b0e9")
                        .userEmail("3643283423@gmail.com")
                        .filename("cv2.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now().minusDays(2)).build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/3b134033-2463-41b2-b9d8-05238856bfef")
                        .userEmail("902938912@gmail.com")
                        .filename("cv1.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now())
                        .build(),
                FileMetaData.builder()
                        .assetId("123456789@gmail.com/8164ae59-a072-4bfe-8f03-2f350dd8086e")
                        .userEmail("902938912@gmail.com")
                        .filename("cv2.pdf")
                        .isValid(false)
                        .isSeen(false)
                        .uploadDate(LocalDateTime.now())
                        .build());

        fileMetaDataRepository.saveAll(fileMetaDataList)
                .subscribe(f -> log.info("new cv file has been created: {}", f));
    }

}
