import edu.unifi.Main;
import edu.unifi.api.security.CurrentSession;
import edu.unifi.api.security.Roles;
import edu.unifi.entities.User;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrentSessionTest {
    @Mock
    private User mockUser;

    static Logger log = Logger.getLogger(CurrentSession.class.getName());

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockUser.getRole()).thenReturn(Roles.ADMIN);
    }

    @Test
    public void testSingleton() {
        CurrentSession session1 = CurrentSession.getInstance();
        CurrentSession session2 = CurrentSession.getInstance();
        assertSame(session1, session2);
    }

    @Test
    public void testLoginLogout() {
        CurrentSession session = CurrentSession.getInstance();

        // Successful login
        session.login(mockUser);

        // Verify that login fails for already logged-in user
        assertThrows(SecurityException.class, () -> session.login(mockUser));

        // Successful logout
        session.logout();

        // Verify that logged-out user cannot retrieve role
        assertThrows(SecurityException.class, session::getRole);
    }


    @Test
    public void testAuthorization() {
        CurrentSession session = CurrentSession.getInstance();
        session.login(mockUser);
        // Verify authorization for admin user
        assertTrue(session.isAuthorized(Roles.ADMIN));

        // Verify non-authorization for non-admin user
        assertFalse(session.isAuthorized(Roles.WAITER));
    }

    @Test
    @Disabled("Disabled until able to create invalid user")
    public void testInvalidLogin() {
        // TODO
        CurrentSession session = CurrentSession.getInstance();

        // Attempt to login with invalid user
        User invalidUser = User.builder().build();
        assertThrows(IllegalArgumentException.class, () -> session.login(invalidUser));
    }

    @Test
    public void testConcurrentAccess() {
        // Simulate multiple threads accessing the singleton instance
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Runnable task = () -> {
            CurrentSession session = CurrentSession.getInstance();
            session.login(mockUser);
            session.logout();
        };

        for (int i = 0; i < 100; i++) {
            executorService.submit(task);
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.severe(e.getMessage());
        }
    }
}