package model;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class Usuario {

    private int id;
    private String nombre;
    private String contrasenia;
    private String tipo;
    private int habilidad;

    public Usuario(String nombre, String contrasenia) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }

    public Usuario(String nombre, String contrasenia, String tipo, int habilidad) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.tipo = tipo;
        this.habilidad = habilidad;
    }
}
