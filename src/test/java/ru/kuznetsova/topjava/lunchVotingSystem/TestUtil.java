package ru.kuznetsova.topjava.lunchVotingSystem;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import ru.kuznetsova.topjava.lunchVotingSystem.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class TestUtil {

    public static String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public static ResultActions print(ResultActions action) throws UnsupportedEncodingException {
        System.out.println(getContent(action.andReturn()));
        return action;
    }

    public static <T> T readFromJsonResultActions(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return readFromJsonMvcResult(action.andReturn(), clazz);
    }

    public static <T> T readFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(result), clazz);
    }

    public static <T> List<T> readListFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValues(getContent(result), clazz);
    }

}