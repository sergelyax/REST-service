package mapper;

import dto.PostDTO;
import entity.Post;
import entity.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {

    @Test
    void postToPostDTOConversion() {
        User user = new User();
        user.setId(2);
        Post post = new Post();
        post.setId(1);
        post.setUser(user);
        post.setTitle("Title");
        post.setContent("Content");

        PostDTO postDTO = new PostMapper().postToPostDTO(post);

        assertNotNull(postDTO, "The conversion should not return null.");
        assertEquals(post.getId(), postDTO.getId(), "IDs should match after conversion.");
        assertEquals(user.getId(), postDTO.getUserId(), "User IDs should match after conversion.");
        assertEquals(post.getTitle(), postDTO.getTitle(), "Titles should match after conversion.");
        assertEquals(post.getContent(), postDTO.getContent(), "Contents should match after conversion.");
    }

    @Test
    void conversionWithNullShouldReturnNull() {
        assertNull(new PostMapper().postToPostDTO(null), "Converting null Post should return null.");
    }
}
