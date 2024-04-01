package servlet;

import com.google.gson.Gson;
import dto.UserDTO;
import service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private UserService userService;
    @Mock
    private ServletContext servletContext;

    private UserServlet servlet;
    private StringWriter responseWriter;
    private final Gson gson = new Gson();

    @BeforeEach
    void setUp() throws IOException, ServletException {
        MockitoAnnotations.openMocks(this);
        when(servletContext.getAttribute("userService")).thenReturn(userService);

        servlet = new UserServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        servlet.init();

        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    void doGetAllUsers() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/");
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(new UserDTO()));

        servlet.doGet(request, response);

        verify(userService).getAllUsers();
        assertNotNull(responseWriter.toString());
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    @Test
    void doGetUserById() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/1");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        when(userService.getUserById(1)).thenReturn(userDTO);

        servlet.doGet(request, response);

        verify(userService).getUserById(1);
        assertEquals(gson.toJson(userDTO), responseWriter.toString());
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    @Test
    void doPostCreatesUser() throws ServletException, IOException {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("newUser");
        userDTO.setEmail("newUser@example.com");
        String userJson = gson.toJson(userDTO);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(userJson)));

        servlet.doPost(request, response);

        verify(userService).createUser(any(UserDTO.class));
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }
}
