package com.team4.backend;

import com.team4.backend.model.FileMetaData;
import com.team4.backend.model.InternshipManager;
import com.team4.backend.model.Monitor;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.Role;
import com.team4.backend.repository.FileMetaDataRepository;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.repository.MonitorRepository;
import com.team4.backend.repository.StudentRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class TestingInserterRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TestingInserterRunner.class);

    private final MonitorRepository monitorRepository;

    private final InternshipOfferRepository internshipOfferRepository;

    private final StudentRepository studentRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    private final FileMetaDataRepository fileMetaDataRepository;

    public TestingInserterRunner(MonitorRepository monitorRepository,
                                 InternshipOfferRepository internshipOfferRepository,
                                 StudentRepository studentRepository,
                                 PBKDF2Encoder pbkdf2Encoder,
                                 FileMetaDataRepository fileMetaDataRepository) {
        this.monitorRepository = monitorRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.studentRepository = studentRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.fileMetaDataRepository = fileMetaDataRepository;
    }

    @Override
    public void run(final ApplicationArguments args) {
        studentRepository.deleteAll().subscribe();
        monitorRepository.deleteAll().subscribe();
        fileMetaDataRepository.deleteAll().subscribe();
        internshipOfferRepository.deleteAll().subscribe();

        insertStudents();

        insertMonitors();

        insertCvs();
    }

    private void insertStudents() {
        List<Student> students = Arrays.asList(
                Student.studentBuilder().email("123456789@gmail.com").firstName("Travis").lastName("Scott").phoneNumber("4387650987").password(pbkdf2Encoder.encode("travis123")).hasValidCv(false).build(),
                Student.studentBuilder().email("3643283423@gmail.com").firstName("Jean").lastName("Jordan").phoneNumber("5143245678").password(pbkdf2Encoder.encode("jean123")).hasValidCv(false).build(),
                Student.studentBuilder().email("123667713@gmail.com").firstName("Farid").lastName("Shalom").phoneNumber("4385738764").password(pbkdf2Encoder.encode("farid123")).hasValidCv(false).build(),
                Student.studentBuilder().email("902938912@gmail.com").firstName("Kevin").lastName("Alphonse").phoneNumber("4385738764").password(pbkdf2Encoder.encode("kevin123")).hasValidCv(false).build()
        );

        studentRepository.saveAll(students).subscribe(student -> log.info("Student has been saved : {}", student));
    }

    private void insertMonitors() {
        Monitor monitor = Monitor.monitorBuilder()
                .email("9182738492@gmail.com").password(pbkdf2Encoder.encode("lao@dkv23")).build();

        monitorRepository.save(monitor).subscribe(user -> log.info("Monitor has been saved: {}", user));
    }

    //TODO --> will have to remove it to test real upload and download
    private void insertCvs() {
        List<FileMetaData> fileMetaDataList = Arrays.asList(
                FileMetaData.builder().assetId("123456789@gmail.com/06708b00-52fe-4054-90d0-a1cd4579b0e9").userEmail("123456789@gmail.com").filename("cv1.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusDays(2)).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/3b134033-2463-41b2-b9d8-05238856bfef").userEmail("123456789@gmail.com").filename("cv2.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusWeeks(2)).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/8164ae59-a072-4bfe-8f03-2f350dd8086e").userEmail("123456789@gmail.com").filename("cv3.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusDays(3)).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/c31a51c5-74b0-4ecb-87a3-554bf5290dac").userEmail("123456789@gmail.com").filename("cv4.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusMonths(1)).build(),
                FileMetaData.builder().assetId("123456789@gmail.com/c31a51c5-74b0-4ecb-87a3-554bf5290dac").userEmail("123456789@gmail.com").filename("cv5.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusMonths(1)).build(),
                FileMetaData.builder().assetId("123667713@gmail.com/PlanCoursProjet.pdf").userEmail("123667713@gmail.com").filename("cv1.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusWeeks(2)).build(),
                FileMetaData.builder().assetId("123667713@gmail.com/PlanCoursVeilleTechnologique.pdf").userEmail("123667713@gmail.com").filename("cv2.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now()).build(),
                FileMetaData.builder().assetId("3643283423@gmail.com/PlanCoursProjet.pdf").userEmail("3643283423@gmail.com").filename("cv1.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusDays(6)).build(),
                FileMetaData.builder().assetId("3643283423@gmail.com/PlanCoursVeilleTechnologique.pdf").userEmail("3643283423@gmail.com").filename("cv2.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now().minusDays(2)).build(),
                FileMetaData.builder().assetId("902938912@gmail.com/PlanCoursProjet.pdf").userEmail("902938912@gmail.com").filename("cv1.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now()).build(),
                FileMetaData.builder().assetId("902938912@gmail.com/PlanCoursVeilleTechnologique.pdf").userEmail("902938912@gmail.com").filename("cv2.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now()).build()
        );

        fileMetaDataRepository.saveAll(fileMetaDataList).subscribe(f -> log.info("new cv file has been created: {}", f));
    }
}
