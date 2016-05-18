package br.ufal.ic.util;

import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;
import utils.Aluno;
import utils.Materia;

public class FirebaseUtil {

	private Firebase firebase;

	private ArrayList<Materia> materias;

	public FirebaseUtil() {
		materias = new ArrayList<>();
	}

	public Firebase iniciateServerConnection() {

		try {
			String firebase_baseUrl = "https://tebd.firebaseio.com/materias";

			if (firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty()) {
				// throw new IllegalArgumentException("Program-argument
				// 'baseUrl' not found but required");
				System.out.println("firebase null");
			}

			// create the firebase
			firebase = new Firebase(firebase_baseUrl);

			return firebase;

		} catch (Exception e) {
			e.printStackTrace();
		} catch (FirebaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Materia> collectData() {

		try {
			FirebaseResponse response; // = firebase.delete();

			response = firebase.get();
			// System.out.println("\n\nResult of GET:\n" + response);
			// System.out.println("\n");

			// Map<String, Object> map = response.getBody();

			// System.out.println(map.size());

			ObjectMapper m = new ObjectMapper();

			materias = m.readValue(response.getRawBody(), new TypeReference<ArrayList<Materia>>() {
			});

			return materias;

		} catch (Exception e) {
			e.printStackTrace();
		} catch (FirebaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public ArrayList<Materia> consult(String nome, boolean print) {

		nome = nome.toLowerCase().trim().replaceAll(" ", "");
		
		//if(nome == null){
		//	return null;
		//}

		ArrayList<Materia> mat = new ArrayList<>();

		if (materias == null) {
			return null;
		}
		
		System.out.println(nome);

		boolean flag = false;
		boolean materia = false;

		for (Materia m : materias) {
			if (m.prof.toLowerCase().equals(nome)) {
				flag = true;
			} else if (m.nome.toLowerCase().equals(nome)) {
				materia = true;
				flag = true;
				
			} else {
				ArrayList<Aluno> alunos = m.alunos;

				if (alunos == null) {
					continue;
				}

				for (Aluno a : alunos) {
					if (a.nome.toLowerCase().equals(nome)) {
						flag = true;
					} else if (a.matricula.toLowerCase().equals(nome)) {
						flag = true;
					}
				}
			}

			if (flag) {
				if (print) {
					System.out.println(m.toString());
				}
				mat.add(m);
				if(materia){
					break;
				}
			}
		}

		return mat;

	}
}
