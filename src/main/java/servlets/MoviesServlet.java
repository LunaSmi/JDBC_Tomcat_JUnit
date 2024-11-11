package servlets;

import dao.MovieDao;
import entities.Movie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import utils.JspHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;



@WebServlet("/movies")
@MultipartConfig
public class MoviesServlet extends HttpServlet {

    private final MovieDao movieDao = MovieDao.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        List<Movie> list = movieDao.findAll();
        req.setAttribute("list",list);

        req.getRequestDispatcher(JspHelper.getPath("movies"))
                .forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        var title = req.getParameter("title");
        var description = req.getParameter("description");
        var catId = Integer.parseInt(req.getParameter("categoryId"));
        var actorIds = req.getParameter("actorIds");
        var t = Arrays.stream(actorIds.split(" ")).map(Integer::parseInt).toList();
        int[] ids = new int[t.size()];
        for (int i = 0; i < t.size(); i++) {
            ids[i]= t.get(i);
        }
        movieDao.save(new Movie(title,description,catId,ids));
    }
}
