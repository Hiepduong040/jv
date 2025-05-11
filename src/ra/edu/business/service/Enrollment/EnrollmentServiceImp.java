package ra.edu.business.service.Enrollment;

import ra.edu.business.dao.Enrollment.EnrollmentDAOImp;
import ra.edu.business.model.Enrollment;
import ra.edu.business.model.Pagination;
import ra.edu.validate.Validator;

import java.util.List;
import java.util.Scanner;

public class EnrollmentServiceImp implements EnrollmentService {
    private EnrollmentDAOImp enrollmentDAOImp;
    private Pagination pagination;

    public EnrollmentServiceImp() {
        enrollmentDAOImp = new EnrollmentDAOImp();
        pagination = new Pagination();
    }

    @Override
    public boolean registerCourse(Enrollment enrollment) {
        return enrollmentDAOImp.save(enrollment);
    }

    @Override
    public boolean cancelEnrollment(int enrollmentId) {
        return enrollmentDAOImp.cancel(enrollmentId);
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudent(int studentId) {
        return enrollmentDAOImp.findByStudentId(studentId);
    }

    @Override
    public int countEnrollmentsByStudent(int studentId) {
        return enrollmentDAOImp.totalEnrollmentsByStudent(studentId);
    }

    @Override
    public void listEnrollmentsByCourse(Scanner scanner, int courseId) {
        boolean exit = false;
        pagination.setCurrentpage(1);
        pagination.setPagesize(5);

        do {
            // Tính tổng số kết quả
            int totalResults = enrollmentDAOImp.countEnrollmentsByCourse(courseId);
            int totalPages = (int) Math.ceil((double) totalResults / pagination.getPagesize());
            pagination.setTotalpages(totalPages);

            // Lấy danh sách phân trang
            List<Enrollment> enrollments = enrollmentDAOImp.listEnrollmentsByCourse(courseId, pagination.getPagesize(), pagination.getCurrentpage());

            if (!enrollments.isEmpty()) {
                System.out.println("---------------------------------------------------------------------------------------------");
                System.out.printf("%-10s | %-25s | %-25s | %-15s | %-20s\n",
                        "Mã DK", "Tên sinh viên", "Tên khóa học", "Trạng thái", "Ngày đăng ký");
                System.out.println("---------------------------------------------------------------------------------------------");
                enrollments.forEach(enrollment -> {
                    System.out.printf("%-10d | %-25s | %-25s | %-15s | %-20s\n",
                            enrollment.getId(), enrollment.getStudentName(), enrollment.getCourseName(),
                            enrollment.getStatus(), enrollment.getRegisteredAt());
                });
                System.out.println("---------------------------------------------------------------------------------------------");

                // Hiển thị phân trang
                if (totalResults <= pagination.getPagesize()) {
                    System.out.println("Trang: \u001B[33m1\u001B[0m");
                    System.out.println("1. Chọn trang");
                    System.out.println("2. Thoát");
                    System.out.print("Lựa chọn: ");
                } else {
                    System.out.print("Trang: ");
                    if (pagination.getCurrentpage() > 1) {
                        System.out.print("Previous      ");
                        if (pagination.getCurrentpage() >= 3) System.out.print("... ");
                        System.out.print(pagination.getCurrentpage() - 1);
                    }
                    System.out.print("\u001B[33m    " + pagination.getCurrentpage() + "     \u001B[0m");
                    if (pagination.getCurrentpage() < totalPages) {
                        System.out.print(" " + (pagination.getCurrentpage() + 1));
                        if (totalPages - pagination.getCurrentpage() >= 2) System.out.print(" ...");
                        System.out.print("      Next");
                    }
                    System.out.println();
                    if (pagination.getCurrentpage() > 1) System.out.println("P. Trang trước");
                    if (pagination.getCurrentpage() < totalPages) System.out.println("N. Trang tiếp");
                    System.out.println("1. Chọn trang");
                    System.out.println("2. Thoát");
                    System.out.print("Lựa chọn: ");
                }

                while (true) {
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        System.out.println("Lựa chọn không được để trống! Vui lòng nhập lại:");
                        continue;
                    }
                    char choice = Character.toUpperCase(input.charAt(0));
                    switch (choice) {
                        case '1':
                            int page = Validator.validateInt(scanner, 1, totalPages, "Nhập trang: ", "Trang");
                            pagination.setCurrentpage(page);
                            break;
                        case '2':
                            exit = true;
                            break;
                        case 'P':
                            if (pagination.getCurrentpage() > 1)
                                pagination.setCurrentpage(pagination.getCurrentpage() - 1);
                            break;
                        case 'N':
                            if (pagination.getCurrentpage() < totalPages)
                                pagination.setCurrentpage(pagination.getCurrentpage() + 1);
                            break;
                        default:
                            System.out.println("Lựa chọn không hợp lệ vui lòng nhập lại!");
                            continue;
                    }
                    break;
                }
            } else {
                System.out.println("Không có đăng ký nào cho khóa học này.");
                break;
            }
        } while (!exit);
    }

    @Override
    public boolean approveEnrollment(int enrollmentId, String newStatus) {
        return enrollmentDAOImp.approveEnrollment(enrollmentId, newStatus);
    }

    @Override
    public Enrollment findEnrollmentById(int enrollmentId) {
        return enrollmentDAOImp.findById(enrollmentId);
    }
}

