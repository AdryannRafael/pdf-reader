package com.adinho.pdf_reader.pdf_reader;

import java.math.BigDecimal;

public class ExtracaoCalulos {
    private String nome;
    private String cpf;
    private BigDecimal principal;
    private BigDecimal juros;
    private BigDecimal selic;
    private BigDecimal total;

    public ExtracaoCalulos() {
        this.principal = BigDecimal.ZERO;
        this.juros = BigDecimal.ZERO;
        this.selic = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }

    public ExtracaoCalulos(String nome, String cpf, BigDecimal principal, BigDecimal juros, BigDecimal selic, BigDecimal total) {
        this.nome = nome;
        this.cpf = cpf;
        this.principal = principal;
        this.juros = juros;
        this.selic = selic;
        this.total = total;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getSelic() {
        return selic;
    }

    public void setSelic(BigDecimal selic) {
        this.selic = selic;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "ExtracaoCalulos{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", principal=" + principal +
                ", juros=" + juros +
                ", selic=" + selic +
                ", total=" + total +
                '}';
    }
}
