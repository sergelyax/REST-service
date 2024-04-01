package mapper;

import dto.PostDTO;
import entity.Post;

public class PostMapper {

    public PostDTO postToPostDTO(Post post) {
        if (post == null) {
            return null;
        }
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        if (post.getUser() != null) {
            postDTO.setUserId(post.getUser().getId());
        }
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        return postDTO;
    }

    public static Post postDTOToPost(PostDTO postDTO) {
        if (postDTO == null) {
            return null;
        }
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        return post;
    }
}
