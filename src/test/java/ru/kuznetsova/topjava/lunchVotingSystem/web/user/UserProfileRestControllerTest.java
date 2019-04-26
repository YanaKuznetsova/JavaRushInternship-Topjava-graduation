package ru.kuznetsova.topjava.lunchVotingSystem.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import static ru.kuznetsova.topjava.lunchVotingSystem.UserTestData.*;
import static ru.kuznetsova.topjava.lunchVotingSystem.web.user.UserProfileRestController.REST_URL;

class UserProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @Test
    void testGet() throws Exception {
        TestUtil.print(
                mockMvc.perform(get(REST_URL))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(getUserMatcher(USER_1))
        );
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL))
                .andExpect(status().isNoContent());
        assertMatchUsers(userService.getAll(), ADMIN, USER_2, USER_3, USER_4, USER_5);
    }

    @Test
    void testUpdate() throws Exception {
        User updated = new User(USER_ID, "newName", "newemail@ya.ru", "newPassword", Role.ROLE_USER);
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatchUsers(userService.getByEmail("newemail@ya.ru"), updated);
    }

}