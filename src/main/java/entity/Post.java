package entity;

public class Post {
    private int id;
    private User user;
    private String title;
    private String content;

    public Post() {}

    public Post(int id, User user, String title, String content) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId() {
        return user != null ? user.getId() : null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserId(int userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(userId);
    }


    @Override
    public String toString() {
        String userInfo = user != null ? user.toString() : "null";
        return "Post{" +
                "id=" + id +
                ", user=" + userInfo +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
