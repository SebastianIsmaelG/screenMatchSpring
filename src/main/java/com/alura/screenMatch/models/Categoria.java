package com.alura.screenMatch.models;

public enum Categoria {
    ACCION("Action","Acción"),
    ROMANCE("Romance","Romance"),
    COMEDIA("Comedy","Comedia"),
    DRAMA("Drama","Drama"),
    CRIMEN("Crime","Crimen"),
    TERROR("Terror", "Terror"),
    ANIMACION("Animation","Animation");

    private String categoriaOmdb;
    private String categoriaESP;

    Categoria(String categoriaOmdb,String categoriaESP){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaESP = categoriaESP;

    }

    public static Categoria fromString(String text){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada" + text);
    }
    public static Categoria fromESP(String text){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaESP.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada" +text);
    }
}
