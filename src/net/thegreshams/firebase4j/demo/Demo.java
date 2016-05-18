package net.thegreshams.firebase4j.demo;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;
import utils.Aluno;
import utils.Materia;


public class Demo {

	public static void main(String[] args)  {

		try {
			// get the base-url (ie: 'http://gamma.firebase.com/username')
			String firebase_baseUrl = "https://tebd.firebaseio.com/materias";
			for (String s : args) {

				if (s == null || s.trim().isEmpty())
					continue;
				if (s.trim().split("=")[0].equals("baseUrl")) {
					firebase_baseUrl = s.trim().split("=")[1];
				}
			}
			if (firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty()) {
				//throw new IllegalArgumentException("Program-argument 'baseUrl' not found but required");
				System.out.println("firebase null");
			}

			// create the firebase
			Firebase firebase = new Firebase(firebase_baseUrl);

			// "DELETE" (the fb4jDemo-root)
			FirebaseResponse response; //= firebase.delete();

			response = firebase.get();
			//System.out.println("\n\nResult of GET:\n" + response);
			//System.out.println("\n");

			//Map<String, Object> map = response.getBody();
			
			//System.out.println(map.size());

			ObjectMapper m = new ObjectMapper();
			
			ArrayList<Materia> materias = new ArrayList<>();
			
			materias = m.readValue(response.getRawBody(), new TypeReference<ArrayList<Materia>>(){});
			
			if(materias == null){
				System.out.println("NULL");
				return;
			}
			
			for(Materia m1 : materias){
				System.out.println(m1.toString());
			}
			
			
			Materia materiaNova = new Materia();
			
			materiaNova.alunos = new ArrayList<Aluno>();
			materiaNova.nome = "bd1";
			
			Aluno caio = new Aluno();
			caio.nome = "Caio Barbosa";
			caio.matricula = "1";
			
			
			materiaNova.prof = "Fabio Coutinho";
			
			materiaNova.alunos.add(caio);
			
			
			
			materias.add(materiaNova);
			
			String j = m.writeValueAsString(materias);
			
			System.out.println(j);
			
			response = firebase.put(j);
			
			response = firebase.get();
			
			ObjectMapper mapper = new ObjectMapper();
			
			ArrayList<Materia> materias2 = new ArrayList<>();
			
			materias2 = mapper.readValue(response.getRawBody(), new TypeReference<ArrayList<Materia>>(){});
			
			if(materias2 == null){
				System.out.println("NULL");
				return;
			}
			
			for(Materia m1 : materias){
				System.out.println(m1.toString());
			}
			
			// "PUT" (test-map into the fb4jDemo-root)
			//Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			//dataMap.put("PUT-root", "This was PUT into the fb4jDemo-root");
			//response = firebase.put(dataMap);
			
			//FirebaseResponse responsePut = firebase.put(response.getRawBody());
			
			//System.out.println("\n\nResult of PUT (for the test-PUT to fb4jDemo-root):\n" + responsePut);
			//System.out.println("\n");
			
			//System.out.println(map.size());

			// "GET" (the fb4jDemo-root)
			
			//response = firebase.get();
			//System.out.println("\n\nResult of GET:\n" + response);
			//System.out.println("\n");
			//HashMap<String, Object> data = (HashMap<String, Object>) response.getBody();
			//System.out.println(data.get("bd1"));

			// "PUT" (test-map into a sub-node off of the fb4jDemo-root)
			//dataMap = new LinkedHashMap<String, Object>();
			//dataMap.put("Key_1", "This is the first value");
			//dataMap.put("Key_2", "This is value #2");
			//Map<String, Object> dataMap2 = new LinkedHashMap<String, Object>();
			//dataMap2.put("Sub-Key1", "This is the first sub-value");
			//dataMap.put("Key_3", dataMap2);
			//response = firebase.put("test-PUT", dataMap);
			//System.out.println("\n\nResult of PUT (for the test-PUT):\n" + response);
			//System.out.println("\n");

			// "GET" (the test-PUT)
			//response = firebase.get("test-PUT");
			//System.out.println("\n\nResult of GET (for the test-PUT):\n" + response);
			//System.out.println("\n");

			// "POST" (test-map into a sub-node off of the fb4jDemo-root)
			//response = firebase.post("test-POST", dataMap);
			//System.out.println("\n\nResult of POST (for the test-POST):\n" + response);
			//System.out.println("\n");

			// "DELETE" (it's own test-node)
			//dataMap = new LinkedHashMap<String, Object>();
			//dataMap.put("DELETE", "This should not appear; should have been DELETED");
			//response = firebase.put("test-DELETE", dataMap);
			//System.out.println("\n\nResult of PUT (for the test-DELETE):\n" + response);
			//response = firebase.delete("test-DELETE");
			//System.out.println("\n\nResult of DELETE (for the test-DELETE):\n" + response);
			//response = firebase.get("test-DELETE");
			//System.out.println("\n\nResult of GET (for the test-DELETE):\n" + response);

		} catch (Exception e) {
			e.printStackTrace();
		} catch (FirebaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
