package config;

import static org.mockito.Mockito.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.PostService;
import service.UserService;

public class AppContextListenerTest {

    @Mock
    private ServletContextEvent sce;

    @Mock
    private ServletContext servletContext;

    private AppContextListener listener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sce.getServletContext()).thenReturn(servletContext);
        listener = new AppContextListener();
    }

    @Test
    public void contextInitializedTest() {
        listener.contextInitialized(sce);

        verify(servletContext).setAttribute(eq("userService"), any(UserService.class));
        verify(servletContext).setAttribute(eq("postService"), any(PostService.class));
    }

    @Test
    public void contextDestroyedTest() {
        listener.contextDestroyed(sce);

    }
}
