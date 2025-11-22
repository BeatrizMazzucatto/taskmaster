package com.taskmaster.exception;

public class ErrorResponse {
    
    private String erro;
    private String mensagem;
    
    public ErrorResponse() {
    }
    
    public ErrorResponse(String erro, String mensagem) {
        this.erro = erro;
        this.mensagem = mensagem;
    }
    
    public ErrorResponse(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public String getErro() {
        return erro;
    }
    
    public void setErro(String erro) {
        this.erro = erro;
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}

