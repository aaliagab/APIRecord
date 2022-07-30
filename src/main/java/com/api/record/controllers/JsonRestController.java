package com.api.record.controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.record.entities.Jugador;

@CrossOrigin(origins = {"http://localhost:8000","http://172.16.0.206:8000",
		"http://172.16.0.207:8000"})
@RestController
@RequestMapping("/api")
public class JsonRestController {
	@GetMapping("/jugadores")
	public static List<Jugador> getJugadores() {
		List<Jugador> jugadores = new ArrayList<>();
		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("jugadores.json")) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONArray jsonArray = (JSONArray) obj;

			int i = 0;
			while (i < jsonArray.size()) {
				JSONObject jsonObj = (JSONObject) jsonArray.get(i);
				jugadores.add(new Jugador(jsonObj.get("nombre").toString(),
						Integer.parseInt(jsonObj.get("record").toString())));
				// System.out.println(jugadores.get(i).getNombre() + " " +
				// jugadores.get(i).getRecord());
				i++;
			}
			reader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jugadores;
	}

	@GetMapping("/jugadores/sort")
	public static List<Jugador> getSortJugadores() {
		List<Jugador> jugadores = new ArrayList<>();
		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("jugadores.json")) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONArray jsonArray = (JSONArray) obj;

			int i = 0;
			while (i < jsonArray.size()) {
				JSONObject jsonObj = (JSONObject) jsonArray.get(i);
				jugadores.add(new Jugador(jsonObj.get("nombre").toString(),
						Integer.parseInt(jsonObj.get("record").toString())));
				// System.out.println(jugadores.get(i).getNombre() + " " +
				// jugadores.get(i).getRecord());
				i++;
			}
			reader.close();
			Jugador aux = null;
			for (int j = 0; j < jugadores.size() - 1; j++) {
				for (int k = j + 1; k < jugadores.size(); k++) {
					if (jugadores.get(k).getRecord() > jugadores.get(j).getRecord()) {
						aux = jugadores.get(j);
						jugadores.set(j, jugadores.get(k));
						jugadores.set(k, aux);
					}
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jugadores;
	}

	@PostMapping("/jugadores")
	public static void insertJugador(@RequestBody Jugador obj) {
		List<Jugador> jugadores = getJugadores();

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Jugador p : jugadores) {
			jsonObject = new JSONObject();
			jsonObject.put("nombre", p.getNombre());
			jsonObject.put("record", p.getRecord());
			jsonArray.add(jsonObject);
		}
		jsonObject = new JSONObject();
		jsonObject.put("nombre", obj.getNombre());
		jsonObject.put("record", obj.getRecord());
		// Add employees to list

		jsonArray.add(jsonObject);

		// Write JSON file
		try (FileWriter file = new FileWriter("jugadores.json")) {
			// We can write any JSONArray or JSONObject instance to the file
			file.write(jsonArray.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
