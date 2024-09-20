package br.com.checkok.model.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String telefone;
	private String email;
	private String cpf;
	private String cnpj;
	private Timestamp dataCriacao;

	private List<Endereco> enderecos;

	public Cliente() {
	}

	public Cliente(Long id, String nome, String telefone, String email, String cpf, String cnpj, Timestamp dataCriacao,
			List<Endereco> enderecos) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.dataCriacao = dataCriacao;
		this.enderecos = enderecos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, cpf, dataCriacao, email, enderecos, id, nome, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(dataCriacao, other.dataCriacao) && Objects.equals(email, other.email)
				&& Objects.equals(enderecos, other.enderecos) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(telefone, other.telefone);
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", cpf=" + cpf
				+ ", cnpj=" + cnpj + ", dataCriacao=" + dataCriacao + ", enderecos=" + enderecos + "]";
	}
}
