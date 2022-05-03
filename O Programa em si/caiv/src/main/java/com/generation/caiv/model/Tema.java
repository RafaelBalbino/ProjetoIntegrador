package com.generation.caiv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table (name = "tb_tema")
public class Tema {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY) 
		private Long id;
		
		@NotBlank
		@Size (min = 2 , max = 50, message = "O tema tem no mínimo 2 caractéres e no máximo 50.")
		public String nome;
		
		@NotBlank
		@Size (min = 5 , max = 100, message = "A descrição tem no mínimo 5 caractéres e no máximo 100.")
		public String descricao;
		
		// @OneToMany(mappedBy = "tema", cascade = CascadeType.ALL)
		// @JsonIgnoreProperties("tema")

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

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}	
}

