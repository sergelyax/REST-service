package servlet;

import dto.PostDTO;
import service.PostService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class PostServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PostService postService;

    private PostServlet servlet;
    private StringWriter responseWriter;
    private Gson gson = new Gson();

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new PostServlet();
        servlet.setPostService(postService);

        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    void doGetShouldReturnAllPosts() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/");
        when(postService.getAllPosts()).thenReturn(List.of(new PostDTO(1, 1, "Title1", "Content1")));

        servlet.doGet(request, response);

        verify(postService).getAllPosts();
        assertEquals(gson.toJson(List.of(new PostDTO(1, 1, "Title1", "Content1"))), responseWriter.toString());
    }

    @Test
    void doPostShouldCreatePost() throws ServletException, IOException {
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"userId\":1,\"title\":\"Title\",\"content\":\"Content\"}")));
        doAnswer(invocation -> {
            PostDTO post = invocation.getArgument(0);
            post.setId(1);
            return null;
        }).when(postService).createPost(any(PostDTO.class));

        servlet.doPost(request, response);

        verify(postService).createPost(any(PostDTO.class));
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

}
