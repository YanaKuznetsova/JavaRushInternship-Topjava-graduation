package ru.kuznetsova.topjava.lunchVotingSystem.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.kuznetsova.topjava.lunchVotingSystem.TestUtil;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Role;
import ru.kuznetsova.topjava.lunchVotingSystem.model.User;
import ru.kuznetsova.topjava.lunchVotingSystem.service.UserService;
import ru.kuznetsova.topjava.lunchVotingSystem.web.AbstractControllerTest;
import ru.kuznetsova.topjava.lunchVotingSystem.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kuznetsova.topjava.lunchVotingSystem.TestUtil.readFromJsonResultActions;
import static ru.kuznetsova.topjava.lunchVotingSystem.UserTestData.*;

class AdminUserRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    private static final String REST_URL = AdminUserRestController.REST_URL + '/';

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getUserMatcher(ADMIN));
    }

    @Test
    void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + USER_1.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getUserMatcher(USER_1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatchUsers(userService.getAll(), ADMIN, USER_2, USER_3, USER_4, USER_5);
    }

    @Test
    void testUpdate() throws Exception {
        User updated = new User(USER_1);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatchUsers(userService.get(USER_ID), updated);
    }

    @Test
    void testCreate() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        User returned = readFromJsonResultActions(action, User.class);
        expected.setId(returned.getId());

        assertMatchUsers(returned, expected);
        assertMatchUsers(userService.getAll(), ADMIN, expected, USER_1, USER_2, USER_3, USER_4, USER_5);
    }

    @Test
    void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getUserMatcher(ADMIN, USER_1, USER_2, USER_3, USER_4, USER_5)));
    }

}