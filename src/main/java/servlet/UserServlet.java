package servlet;

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

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

    private UserService userService;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (pathInfo == null || pathInfo.equals("/")) {
            out.print(gson.toJson(userService.getAllUsers()));
        } else {
            try {
                String[] splits = pathInfo.split("/");
                if(splits.length == 2) {
                    int id = Integer.parseInt(splits[1]);
                    UserDTO user = userService.getUserById(id);
                    out.print(gson.toJson(user));
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Invalid user ID format\"}");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid user ID format\"}");
            }
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = gson.fromJson(req.getReader(), UserDTO.class);
        userService.createUser(userDTO);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_CREATED);
        out.print(gson.toJson(userDTO));
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
                UserDTO userDTO = gson.fromJson(req.getReader(), UserDTO.class);
                userDTO.setId(Integer.parseInt(splits[1])); // Установить ID из URL в DTO
                userService.updateUser(userDTO);
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(gson.toJson(userDTO));
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
                userService.deleteUser(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
