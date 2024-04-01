package service;

import dto.PostDTO;
import entity.Post;
import dao.PostDAO;
import mapper.PostMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private PostDAO postDAO;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPostByIdTest() {
        Post post = new Post();
        post.setId(1);
        when(postDAO.getPostById(1)).thenReturn(post);

        PostDTO postDTO = new PostDTO();
        when(postMapper.postToPostDTO(post)).thenReturn(postDTO);

        PostDTO result = postService.getPostById(1);

        assertNotNull(result);
        verify(postDAO).getPostById(1);
        verify(postMapper).postToPostDTO(post);
    }

    @Test
    void getAllPostsTest() {
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postDAO.getAllPosts()).thenReturn(posts);

        List<PostDTO> postDTOs = Arrays.asList(new PostDTO(), new PostDTO());
        when(postMapper.postToPostDTO(any(Post.class))).thenReturn(postDTOs.get(0), postDTOs.get(1));

        List<PostDTO> results = postService.getAllPosts();

        assertNotNull(results);
        assertEquals(2, results.size());
        verify(postDAO).getAllPosts();
        verify(postMapper, times(posts.size())).postToPostDTO(any(Post.class));
    }

    @Test
    void createPostTest() {
        PostDTO postDTO = new PostDTO();
        Post post = new Post();

        try (MockedStatic<PostMapper> mockedStatic = Mockito.mockStatic(PostMapper.class)) {
            mockedStatic.when(() -> PostMapper.postDTOToPost(any(PostDTO.class))).thenReturn(post);

            postService.createPost(postDTO);

            mockedStatic.verify(() -> PostMapper.postDTOToPost(any(PostDTO.class)));
            verify(postDAO).createPost(post);
        }
    }

    @Test
    void updatePostTest() {
        try (MockedStatic<PostMapper> mocked = Mockito.mockStatic(PostMapper.class)) {
            PostDTO postDTO = new PostDTO();
            Post post = new Post();

            mocked.when(() -> PostMapper.postDTOToPost(postDTO)).thenReturn(post);

            postService.updatePost(postDTO);

            mocked.verify(() -> PostMapper.postDTOToPost(any(PostDTO.class)));
            verify(postDAO).updatePost(post);
        }
    }

    @Test
    void deletePostTest() {
        int postId = 1;
        doNothing().when(postDAO).deletePost(postId);

        postService.deletePost(postId);

        verify(postDAO).deletePost(postId);
    }
}
