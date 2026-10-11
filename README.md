# 📢 Smart College Announcement and Notification Management System
### *One Portal for Every College Announcement — Built for Students, Faculty & Admins*

---

### 🎓 Academic Submission Details
- **Student Name:** Narayanadas Wisshwakh Shen
- **Roll Number:** `24EG105R24`
- **College / University:** Anurag University, Hyderabad
- **College Email:** [24eg105r24@anurag.edu.in](mailto:24eg105r24@anurag.edu.in)
- **Personal Email:** [n.wisshwa47@gmail.com](mailto:n.wisshwa47@gmail.com)
- **Phone Contact:** `+91 63019 83933`

---

## 1. Project Overview & Abstract
Every college runs on announcements, yet most colleges still spread them across WhatsApp groups, printed notices, class representatives, and scattered emails. Something urgent — a postponed exam, a placement drive, a fee deadline — can quietly get lost between five different channels, and no one at the top ever really knows who has actually seen it.

The **Smart College Announcement and Notification Management System** solves this by establishing a single, role-governed portal to compose, target, and monitor every college announcement.

### Key Highlights:
- **Precise Audience Targeting:** Authors target by Department (CSE, IT, ECE, etc.), Academic Year (1st–4th), Section (A, B, C), Role (Students, Faculty, All), or Whole College.
- **Automated Delivery Engine:** Instantly matches recipients and dispatches in-app notifications with unread counter badges and simulated email alerts.
- **Read & Acknowledgement Audit Trail:** Students confirm readership via the *"Acknowledge / Mark as Noted"* action, feeding admin analytics.
- **Admin Reach Analytics:** Real-time visibility into readership %, acknowledgment rates, department breakdowns, and exportable CSV reports.
- **Public Bulletins & SEO:** Clean, crawlable public circulars with OpenGraph metadata, `sitemap.xml`, and `robots.txt`.
- **Framework-Free Responsive Frontend:** Lightweight Vanilla HTML5, CSS3, and JavaScript rendering under 2 seconds on mobile data or campus Wi-Fi.

---

## 2. Technology Stack
- **Backend:** Java 17+, Spring Boot 3.3.x (Spring Web, Spring Security, Spring Data JPA, Hibernate, Validation)
- **View Engine:** Thymeleaf with Thymeleaf Spring Security 6 extras
- **Frontend:** Vanilla CSS3 (Custom Design System with Glassmorphism, CSS Grid & Flexbox, Dark/Light mode), Vanilla JavaScript (No React/Angular/Vue dependencies)
- **Database:** MySQL 8.x (`smart_college_announcements_db`)
- **Build Tool:** Apache Maven

---

## 3. Pre-Seeded Demo Credentials

The system seeds sample accounts and realistic notices upon first launch:

| Role | Name | Email | Password | Scope / Department |
| :--- | :--- | :--- | :--- | :--- |
| **Student** | Narayanadas Wisshwakh Shen | `24eg105r24@anurag.edu.in` | `student123` | CSE, 2nd Year, Section A |
| **Student** | Aditi Rao | `24eg105r01@anurag.edu.in` | `student123` | CSE, 2nd Year, Section A |
| **Student** | K. Rahul | `24eg105r55@anurag.edu.in` | `student123` | IT, 3rd Year, Section B |
| **Student** | Sneha Patel | `24eg105r88@anurag.edu.in` | `student123` | ECE, 4th Year, Section A |
| **Faculty** | Prof. Narayanadas Wisshwakh Shen | `wisshwa.faculty@anurag.edu.in` | `faculty123` | Head of CSE Dept |
| **Faculty** | Dr. R. Sharma | `sharma.faculty@anurag.edu.in` | `faculty123` | Controller of Examinations |
| **Faculty** | Prof. Anita Roy | `training.head@anurag.edu.in` | `faculty123` | Director of Placements |
| **Admin** | Dr. P. Rajeshwar | `admin@anurag.edu.in` | `admin123` | Chief Academic Dean |

> **Pro Tip:** The `/login` page features **1-Click Demo Login** buttons to fill these credentials instantly.

---

## 4. How to Run the Project Locally

### Prerequisites:
- Java JDK 17 or higher
- Apache Maven 3.8+
- MySQL 8.x running on `localhost:3306`

### Steps:
1. **Ensure MySQL is running:**
   ```sql
   CREATE DATABASE IF NOT EXISTS smart_college_announcements_db CHARACTER SET utf8mb4;
   ```
2. **Review configuration in `src/main/resources/application.properties`:**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/smart_college_announcements_db?createDatabaseIfNotExist=true&useSSL=false
   spring.datasource.username=root
   spring.datasource.password=C10@8538
   ```
3. **Run using Maven:**
   ```bash
   mvn spring-boot:run
   ```
4. **Open your browser:**
   - Portal Home: [http://localhost:8080/](http://localhost:8080/)
   - Portal Login: [http://localhost:8080/login](http://localhost:8080/login)
   - Public Notices: [http://localhost:8080/public/announcements](http://localhost:8080/public/announcements)
   - System Documentation & Abstract: [http://localhost:8080/about](http://localhost:8080/about)
   - XML Sitemap: [http://localhost:8080/sitemap.xml](http://localhost:8080/sitemap.xml)

---

## 5. System Architecture & Workflows

### 5.1 Layered Backend Architecture
```
[Browser / Mobile / Tablet]
        │
        ▼ HTTP/HTTPS
[Spring Security 6 Filter Chain] (BCrypt verification · Session authorization)
        │
        ▼ Authorized Request
[Controller Layer]
  ├── AuthController
  ├── DashboardController
  ├── AnnouncementController
  ├── NotificationController
  ├── AdminController
  └── PublicNoticeController
        │
        ▼
[Service Layer]
  ├── AnnouncementService (Workflow orchestration & views)
  ├── NotificationService (Recipients matcher & automated dispatch)
  ├── AcknowledgementService (Read receipts & noted confirmations)
  └── AnalyticsService (Reach KPIs & recipient status matrices)
        │
        ▼
[Repository Layer] (Spring Data JPA / Hibernate)
        │
        ▼
[MySQL 8.x Database & Upload Storage]
```

### 5.2 Key Features Walkthrough:
1. **Compose & Publish:** Faculty and Admins can publish notices with markdown/text formatting, category tags, glowing priority ribbons (Low, Medium, High, Urgent), and circular file attachments.
2. **Automated Notification Dispatch:** As soon as an announcement is created, the system inspects target criteria (Branch, Year, Section, Role) and automatically generates in-app notifications and simulated email delivery logs.
3. **Acknowledgement:** Students opening an announcement can click *"Acknowledge / Mark as Noted"*, optionally inputting remarks (e.g. *"Noted timetable change"*).
4. **Audit Reporting:** Admins can view individual announcements to see an exact recipient matrix with read timestamps, acknowledgement timestamps, and exportable CSV reports.
