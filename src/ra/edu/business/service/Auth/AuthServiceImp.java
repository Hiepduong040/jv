package ra.edu.business.service.Auth;

import ra.edu.business.dao.Auth.AuthDAOImp;

public class AuthServiceImp implements AuthService {
    private AuthDAOImp authDAOImp;

    public AuthServiceImp() {
        authDAOImp = new AuthDAOImp();
    }

    @Override
    public Object login(String username, String password) {
        return authDAOImp.login(username, password);
    }
}