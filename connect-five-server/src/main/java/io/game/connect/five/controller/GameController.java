package io.game.connect.five.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.game.connect.five.model.Game;
import io.game.connect.five.service.GameService;

@RestController
public class GameController {

	@Autowired
	private GameService gameService;
	
	@GetMapping("/games")
	public Collection<Game> games() {
		return gameService.getGames();
	}
}
