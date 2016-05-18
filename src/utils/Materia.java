package utils;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonView;

public class Materia {
	
	public String prof;
	public String nome;
	public ArrayList<Aluno> alunos;
	public ArrayList<Livro> livros;
	
	public Materia(){
		
	}
	
	@Override
	public String toString(){
		
		if(nome == null || nome.equals("")){
			return "";
		}
		
		String str = "";
		str += "\nNome: " + nome + "\n";
		str += "Professor: " + prof + "\n";
		str += "Alunos: \n";
		if(alunos == null){
			return str;
		}
		for(Aluno aluno : alunos){
			str += "	Nome: " + aluno.nome + "\n";
			str += "	Matrícula: " + aluno.matricula + "\n";
		}
		
		if(livros == null){
			return str;
		}
		System.out.println("Livros:\n");
		
		for(Livro l : livros){
			str += "	Titulo:" + l.titulo + "\n";
			str += "	Autor:" + l.autor + "\n";
		}
		
		
		return str;
	}
	
	
	

}
