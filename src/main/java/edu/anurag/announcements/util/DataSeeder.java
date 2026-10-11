package edu.anurag.announcements.util;

import edu.anurag.announcements.model.*;
import edu.anurag.announcements.repository.*;
import edu.anurag.announcements.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;
    private final AcknowledgementRepository acknowledgementRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    public DataSeeder(DepartmentRepository departmentRepository,
                      UserRepository userRepository,
                      AnnouncementRepository announcementRepository,
                      AcknowledgementRepository acknowledgementRepository,
                      NotificationRepository notificationRepository,
                      PasswordEncoder passwordEncoder,
                      NotificationService notificationService) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.announcementRepository = announcementRepository;
        this.acknowledgementRepository = acknowledgementRepository;
        this.notificationRepository = notificationRepository;
        this.passwordEncoder = passwordEncoder;
        this.notificationService = notificationService;
    }

    @Override
    public void run(String... args) {
        if (departmentRepository.count() > 0) {
            log.info("Database already seeded. Skipping initial seeding.");
            return;
        }

        log.info("Seeding initial data for Anurag University Announcements Portal...");

        // 1. Seed Departments
        Department cse = departmentRepository.save(Department.builder().code("CSE").name("Computer Science & Engineering").description("School of Computer Science").build());
        Department it = departmentRepository.save(Department.builder().code("IT").name("Information Technology").description("Department of IT").build());
        Department aiml = departmentRepository.save(Department.builder().code("AIML").name("Artificial Intelligence & Machine Learning").description("School of AI").build());
        Department ds = departmentRepository.save(Department.builder().code("DS").name("Data Science & Analytics").description("Dept of Data Science").build());
        Department ece = departmentRepository.save(Department.builder().code("ECE").name("Electronics & Communication Engineering").description("School of Electronics").build());
        Department eee = departmentRepository.save(Department.builder().code("EEE").name("Electrical & Electronics Engineering").description("Dept of EEE").build());
        Department mech = departmentRepository.save(Department.builder().code("MECH").name("Mechanical Engineering").description("School of Mechanical").build());
        Department civil = departmentRepository.save(Department.builder().code("CIVIL").name("Civil Engineering").description("School of Civil").build());
        Department pharm = departmentRepository.save(Department.builder().code("PHARM").name("School of Pharmacy").description("Pharmacy & Health Sciences").build());
        Department mgmt = departmentRepository.save(Department.builder().code("MGMT").name("School of Management").description("MBA & Business Studies").build());

        // 2. Seed Users
        // Admin
        User admin = userRepository.save(User.builder()
                .fullName("Dr. P. Rajeshwar (Dean)")
                .email("admin@anurag.edu.in")
                .password(passwordEncoder.encode("admin123"))
                .rollNumber("EMP-ADMIN-01")
                .phone("+91 98480 12345")
                .role(Role.ROLE_ADMIN)
                .department(cse)
                .build());

        // Faculty
        User facultyWisshwa = userRepository.save(User.builder()
                .fullName("Prof. Narayanadas Wisshwakh Shen")
                .email("wisshwa.faculty@anurag.edu.in")
                .password(passwordEncoder.encode("faculty123"))
                .rollNumber("FAC-CSE-24")
                .phone("+91 63019 83933")
                .role(Role.ROLE_FACULTY)
                .department(cse)
                .build());

        User facultyExam = userRepository.save(User.builder()
                .fullName("Dr. R. Sharma (Exam Controller)")
                .email("sharma.faculty@anurag.edu.in")
                .password(passwordEncoder.encode("faculty123"))
                .rollNumber("FAC-EXAM-09")
                .phone("+91 94401 22334")
                .role(Role.ROLE_FACULTY)
                .department(cse)
                .build());

        User facultyPlacement = userRepository.save(User.builder()
                .fullName("Prof. Anita Roy (Placements)")
                .email("training.head@anurag.edu.in")
                .password(passwordEncoder.encode("faculty123"))
                .rollNumber("FAC-TPO-02")
                .phone("+91 98850 77889")
                .role(Role.ROLE_FACULTY)
                .department(it)
                .build());

        // Students
        User studentWisshwa = userRepository.save(User.builder()
                .fullName("Narayanadas Wisshwakh Shen")
                .email("24eg105r24@anurag.edu.in")
                .password(passwordEncoder.encode("student123"))
                .rollNumber("24EG105R24")
                .phone("+91 63019 83933")
                .role(Role.ROLE_STUDENT)
                .department(cse)
                .academicYear(2)
                .section("A")
                .build());

        User studentAditi = userRepository.save(User.builder()
                .fullName("Aditi Rao")
                .email("24eg105r01@anurag.edu.in")
                .password(passwordEncoder.encode("student123"))
                .rollNumber("24EG105R01")
                .phone("+91 97000 11223")
                .role(Role.ROLE_STUDENT)
                .department(cse)
                .academicYear(2)
                .section("A")
                .build());

        User studentRahul = userRepository.save(User.builder()
                .fullName("K. Rahul")
                .email("24eg105r55@anurag.edu.in")
                .password(passwordEncoder.encode("student123"))
                .rollNumber("24EG105R55")
                .phone("+91 99887 66554")
                .role(Role.ROLE_STUDENT)
                .department(it)
                .academicYear(3)
                .section("B")
                .build());

        User studentSneha = userRepository.save(User.builder()
                .fullName("Sneha Patel")
                .email("24eg105r88@anurag.edu.in")
                .password(passwordEncoder.encode("student123"))
                .rollNumber("24EG105R88")
                .phone("+91 91234 56789")
                .role(Role.ROLE_STUDENT)
                .department(ece)
                .academicYear(4)
                .section("A")
                .build());

        User studentVikram = userRepository.save(User.builder()
                .fullName("Vikram Singh")
                .email("24eg105r99@anurag.edu.in")
                .password(passwordEncoder.encode("student123"))
                .rollNumber("24EG105R99")
                .phone("+91 94411 22334")
                .role(Role.ROLE_STUDENT)
                .department(aiml)
                .academicYear(1)
                .section("C")
                .build());

        // Notice 1: Exam postponed
        Announcement notice1 = Announcement.builder()
                .title("URGENT: Rescheduled Mid-Term Examination Dates for B.Tech Autumn 2026")
                .content("""
                        All students and faculty members are hereby informed that due to the upcoming State Technical Conclave and severe weather advisory, the B.Tech Mid-Term Examinations originally scheduled for October 15th, 2026, stand rescheduled.
                        
                        KEY HIGHLIGHTS:
                        • Revised Start Date: Monday, October 20th, 2026.
                        • Detailed subject-wise timetable is attached below and has been sent to your college email IDs.
                        • Reporting time at exam centers will be 09:30 AM sharp.
                        • Hall tickets are downloadable from the student portal starting October 17th.
                        
                        Every student must acknowledge this circular by clicking the 'Noted & Acknowledged' button below.
                        """)
                .category(AnnouncementCategory.EXAMINATION)
                .priority(Priority.URGENT)
                .author(facultyExam)
                .isPublic(true)
                .status(AnnouncementStatus.PUBLISHED)
                .publishedAt(LocalDateTime.now().minusDays(2))
                .expiresAt(LocalDateTime.now().plusDays(10))
                .viewCount(48)
                .build();
        notice1.addTarget(AnnouncementTarget.builder()
                .targetScopeDescription("Campus-wide (All Departments & Years)")
                .build());
        notice1 = announcementRepository.save(notice1);
        notificationService.dispatchNotificationsForAnnouncement(notice1);

        // Notice 2: Placement Drive
        Announcement notice2 = Announcement.builder()
                .title("Campus Placement Drive: Microsoft & Google Elite Engineering Hiring 2026")
                .content("""
                        The Training & Placement Cell (T&P) is pleased to announce that registrations are open for the on-campus recruitment drive for Microsoft (Software Development Engineer) and Google (Associate Cloud Engineer).
                        
                        ELIGIBILITY CRITERIA:
                        • B.Tech (CSE, IT, AIML, ECE) 3rd and 4th Year students.
                        • Minimum CGPA: 7.5 throughout without active backlogs.
                        • CTC Offered: 18.5 LPA to 32.0 LPA.
                        
                        SELECTION SCHEDULE:
                        • Round 1 (Online Coding Assessment): October 24th, 2026 on HackerEarth.
                        • Round 2 (Technical Interviews): October 28th-29th at Anurag Auditorium.
                        
                        Resume submission deadline: October 21st, 2026, 05:00 PM. Late submissions will strictly not be entertained.
                        """)
                .category(AnnouncementCategory.PLACEMENT)
                .priority(Priority.HIGH)
                .author(facultyPlacement)
                .isPublic(true)
                .status(AnnouncementStatus.PUBLISHED)
                .publishedAt(LocalDateTime.now().minusDays(1))
                .expiresAt(LocalDateTime.now().plusDays(12))
                .viewCount(95)
                .build();
        notice2.addTarget(AnnouncementTarget.builder()
                .targetDepartment(cse)
                .targetYear(3)
                .targetScopeDescription("CSE & IT Depts - 3rd & 4th Year Students")
                .build());
        notice2 = announcementRepository.save(notice2);
        notificationService.dispatchNotificationsForAnnouncement(notice2);

        // Notice 3: CSE 2nd Year Sec A Lab Guidelines
        Announcement notice3 = Announcement.builder()
                .title("Advanced Data Structures & Algorithms: Lab Evaluation Schedule for CSE 2nd Year Sec A")
                .content("""
                        Dear CSE 2nd Year Section A students,
                        
                        This is an official intimation regarding the Continuous Internal Evaluation (CIE-2) for the Advanced Data Structures Lab.
                        
                        INSTRUCTIONS:
                        1. Laboratory records must be submitted in hardcover spiral format by this Friday.
                        2. The practical viva-voce will test Graph Algorithms (Dijkstra, Kruskal, Prim), AVL Trees, and Dynamic Programming implementations.
                        3. Students must push their clean code to their department GitHub Classroom repositories before entering the lab.
                        
                        Strict adherence to academic integrity is mandatory. Students failing to bring certified observation notebooks will not be permitted.
                        """)
                .category(AnnouncementCategory.ACADEMIC)
                .priority(Priority.HIGH)
                .author(facultyWisshwa)
                .isPublic(false)
                .status(AnnouncementStatus.PUBLISHED)
                .publishedAt(LocalDateTime.now().minusHours(14))
                .expiresAt(LocalDateTime.now().plusDays(5))
                .viewCount(22)
                .build();
        notice3.addTarget(AnnouncementTarget.builder()
                .targetDepartment(cse)
                .targetYear(2)
                .targetSection("A")
                .targetRole(Role.ROLE_STUDENT)
                .targetScopeDescription("CSE Dept - 2nd Year - Section A Students")
                .build());
        notice3 = announcementRepository.save(notice3);
        notificationService.dispatchNotificationsForAnnouncement(notice3);

        // Notice 4: Annual Technical Symposium 'DAKSHA 2026'
        Announcement notice4 = Announcement.builder()
                .title("National Technical Symposium 'DAKSHA 2026' — Hackathon, Paper Presentation & Robotics Arena")
                .content("""
                        Anurag University is proud to host 'DAKSHA 2026', one of South India's premier technical conclaves!
                        
                        FEATURED TRACKS:
                        • 24-Hour AI & Web3 National Hackathon (Cash Prize: ₹1,50,000).
                        • IEEE Paper Presentation Track across Computer Science, Electronics & Mechanical tracks.
                        • RoboWars & Autonomous Drone Racing championship.
                        • Industry Masterclasses by AI Researchers from Google DeepMind and Microsoft Research.
                        
                        Registrations are officially open for both internal students and external college delegates!
                        """)
                .category(AnnouncementCategory.EVENT)
                .priority(Priority.MEDIUM)
                .author(admin)
                .isPublic(true)
                .status(AnnouncementStatus.PUBLISHED)
                .publishedAt(LocalDateTime.now().minusDays(4))
                .expiresAt(LocalDateTime.now().plusDays(20))
                .viewCount(180)
                .build();
        notice4.addTarget(AnnouncementTarget.builder()
                .targetScopeDescription("Campus-wide (All Students, Faculty & External Guests)")
                .build());
        notice4 = announcementRepository.save(notice4);
        notificationService.dispatchNotificationsForAnnouncement(notice4);

        // Notice 5: Holiday Declaration
        Announcement notice5 = Announcement.builder()
                .title("Declaration of Holiday on Account of National Technology Day")
                .content("""
                        As per the directive of the Registrar and Vice-Chancellor, the university will observe a holiday on Monday for all undergraduate, postgraduate classes and administrative offices.
                        
                        Emergency campus services, security, essential lab servers, and hostel catering services will function normally.
                        Regular classes will resume from Tuesday as per the standard timetable.
                        """)
                .category(AnnouncementCategory.HOLIDAY)
                .priority(Priority.LOW)
                .author(admin)
                .isPublic(true)
                .status(AnnouncementStatus.PUBLISHED)
                .publishedAt(LocalDateTime.now().minusDays(5))
                .expiresAt(LocalDateTime.now().plusDays(3))
                .viewCount(65)
                .build();
        notice5.addTarget(AnnouncementTarget.builder()
                .targetScopeDescription("Whole College")
                .build());
        notice5 = announcementRepository.save(notice5);
        notificationService.dispatchNotificationsForAnnouncement(notice5);

        // Seed sample acknowledgements
        acknowledgementRepository.save(Acknowledgement.builder()
                .announcement(notice1)
                .user(studentWisshwa)
                .acknowledgedAt(LocalDateTime.now().minusDays(1))
                .remarks("Noted revised exam schedule. Prepared.")
                .build());

        acknowledgementRepository.save(Acknowledgement.builder()
                .announcement(notice1)
                .user(studentAditi)
                .acknowledgedAt(LocalDateTime.now().minusHours(20))
                .remarks("Noted and updated calendar.")
                .build());

        acknowledgementRepository.save(Acknowledgement.builder()
                .announcement(notice1)
                .user(studentRahul)
                .acknowledgedAt(LocalDateTime.now().minusHours(18))
                .remarks("Acknowledged.")
                .build());

        acknowledgementRepository.save(Acknowledgement.builder()
                .announcement(notice3)
                .user(studentWisshwa)
                .acknowledgedAt(LocalDateTime.now().minusHours(8))
                .remarks("Submitted lab records on GitHub.")
                .build());

        notificationRepository.findByUserIdOrderByCreatedAtDesc(studentWisshwa.getId()).stream().limit(2)
                .forEach(n -> {
                    n.setRead(true);
                    n.setReadAt(LocalDateTime.now().minusHours(5));
                    notificationRepository.save(n);
                });

        log.info("Initial data seeded successfully! Demo accounts ready:");
        log.info("  Admin: admin@anurag.edu.in / admin123");
        log.info("  Faculty: wisshwa.faculty@anurag.edu.in / faculty123");
        log.info("  Student: 24eg105r24@anurag.edu.in / student123");
    }
}
