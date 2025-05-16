package dev.luanfernandes.service;

public interface EmailService {
    void enviarEmail(String para, String assunto, String corpo);
}
