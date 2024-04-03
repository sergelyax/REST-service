package service;
import java.util.List;
import java.util.stream.Collectors;
// Импорты из пакета сущностей
import entity.Post;

// Импорты из пакета DTO
import dto.PostDTO;

// Импорты из пакета DAO
import dao.PostDAO;

// Импорты из пакета мапперов, если вы используете мапперы для преобразования сущностей в DTO и обратно
import mapper.PostMapper;

public class PostService {
    private final PostDAO postDAO;
    private final PostMapper postMapper;

    public PostService(PostDAO postDAO, PostMapper postMapper) {
        this.postDAO = postDAO;
        this.postMapper = postMapper;
    }

    public PostDTO getPostById(int id) {
        Post post = postDAO.getPostById(id);
        return post != null ? postMapper.postToPostDTO(post) : null;
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postDAO.getAllPosts();
        return posts.stream().map(postMapper::postToPostDTO).collect(Collectors.toList());
    }

    public List<PostDTO> getPostsByUserId(int userId) {
        List<Post> userPosts = postDAO.getPostsByUserId(userId);
        return userPosts.stream().map(postMapper::postToPostDTO).collect(Collectors.toList());
    }

    public void createPost(PostDTO postDTO) {
        Post post = postMapper.postDTOToPost(postDTO);
        postDAO.createPost(post);
        postDTO.setId(post.getId());
    }

    public void updatePost(PostDTO postDTO) {
        Post post = postMapper.postDTOToPost(postDTO);
        postDAO.updatePost(post);
    }

    public void deletePost(int id) {
        postDAO.deletePost(id);
    }
}
