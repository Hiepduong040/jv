//package ra.edu.presentation;
//
//import ra.edu.business.model.Course;
//import ra.edu.business.model.Enrollment;
//import ra.edu.business.service.Course.CourseServiceImp;
//import ra.edu.business.service.Enrollment.EnrollmentServiceImp;
//import ra.edu.validate.Validator;
//import ra.edu.validate.ValidatorChoice;
//
//import java.util.Scanner;
//
//public class EnrollmentUI {
//    private static CourseServiceImp courseServiceImp;
//    private static EnrollmentServiceImp enrollmentServiceImp;
//
//    public EnrollmentUI() {
//        courseServiceImp = new CourseServiceImp();
//        enrollmentServiceImp = new EnrollmentServiceImp();
//    }
//
//    public static void main(String[] args) {
//        EnrollmentUI enrollmentUI = new EnrollmentUI();
//        Scanner scanner = new Scanner(System.in);
//        boolean exit = false;
//        do {
//            System.out.println("================== Quản lý đăng ký khóa học ==================");
//            System.out.println("1. Hiển thị danh sách sinh viên đăng ký theo khóa học");
//            System.out.println("2. Duyệt đăng ký khóa học");
//            System.out.println("3. Hủy đăng ký khóa học");
//            System.out.println("4. Quay về menu quản trị");
//            int choice = ValidatorChoice.validater(scanner);
//            switch (choice) {
//                case 1:
//                    listEnrollmentsByCourse(scanner);
//                    break;
//                case 2:
//                    approveEnrollment(scanner);
//                    break;
//                case 3:
//                    cancelEnrollment(scanner);
//                    break;
//                case 4:
//                    exit = true;
//                    break;
//                default:
//                    System.out.println("Lựa chọn không hợp lệ vui lòng chọn từ 1 - 4!");
//            }
//        } while (!exit);
//    }
//
//    public static void listEnrollmentsByCourse(Scanner scanner) {
//        int courseId = Validator.validateInt(scanner, 0, 1000, "Nhập mã khóa học: ", "Mã khóa học");
//        Course course = courseServiceImp.findCourseById(courseId);
//        if (course == null) {
//            System.out.println("Không tìm thấy khóa học!");
//        } else {
//            enrollmentServiceImp.listEnrollmentsByCourse(scanner, courseId);
//        }
//    }
//
//    public static void approveEnrollment(Scanner scanner) {
//        boolean validEnrollment = false;
//        Enrollment enrollment = null;
//        int enrollmentId;
//
//        do {
//            enrollmentId = Validator.validateInt(scanner, 0, 1000, "Nhập mã đăng ký cần duyệt: ", "Mã đăng ký");
//            enrollment = enrollmentServiceImp.findEnrollmentById(enrollmentId);
//
//            if (enrollment == null) {
//                System.out.println("Mã đăng ký không tồn tại! Vui lòng nhập lại.");
//            } else {
//                validEnrollment = true;
//                // Hiển thị thông tin đăng ký
//                System.out.println("Thông tin đăng ký:");
//                System.out.println("---------------------------------------------------------------------------------------------");
//                System.out.printf("%-10s | %-25s | %-25s | %-15s | %-20s\n",
//                        "Mã DK", "Tên sinh viên", "Tên khóa học", "Trạng thái", "Ngày đăng ký");
//                System.out.println("---------------------------------------------------------------------------------------------");
//                System.out.printf("%-10d | %-25s | %-25s | %-15s | %-20s\n",
//                        enrollment.getId(), enrollment.getStudentName(), enrollment.getCourseName(),
//                        enrollment.getStatus(), enrollment.getRegisteredAt());
//                System.out.println("---------------------------------------------------------------------------------------------");
//            }
//        } while (!validEnrollment);
//
//        System.out.println("Chọn trạng thái:");
//        System.out.println("1. CONFIRM");
//        System.out.println("2. DENIED");
//        int choice = ValidatorChoice.validater(scanner);
//        String newStatus = choice == 1 ? "CONFIRM" : "DENIED";
//
//        if (enrollmentServiceImp.approveEnrollment(enrollmentId, newStatus)) {
//            System.out.println("Duyệt đăng ký thành công");
//        } else {
//            System.out.println("Duyệt đăng ký thất bại!");
//        }
//    }
//
//    public static void cancelEnrollment(Scanner scanner) {
//        int enrollmentId = Validator.validateInt(scanner, 0, 1000, "Nhập mã đăng ký cần hủy: ", "Mã đăng ký");
//        boolean exit = false;
//        while (!exit) {
//            System.out.println("Bạn có chắc chắn muốn hủy đăng ký này không(y/n)");
//            System.out.print("Lựa chọn: ");
//            char choice = Character.toLowerCase(scanner.nextLine().charAt(0));
//            switch (choice) {
//                case 'y':
//                    if (enrollmentServiceImp.cancelEnrollment(enrollmentId)) {
//                        System.out.println("Hủy đăng ký thành công");
//                    } else {
//                        System.out.println("Hủy đăng ký thất bại!");
//                    }
//                    exit = true;
//                    break;
//                case 'n':
//                    System.out.println("Đã hủy thao tác");
//                    exit = true;
//                    break;
//                default:
//                    System.out.println("Lựa chọn không hợp lệ vui lòng chọn y/n");
//            }
//        }
//    }
//}



package ra.edu.presentation;

import ra.edu.business.model.Course;
import ra.edu.business.model.Enrollment;
import ra.edu.business.service.Course.CourseServiceImp;
import ra.edu.business.service.Enrollment.EnrollmentServiceImp;
import ra.edu.validate.Validator;
import ra.edu.validate.ValidatorChoice;

import java.util.Scanner;

import static ra.edu.presentation.AdminUI.*;

public class EnrollmentUI {
    private static CourseServiceImp courseServiceImp;
    private static EnrollmentServiceImp enrollmentServiceImp;

    public EnrollmentUI() {
        courseServiceImp = new CourseServiceImp();
        enrollmentServiceImp = new EnrollmentServiceImp();
    }

    public static void main(String[] args) {
        EnrollmentUI enrollmentUI = new EnrollmentUI();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        do {
            System.out.println(CYAN + "================== Quản lý đăng ký khóa học ==================" + RESET);
            System.out.println(GREEN + "1. Hiển thị danh sách sinh viên đăng ký theo khóa học" + RESET);
            System.out.println(GREEN + "2. Duyệt đăng ký khóa học" + RESET);
            System.out.println(GREEN + "3. Hủy đăng ký khóa học" + RESET);
            System.out.println(GREEN + "4. Quay về menu quản trị" + RESET);
            int choice = ValidatorChoice.validater(scanner);
            switch (choice) {
                case 1:
                    listEnrollmentsByCourse(scanner);
                    break;
                case 2:
                    approveEnrollment(scanner);
                    break;
                case 3:
                    cancelEnrollment(scanner);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ vui lòng chọn từ 1 - 4!" + RESET);
            }
        } while (!exit);
    }

    public static void listEnrollmentsByCourse(Scanner scanner) {
        int courseId = Validator.validateInt(scanner, 0, 1000, "Nhập mã khóa học: ", "Mã khóa học");
        Course course = courseServiceImp.findCourseById(courseId);
        if (course == null) {
            System.out.println(RED + "Không tìm thấy khóa học!" + RESET);
        } else {
            enrollmentServiceImp.listEnrollmentsByCourse(scanner, courseId);
        }
    }

    public static void approveEnrollment(Scanner scanner) {
        boolean validEnrollment = false;
        Enrollment enrollment = null;
        int enrollmentId;

        do {
            enrollmentId = Validator.validateInt(scanner, 0, 1000, "Nhập mã đăng ký cần duyệt: ", "Mã đăng ký");
            enrollment = enrollmentServiceImp.findEnrollmentById(enrollmentId);

            if (enrollment == null) {
                System.out.println(RED + "Mã đăng ký không tồn tại! Vui lòng nhập lại." + RESET);
            } else {
                validEnrollment = true;
                // Hiển thị thông tin đăng ký
                System.out.println("Thông tin đăng ký:");
                System.out.println("---------------------------------------------------------------------------------------------");
                System.out.printf("%-10s | %-25s | %-25s | %-15s | %-20s\n",
                        "Mã DK", "Tên sinh viên", "Tên khóa học", "Trạng thái", "Ngày đăng ký");
                System.out.println("---------------------------------------------------------------------------------------------");
                System.out.printf("%-10d | %-25s | %-25s | %-15s | %-20s\n",
                        enrollment.getId(), enrollment.getStudentName(), enrollment.getCourseName(),
                        enrollment.getStatus(), enrollment.getRegisteredAt());
                System.out.println("---------------------------------------------------------------------------------------------");
            }
        } while (!validEnrollment);

        System.out.println(GREEN + "Chọn trạng thái:" + RESET);
        System.out.println(GREEN + "1. CONFIRM" + RESET);
        System.out.println(GREEN + "2. DENIED" + RESET);
        int choice = ValidatorChoice.validater(scanner);
        String newStatus = choice == 1 ? "CONFIRM" : "DENIED";

        if (enrollmentServiceImp.approveEnrollment(enrollmentId, newStatus)) {
            System.out.println(GREEN + "Duyệt đăng ký thành công" + RESET);
        } else {
            System.out.println(RED + "Duyệt đăng ký thất bại!" + RESET);
        }
    }

    public static void cancelEnrollment(Scanner scanner) {
        int enrollmentId = Validator.validateInt(scanner, 0, 1000, "Nhập mã đăng ký cần hủy: ", "Mã đăng ký");
        boolean exit = false;
        while (!exit) {
            System.out.println("Bạn có chắc chắn muốn hủy đăng ký này không(y/n)");
            System.out.print("Lựa chọn: ");
            char choice = Character.toLowerCase(scanner.nextLine().charAt(0));
            switch (choice) {
                case 'y':
                    if (enrollmentServiceImp.cancelEnrollment(enrollmentId)) {
                        System.out.println(GREEN + "Hủy đăng ký thành công" + RESET);
                    } else {
                        System.out.println(RED + "Hủy đăng ký thất bại!" + RESET);
                    }
                    exit = true;
                    break;
                case 'n':
                    System.out.println(YELLOW + "Đã hủy thao tác" + RESET);
                    exit = true;
                    break;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ vui lòng chọn y/n" + RESET);
            }
        }
    }
}