/**
 * Класс для работы с базой данных
 *
 * @author Popov Vitaliy (Totalexx)
 */

package ru.totalexx;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Database {

    // Параметры подключения к БД
    private final String USER = "postgres";
    private final String PASSWORD = "postgres";
    private final String URL_DB = "jdbc:postgresql://localhost:5432/Hakaton";

    private Statement statement;

    /**
     * Конструктор класса Database, добавляет jdbs driver postgresql
     */
    public Database () {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод получает все таблицы, которые хранят нужный объект, и возвращает
     * сопоставление колонок и названий таблиц
     *
     * @param typeObject Тип объектов, которые необходимо отобрать
     * @return HashMap, в котором колонка указывает на хранящую её таблицу
     */
    public LinkedHashMap<String, String> getColumns(String typeObject) {

        LinkedHashMap<String, String> columns = new LinkedHashMap();
        openConnection();

        try {
            // Получаем все имена колонок и соотвествующие им таблицы
            ResultSet result = statement.executeQuery(  "SELECT table_name, column_name " +
                                                            "FROM information_schema.columns " +
                                                            "WHERE table_schema='public' AND table_name LIKE '" + typeObject + "_%'");

            // Записываем полученные данные в HashMap
            while (result.next()) {
                String tableName = result.getString("table_name");
                String columnName = result.getString("column_name");
                columns.put(columnName, tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

        return columns;
    }

    /**
     * Метод для генерации sql-запроса по параметрам
     *
     * @param selection
     * @return sql-запрос с использованием алиасов
     */
    public String compareTables(Selection selection) {
        //Получаем параметры отбора и тип объекта
        HashMap<String, String> constraints = selection.getConstraints();
        String typeObject = constraints.get("typeObject");
        constraints.remove("typeObject");

        // Если typeObject не получен, то возвращаем ошибку
        if (typeObject.equals("") || typeObject.equals(null))
            return "Error: typeObject not found";

        // Получаем список колонок и к какой таблице относится каждая колонка
        LinkedHashMap<String, String> columns = getColumns(typeObject);

        // Строка в которую будет записываться sql-запрос
        String sql = "SELECT ";

        // Hashmap, хранящий соответствие названия таблицы и алиаса
        Character alias = 'a';
        LinkedHashMap<String, Character> aliases = new LinkedHashMap<>();

        // Переменная для удобной записи частей sql запроса
        StringJoiner sqlJoiner = new StringJoiner(", ");

        // Указываем, какие колонки из каких таблиц нужно выбрать. Заменяем названия таблиц алиасами
        for (Map.Entry<String, String> entry : columns.entrySet()) {
            String columnName = entry.getKey();
            String tableName = entry.getValue();

            // Добавление алиасов таблиц
            if (!aliases.containsKey(tableName)) {
                aliases.put(tableName, alias);
                alias++;
            }

            // Добавляем колонки с заменой таблиц на алиасы
            sqlJoiner.add(aliases.get(tableName) + "." + columnName);
        }

        // Добавляем полученное значение к запросу
        sql += sqlJoiner.toString();

        boolean isFirst = true;
        sqlJoiner = new StringJoiner(" ").add("");

        // Формируем FROM секцию с JOIN'ом всех таблиц и указываем алиасы для таблиц
        for (Map.Entry<String, Character> entry : aliases.entrySet()) {
            String table = entry.getKey();
            alias = entry.getValue();

            if (isFirst) {
                sqlJoiner.add("FROM").add(table).add(alias.toString());
                isFirst = false;
                continue;
            }

            sqlJoiner.add("JOIN")
                    .add(table)
                    .add(alias.toString())
                    .add("ON")
                    .add(alias + ".id")
                    .add("= a.id");
        }

        sql += sqlJoiner.toString();

        sqlJoiner = new StringJoiner(" AND ");
        isFirst = true;

        // Формируем WHERE секцию, сопоставляем параметры отбора с колонками таблицы
        for (Map.Entry<String, String> entry : constraints.entrySet()) {
            String constraint = entry.getValue();

            // Если по данной колонке не нужно отбирать, то пропускаем её
            if (constraint.equals(""))
                continue;

            if (isFirst) {
                sql += " WHERE ";
                isFirst = false;
            }

            String column = entry.getKey();
            alias = aliases.get(columns.get(column));

            sqlJoiner.add(alias + "." + column + " " + constraint);
        }

        sql += sqlJoiner.toString();

        return sql;
    }

    /**
     * Открываем соединение с БД
     */
    public void openConnection() {
        try {
            statement = DriverManager.getConnection(URL_DB, USER, PASSWORD).createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Закрываем соединение с БД
     */
    public void closeConnection() {
        try {
            Connection connection = statement.getConnection();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
