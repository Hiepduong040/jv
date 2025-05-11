package ra.edu.business.service.Enrollment;

import ra.edu.business.model.Enrollment;

import java.util.List;
import java.util.Scanner;

public interface EnrollmentService {
    boolean registerCourse(Enrollment enrollment);
    boolean cancelEnrollment(int enrollmentId);
    List<Enrollment> getEnrollmentsByStudent(int studentId);
    int countEnrollmentsByStudent(int studentId);
    void listEnrollmentsByCourse(Scanner scanner, int courseId);
    boolean approveEnrollment(int enrollmentId, String newStatus);
    Enrollment findEnrollmentById(int enrollmentId);
}