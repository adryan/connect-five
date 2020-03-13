package io.game.connect.five.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.game.connect.five.model.BasicBoard;
import io.game.connect.five.model.Board;
import io.game.connect.five.model.Game;
import io.game.connect.five.service.GameService;


@AutoConfigureMockMvc
@WebMvcTest(GameController.class)
public class GameControllerTest {
	
//	@TestConfiguration
//    static class GameServiceTestContextConfiguration {
//        @Bean
//        public GameService employeeService() {
//            return Mockito.mock(GameService.class);
//        }
//    }
	
	 @MockBean
	 private GameService gameService;
	 
	 @Autowired
	 private GameController controller;
	 
	 @Autowired
	 private MockMvc mockMvc;
	 
	 @Test
	 public void testValidSetup() {
		 assertNotNull(gameService);
		 assertNotNull(controller);
	 }
	 
	 @Test
	 public void testGetGamesEmptyContent() throws Exception {
		mockMvc.perform(get("/games")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string("[]"));
	 }
	 
	 @Test
	 public void testGetGames() throws Exception {
		 
		 List<Game> games = new ArrayList<>();
		 Board board = new BasicBoard();
		 Game game = new Game(board);
		 
		 games.add(game);
		 
		 Mockito.when(gameService.getGames()).thenReturn(games);
		 
		 mockMvc.perform(get("/games")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[0].id", is(notNullValue())))
            .andExpect(jsonPath("$.[0].players", is(notNullValue())))
            .andExpect(jsonPath("$.[0].board", is(notNullValue())))
            .andExpect(jsonPath("$.[0].gameStatus", is("OPEN")));
	 }
}
