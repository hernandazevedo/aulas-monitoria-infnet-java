package com.pessoa;

public class Pessoa {
	private String nome;
	
	private Pessoa pessoa;
	private Pessoa[] listaDeContatos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pessoa[] getListaDeContatos() {
		return listaDeContatos;
	}

	public void setListaDeContatos(Pessoa[] listaDeContatos) {
		this.listaDeContatos = listaDeContatos;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
}
