package dev.luanfernandes.webclient.opencep.response;

public record OpenCepResponse(
        String uf, String complemento, String logradouro, String bairro, String localidade, String ibge, String cep) {}
