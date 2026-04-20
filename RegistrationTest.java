/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.login_system.Login;
import com.mycompany.login_system.Registration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */

public class RegistrationTest {

    Registration reg = new Registration();

    @Test
    void testValidRegistration() {
        assertTrue(reg.register("ab_cd", "Pass123!", "+27831234567"));
    }

    @Test
    void testInvalidUsername() {
        assertFalse(reg.register("abcd", "Pass123!", "+27831234567"));
    }

    @Test
    void testInvalidPassword() {
        assertFalse(reg.register("ab_cd", "pass", "+27831234567"));
    }

    @Test
    void testInvalidPhone() {
        assertFalse(reg.register("ab_cd", "Pass123!", "0831234567"));
    }

    @Test
    void testLoginSuccess() {
        reg.register("ab_cd", "Pass123!", "+27831234567");
        Login login = new Login();
        assertTrue(login.login("ab_cd", "Pass123!", reg));
    }

    @Test
    void testLoginFail() {
        reg.register("ab_cd", "Pass123!", "+27831234567");
        Login login = new Login();
        assertFalse(login.login("ab_cd", "wrong", reg));

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    }
}

