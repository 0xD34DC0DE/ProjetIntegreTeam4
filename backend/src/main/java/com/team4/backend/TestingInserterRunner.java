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
                Student.studentBuilder().email("123456789@gmail.com").firstName("Travis").lastName("Scott").phoneNumber("4387650987").password(pbkdf2Encoder.encode("travis123")).build(),
                Student.studentBuilder().email("6754358234@gmail.com").firstName("Jean").lastName("Jordan").phoneNumber("5143245678").password(pbkdf2Encoder.encode("jean123")).build(),
                Student.studentBuilder().email("09237732@gmail.com").firstName("Farid").lastName("Shalom").phoneNumber("4385738764").password(pbkdf2Encoder.encode("farid123")).build()
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
                FileMetaData.builder().assetId("edsa09923easdas").userEmail("123456789@gmail.com").filename("cv1.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now()).build(),
                FileMetaData.builder().assetId("p90sjifkk32nk").userEmail("123456789@gmail.com").filename("cv2.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now()).build(),
                FileMetaData.builder().assetId("0zjixj43jbj").userEmail("123456789@gmail.com").filename("cv3.pdf").isValid(false).isSeen(false).uploadDate(LocalDateTime.now()).build()
        );

        fileMetaDataRepository.saveAll(fileMetaDataList).subscribe(f -> log.info("new cv file has been created: {}", f));
    }
}
