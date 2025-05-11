package ra.edu.business.dao.Auth;

import ra.edu.business.model.Admin;
import ra.edu.business.model.Student;

public interface AuthDAO {
    Object login(String username, String password);
}