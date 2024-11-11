package servlets;


import dao.MovieDao;
import entities.Movie;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.util.Optional;


@WebServlet("/movie")
@MultipartConfig
public class MovieServlet extends HttpServlet {

    private final MovieDao movieDao = MovieDao.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        Optional<Movie> movie = movieDao.findById(Integer.parseInt(req.getParameter("id")));

    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)  {
        var id = Integer.parseInt(req.getParameter("id"));
        var title = req.getParameter("title");
        var description = req.getParameter("description");
        var catId = Integer.parseInt(req.getParameter("categoryId"));

        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setCategoryId(catId);

        movieDao.update(movie);


    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
          var id = Integer.parseInt(req.getParameter("id"));
          movieDao.delete(id);

    }
}
