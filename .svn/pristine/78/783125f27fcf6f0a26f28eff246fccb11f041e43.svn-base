package ocrm.labex.feevale.br.ocr_mobile.utils;

import org.w3c.dom.DOMException;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.util.EmptyStackException;

/**
 * Created by 0118424 on 24/11/2014.
 */
public class ExceptionOCR {

    private MessageResponse message = null;
    private String msg = null;

    /*
    * Para usar a classe:
    * Ao instanciar um objeto ExceptionOCR é passado o objeto da
    * exception pega.
    * Para retornar a mensagem utiliza-se o método .alert()
    * este retorna um MessageResponse da exception pega.
    *
    * */

    public ExceptionOCR(Exception e) {
        if(e instanceof NullPointerException){
            msg = "Valor nulo";
        }else if(e instanceof ArithmeticException){
            msg = "Divisão por zero";
        }else if(e instanceof ArrayStoreException){
            msg = "Tipo que está tentando ser armazenado não é o tipo declarado";
        }else if(e instanceof BufferOverflowException){
            msg = "Buffer está muito pesado";
        }else if(e instanceof BufferUnderflowException){
            msg = "Dados retornados ultrapassam o limite de Buffer";
        }else if(e instanceof ClassCastException){
            msg = "O casting que você está tentando fazer não se aplica";
        }else if(e instanceof DOMException){
            msg = "Operação em execução é anormal";
        }else if(e instanceof EmptyStackException){
            msg = "Pilha está vazia";
        }else if(e instanceof IllegalArgumentException){
            msg = "Argumento inesperado";
        }else if(e instanceof UnsupportedOperationException){
            msg = "Operador informado não era esperado";
        }else if(e instanceof  IndexOutOfBoundsException){
            msg = "A posição que está sendo acessada não existe";
        }

    }
    public MessageResponse alert(){
        message = new MessageResponse(this.msg,false);
        return message;
    }






}