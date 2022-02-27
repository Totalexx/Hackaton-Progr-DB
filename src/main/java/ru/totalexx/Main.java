/**
 * Команда TrueWay. Хакатон TV Neuro Technologies
 * Кейс 3. Программирование базы данных
 *
 * @author Popov Vitaliy (Totalexx)
 *
 */

package ru.totalexx;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Main extends HttpServlet {

    /**
     *  Ожидаем post-запрос по адресу http://localhost:8080/sql c json,
     *  когда пришёл, формируем sql-запрос и отправляем его
     *
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Получаем json и создаём POJO, который хранит параметры отбора записей
        String jsonSelection = req.getParameter("constraints");
        Selection selection = new Selection(jsonSelection);

        // Подключаемся к базе данных, составляем sql-запрос и выводим его в браузер
        Database database = new Database();
        String sql = database.compareTables(selection);
        resp.getWriter().write(sql);
    }

}
