package com.alura.screenMatch.service;

public interface InterfaceConvertData {
    <T> T obtenerDatos(String json,Class<T> tClass);
}
