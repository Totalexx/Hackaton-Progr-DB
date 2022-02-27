/**
 * POJO, хранящий параметры отбора объектов
 *
 * @author Popov Vitaliy (Totalexx)
 */

package ru.totalexx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class Selection {

    private HashMap<String, String> constraints;

    /**
     * Конструктор, который десериализует HashMap параметров отбора
     *
     * @param jsonSelection Строка, содержащая JSON c ограничениями
     */
    public Selection(String jsonSelection) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            constraints = mapper.readValue(jsonSelection, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращаем HashMap параметров отбора
     *
     * @return HashMap с типом объекта и ограничениями
     */
    public HashMap<String, String> getConstraints() {
        return constraints;
    }
}
