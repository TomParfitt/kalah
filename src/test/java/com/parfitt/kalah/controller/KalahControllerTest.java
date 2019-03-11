package com.parfitt.kalah.controller;

import static com.parfitt.kalah.model.Constants.EMPTY_PIT;
import static com.parfitt.kalah.model.Constants.FIRST_PIT;
import static com.parfitt.kalah.model.Constants.INIT_PIT_SIZE;
import static com.parfitt.kalah.model.Constants.TOTAL_PITS;
import static com.parfitt.kalah.model.Player.NORTH;
import static com.parfitt.kalah.model.Player.SOUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.parfitt.kalah.model.Game;
import com.parfitt.kalah.service.KalahService;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@RunWith(SpringRunner.class)
@WebMvcTest(KalahController.class)
public class KalahControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private KalahService kalahService;

    @Test
    public void createGame() throws Exception {
        // Given
        Game game = new Game();
        game.setId(1234L);
        given(kalahService.create()).willReturn(game);
        MockHttpServletRequestBuilder request = post("/games").contentType(MediaType.APPLICATION_JSON);

        // When
        MvcResult result = mvc.perform(request).andReturn();

        // Then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"uri\":\"http://localhost/games/1234\",\"id\":\"1234\"}");
    }

    @Test
    public void makeMove() throws Exception {
        // Given
        Game game = new Game();
        game.setId(1234L);
        game.setBoard(mockBoard());
        given(kalahService.makeMove(1234L, 2)).willReturn(game);
        MockHttpServletRequestBuilder request = put("/games/1234/pits/2").contentType(MediaType.APPLICATION_JSON);

        // When
        MvcResult result = mvc.perform(request).andReturn();

        // Then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                "{\"status\":{\"1\":\"4\",\"2\":\"4\",\"3\":\"4\",\"4\":\"4\",\"5\":\"4\",\"6\":\"4\",\"7\":\"0\",\"8\":\"4\",\"9\":\"4\",\"10\":\"4\",\"11\":\"4\",\"12\":\"4\",\"13\":\"4\",\"14\":\"0\"}," +
                        "\"url\":\"http://localhost/games/1234\"," +
                        "\"id\":\"1234\"}");
    }

    private Map<Integer, Integer> mockBoard() {
        return IntStream
                .rangeClosed(FIRST_PIT, TOTAL_PITS)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> i == NORTH.getHome() || i == SOUTH.getHome() ? EMPTY_PIT : INIT_PIT_SIZE)
                );
    }
}