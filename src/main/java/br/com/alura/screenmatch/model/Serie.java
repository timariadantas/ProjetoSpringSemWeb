package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.traducao.ConsultaMyMemory;
import jakarta.persistence.*;
import org.intellij.lang.annotations.Identifier;


import java.util.OptionalDouble;

@Entity
@Table(name = "series")

public class Serie{
        @Id
        private Long id;
        @Column(unique = true)
        private String titulo;
        private Integer totalTemporadas;
        private Double avaliacao;
        @Enumerated(EnumType.STRING)
        private Categoria  genero;
        private String atores;
        private String poster;
        private String sinopse;

        // construtor
        public Serie (DadosSerie dadosSerie){
                this.titulo = dadosSerie.titulo();
                this.totalTemporadas = dadosSerie.totalTemporadas();
                this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0); // if else melhorado, poderia usar tambem try catch
                this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
                this.atores = dadosSerie.atores();
                this.poster = dadosSerie.poster();
                this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()).trim(); // metodopara nenhum caracter em branco
        }

        public String getTitulo() {
                return titulo;
        }

        public void setTitulo(String titulo) {
                this.titulo = titulo;
        }

        public Integer getTotalTemporadas() {
                return totalTemporadas;
        }

        public void setTotalTemporadas(Integer totalTemporadas) {
                this.totalTemporadas = totalTemporadas;
        }

        public Double getAvaliacao() {
                return avaliacao;
        }

        public void setAvaliacao(Double avaliacao) {
                this.avaliacao = avaliacao;
        }

        public Categoria getGenero() {
                return genero;
        }

        public void setGenero(Categoria genero) {
                this.genero = genero;
        }

        public String getAtores() {
                return atores;
        }

        public void setAtores(String atores) {
                this.atores = atores;
        }

        public String getPoster() {
                return poster;
        }

        public void setPoster(String poster) {
                this.poster = poster;
        }

        public String getSinopse() {
                return sinopse;
        }

        public void setSinopse(String sinopse) {
                this.sinopse = sinopse;
        }

        @Override
        public String toString() {
                return
                        "genero=" + genero + '\'' +
                        "titulo='" + titulo + '\'' +
                        ", totalTemporadas=" + totalTemporadas +
                        ", avaliacao=" + avaliacao +
                        ", atores='" + atores + '\'' +
                        ", poster='" + poster + '\'' +
                        ", sinopse='" + sinopse + '\'' ;
        }
}




//metodo split tudo que for antes da virgula, [0] primeiro indexo do array de caracteres