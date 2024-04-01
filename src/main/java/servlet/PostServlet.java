package servlet;

import dto.PostDTO;
import service.PostService;
import dto.UserDTO;
import service.UserService;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/posts/*")
public class PostServlet extends HttpServlet {

    private PostService postService;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        super.init();
        this.postService = (PostService) getServletContext().getAttribute("postService");
    }

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (pathInfo == null || pathInfo.equals("/")) {
            String userId = req.getParameter("userId");
            if (userId != null) {
                try {
                    int id = Integer.parseInt(userId);
                    List<PostDTO> userPosts = postService.getPostsByUserId(id);
                    out.print(gson.toJson(userPosts));
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Invalid user ID format\"}");
                }
            } else {
                out.print(gson.toJson(postService.getAllPosts()));
            }
        } else {
            try {
                String[] splits = pathInfo.split("/");
                if (splits.length == 2) {
                    int id = Integer.parseInt(splits[1]);
                    PostDTO post = postService.getPostById(id);
                    if (post != null) {
                        out.print(gson.toJson(post));
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print("{\"error\":\"Post not found\"}");
                    }
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Invalid request\"}");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid post ID format\"}");
            }
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostDTO post = gson.fromJson(req.getReader(), PostDTO.class);
        postService.createPost(post);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_CREATED);
        out.print(gson.toJson(post));
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            String[] splits = pathInfo.split("/");
            if(splits.length == 2) {
                PostDTO post = gson.fromJson(req.getReader(), PostDTO.class);
                post.setId(Integer.parseInt(splits[1])); // Set ID from URL
                postService.updatePost(post);
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(gson.toJson(post));
                out.flush();
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            String[] splits = pathInfo.split("/");
            if(splits.length == 2) {
                int id = Integer.parseInt(splits[1]);
                postService.deletePost(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
