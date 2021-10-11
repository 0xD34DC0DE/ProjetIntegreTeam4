package com.team4.backend;

import com.team4.backend.model.*;
import com.team4.backend.repository.*;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Monitor;
import com.team4.backend.model.Student;
import com.team4.backend.model.User;
import com.team4.backend.model.enums.Role;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.repository.MonitorRepository;
import com.team4.backend.repository.StudentRepository;
import com.team4.backend.repository.UserRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public TestingInserterRunner(MonitorRepository monitorRepository,
                                 InternshipOfferRepository internshipOfferRepository,
                                 StudentRepository studentRepository,
                                 SupervisorRepository supervisorRepository,
                                 PBKDF2Encoder pbkdf2Encoder,
                                 FileMetaDataRepository fileMetaDataRepository) {
        this.monitorRepository = monitorRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.studentRepository = studentRepository;
        this.supervisorRepository = supervisorRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.fileMetaDataRepository = fileMetaDataRepository;
    }

    @Override
    public void run(final ApplicationArguments args) {
        studentRepository.deleteAll().subscribe();
        monitorRepository.deleteAll().subscribe();
        studentRepository.deleteAll().subscribe();
        fileMetaDataRepository.deleteAll().subscribe();
        internshipOfferRepository.deleteAll().subscribe();

        insertInternshipOffers();
        insertStudents();
        insertMonitors();
        insertSupervisors();
        insertCvs();
    }

    private void insertStudents() {
        List<Student> students = Arrays.asList(
                Student.studentBuilder().email("123456789@gmail.com").firstName("Travis").lastName("Scott").phoneNumber("4387650987").password(pbkdf2Encoder.encode("travis123")).hasValidCv(false).build(),
                Student.studentBuilder().email("3643283423@gmail.com").firstName("Jean").lastName("Jordan").phoneNumber("5143245678").password(pbkdf2Encoder.encode("jean123")).hasValidCv(false).build(),
                Student.studentBuilder().email("123667713@gmail.com").firstName("Farid").lastName("Shalom").phoneNumber("4385738764").password(pbkdf2Encoder.encode("farid123")).hasValidCv(false).build(),
                Student.studentBuilder().email("902938912@gmail.com").firstName("Kevin").lastName("Alphonse").phoneNumber("4385738764").password(pbkdf2Encoder.encode("kevin123")).hasValidCv(false).build(),
                Student.studentBuilder()
                        .email("student@gmail.com")
                        .password(pbkdf2Encoder.encode("student"))
                        .firstName("John")
                        .lastName("Doe")
                        .registrationDate(LocalDate.now())
                        .studentState(StudentState.REGISTERED)
                        .phoneNumber("123-123-1234")
                        .exclusiveOffersId(Collections.singleton(insertInternshipOffers2()))
                        .build()
        );

        studentRepository.saveAll(students).subscribe(student -> log.info("Student has been saved : {}", student));
    }

    private void insertMonitors() {
        Monitor monitor = Monitor.monitorBuilder()
                .email("9182738492@gmail.com").password(pbkdf2Encoder.encode("lao@dkv23")).build();

        monitorRepository.save(monitor).subscribe(user -> log.info("Monitor has been saved: {}", user));
    }

    private void insertSupervisors() {
        List<Supervisor> supervisorList = Collections.singletonList(
                Supervisor.supervisorBuilder()
                        .email("45673234@gmail.com").password(pbkdf2Encoder.encode("sasuke123")).build()
        );

        supervisorRepository.saveAll(supervisorList).subscribe();
    }

    private String insertInternshipOffers2() {
        InternshipOffer internshipOffer1 =
                InternshipOffer.builder()
                        .beginningDate(LocalDate.now().plusMonths(1))
                        .endingDate(LocalDate.now().plusMonths(2))
                        .limitDateToApply(LocalDate.now().plusDays(15))
                        .companyName("BestCo.")
                        .description("Description")
                        .isExclusive(false)
                        .isValidated(true)
                        .maxSalary(17.50f)
                        .minSalary(16.25f)
                        .emailOfMonitor("9182738492@gmail.com")
                        .listEmailInterestedStudents(Collections.emptyList())
                        .build();

        InternshipOffer internshipOffer2 = InternshipOffer.builder()
                .beginningDate(LocalDate.now().plusMonths(3))
                .endingDate(LocalDate.now().plusMonths(6))
                .limitDateToApply(LocalDate.now().plusDays(11))
                .companyName("CAE")
                .description("Some Description")
                .isExclusive(true)
                .isValidated(true)
                .maxSalary(19.50f)
                .minSalary(19.50f)
                .emailOfMonitor("9182738492@gmail.com")
                .listEmailInterestedStudents(Collections.emptyList())
                .build();

        for (int i = 0; i < 7; i++) {
            internshipOffer1.setDescription(internshipOffer1.getDescription() + i);
            internshipOfferRepository.save(internshipOffer1).block();
            internshipOffer1.setId(null);
        }
        return internshipOfferRepository.save(internshipOffer2).block().getId();
    }


    private void insertInternshipOffers() {
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
                        .build(),
                InternshipOffer.builder()
                        .limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .emailOfMonitor("9182738492@gmail.com")
                        .companyName("Umaknow")
                        .description(".NET Developper")
                        .minSalary(19.0f)
                        .maxSalary(22.0f)
                        .isValidated(false)
                        .isExclusive(false)
                        .build(),
                InternshipOffer.builder()
                        .limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .emailOfMonitor("9182738492@gmail.com")
                        .companyName("Desjardins")
                        .description("Cobol Developper")
                        .minSalary(15.0f)
                        .maxSalary(20.0f)
                        .isValidated(false)
                        .isExclusive(false)
                        .build(),
                InternshipOffer.builder()
                        .limitDateToApply(LocalDate.now())
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
                        .build(),
                InternshipOffer.builder()
                        .limitDateToApply(LocalDate.now())
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
                        .build(),
                InternshipOffer.builder()
                        .limitDateToApply(LocalDate.now())
                        .beginningDate(LocalDate.now().plusDays(30))
                        .endingDate(LocalDate.now().plusMonths(3))
                        .emailOfMonitor("9182738492@gmail.com")
                        .companyName("Banque National")
                        .description("We have many opportunities in our Montreal contact customer centre team. A career as an agent is to use your customer service skills to respond to the changing needs of our clients. The Customer Service Centre exists to help our customers and have a positive impact in their daily lives. Our modern, custom-designed space located downtown Montreal is specially designed to create a collaborative experience.\n" +
                                "\n" +
                                "Your role\n" +
                                "\n" +
                                "Answer client calls and listen to their questions\n" +
                                "Be attuned to client's needs and resolve issues\n" +
                                "Offer customized solutions to client requests\n" +
                                "Help clients use our banking applications\n" +
                                "Advise about and offer transaction accounts or credit solutions\n" +
                                "Refer clients to your colleagues for their specialized needs\n" +
                                "The centre is open Monday to Sunday from 6:00 AM to midnight. Several work schedules are possible and will vary depending on the availability of team members.\n" +
                                "\n" +
                                "\n" +
                                "You will begin with a seven-week virtual on-the-job training. It will allow you to experience the customer centre environment and get to know your teammates. The training program focusses on learning in action. You will be supported by a remote trainer-coach who will help you learn about your role, our products and our technologies.\n" +
                                "\n" +
                                "Your benefits\n" +
                                "\n" +
                                "Upon hiring, you will be eligible for a wide range of benefits. In addition to competitive compensation, which starts at $19.75/hour, we offer attractive benefits for you and your family:\n" +
                                "\n" +
                                "Two pay increases in the first year\n" +
                                "Evening and weekend premiums may apply\n" +
                                "Repayment of your studies\n" +
                                "Health and wellness program, including many benefits\n" +
                                "Flexible group insurance\n" +
                                "Defined benefit pension plan\n" +
                                "Employee Share Ownership Plan\n" +
                                "Employee and family assistance program\n" +
                                "Exclusive banking services\n" +
                                "Volunteer program\n" +
                                "Telemedicine and virtual sleep clinic\n" +
                                "These are just a few of the many benefits we offer. We've rolled out a number of additional measures to ensure your health, safety and wellbeing during the pandemic.\n" +
                                "\n" +
                                "Your career development\n" +
                                "\n" +
                                "Many options will be available to you. For example, you could advance in the same role, get promoted or move to another department. We offer many career opportunities and encourage internal mobility.\n" +
                                "\n" +
                                "\n" +
                                "Competencies required\n" +
                                "\n" +
                                "High school diploma\n" +
                                "Experience in customer service\n" +
                                "Aptitude for building strong relationships and proactively establishing ties with clients\n" +
                                "Ability to effectively communicate in French and English\n" +
                                "Preferred competencies\n" +
                                "\n" +
                                "Desire to learn and grow in a stimulating and constantly changing workplace\n" +
                                "Attention to detail with your files\n" +
                                "Ability to adapt to frequent changes and team spirit\n" +
                                "Ability to effectively communicate in French and English\n" +
                                "Our dynamic work environments and cutting-edge collaboration tools foster a pleasant employee experience. We actively listen to employees’ ideas. Whether through our surveys or programs, regular feedback is encouraged.\n" +
                                "\n" +
                                "We're putting people first\n" +
                                "\n" +
                                "We're a bank on a human scale that stands out for its courage, entrepreneurial culture, and passion for people. Our mission is to have a positive impact on peoples' lives.\n" +
                                "\n" +
                                "Our core values of partnership, agility, and empowerment inspire us, and inclusivity is central to our commitments. We offer a barrier-free workplace that is accessible to all employees.\n" +
                                "\n" +
                                "We want our recruitment process to be fully accessible. If you require accommodations, feel free to let us know during your first conversations with us.\n" +
                                "\n" +
                                "We welcome all candidates! What can you bring to our team?\n" +
                                "\n" +
                                "Are you ready to live your ambitions?\n" +
                                "\n" +
                                "IMPORTANT Recruitment Process\n" +
                                "\n" +
                                "Thank you for your interest in this position. If your application is selected, you will receive an email from the \"hr-banquenationaleducanada@bnc.ca\" email address inviting you to complete a Talent Explorer questionnaire. Please check your spam or junk folder. After receiving this 15-minute test, you have 24 hours to respond.")
                        .minSalary(15.0f)
                        .maxSalary(20.0f)
                        .isValidated(false)
                        .validationDate(null)
                        .isExclusive(false)
                        .build()
        );

        internshipOfferRepository.saveAll(internshipOffers).subscribe();
    }

    //TODO --> will have to remove it to test real upload and download
    private void insertCvs() {
        List<FileMetaData> fileMetaDataList = Arrays.asList(
                FileMetaData.builder().assetId("123456789@gmail.com/06708b00-52fe-4054-90d0-a1cd4579b0e9").userEmail("123456789@gmail.com").filename("cv1.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusDays(2)).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/3b134033-2463-41b2-b9d8-05238856bfef").userEmail("123456789@gmail.com").filename("cv2.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusWeeks(2)).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/8164ae59-a072-4bfe-8f03-2f350dd8086e").userEmail("123456789@gmail.com").filename("cv3.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusDays(3)).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/c31a51c5-74b0-4ecb-87a3-554bf5290dac").userEmail("123456789@gmail.com").filename("cv4.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusMonths(1)).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/c31a51c5-74b0-4ecb-87a3-554bf5290dac").userEmail("123456789@gmail.com").filename("cv5.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusMonths(1)).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/06708b00-52fe-4054-90d0-a1cd4579b0e9").userEmail("123667713@gmail.com").filename("cv1.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusWeeks(2)).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/3b134033-2463-41b2-b9d8-05238856bfef").userEmail("123667713@gmail.com").filename("cv2.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now()).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/8164ae59-a072-4bfe-8f03-2f350dd8086e").userEmail("3643283423@gmail.com").filename("cv1.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusDays(6)).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/06708b00-52fe-4054-90d0-a1cd4579b0e9").userEmail("3643283423@gmail.com").filename("cv2.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusDays(2)).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/3b134033-2463-41b2-b9d8-05238856bfef").userEmail("902938912@gmail.com").filename("cv1.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now()).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/8164ae59-a072-4bfe-8f03-2f350dd8086e").userEmail("902938912@gmail.com").filename("cv1.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now()).build()
        );

        fileMetaDataRepository.saveAll(fileMetaDataList).subscribe(f -> log.info("new cv file has been created: {}", f));
    }
}
