package br.com.tech.instabook.exceptions;

public class UserNotFoundException extends ClassNotFoundException {
    private static final long serialVersionUID = -2346384470483785588L;
    public UserNotFoundException() {
        super("Cliente não encontrado!");
    }

}

