import edu.unifi.model.entities.User;
import edu.unifi.model.util.security.CurrentSession;
import edu.unifi.model.util.security.Roles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CurrentSessionTest {
    @Mock
    private User mockUser = new User();

    static Logger log = Logger.getLogger(CurrentSession.class.getName());


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockUser.getRole()).thenReturn(Roles.ADMIN);
    }

    @Test
    public void testAuthorization() {
        CurrentSession session = CurrentSession.getInstance();
        session.login(mockUser);
        assertTrue(session.isAuthorized(Roles.ADMIN));
    }

    @AfterEach
    public void tearDown() {
        CurrentSession.getInstance().logout();
    }

    @Test
    public void testGetRole() {
        CurrentSession session = CurrentSession.getInstance();
        session.login(mockUser);
        assertEquals(Roles.ADMIN, session.getRole());
    }

    @Test
    public void testGetRoleWithoutLogin() {
        CurrentSession session = CurrentSession.getInstance();
        assertThrows(SecurityException.class, session::getRole);
    }

    @Test
    public void testGetUser() {
        CurrentSession session = CurrentSession.getInstance();
        session.login(mockUser);
        assertEquals(mockUser, session.getUser());
    }

    @Test
    public void testGetUserWithoutLogin() {
        CurrentSession session = CurrentSession.getInstance();
        assertThrows(SecurityException.class, session::getUser);
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
            boolean res = executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.severe(e.getMessage());
        }
    }
}