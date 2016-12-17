package com.example.marcia.crudproduto;

/**
 * Created by Marcia on 11/12/2016.
 */
public class Produtos {

    private String nome;
    private int codigo;
    private double valor;

    public Produtos(String nome, int codigo, double valor) {
        this.nome = nome;
        this.codigo = codigo;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
