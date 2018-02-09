package com.vbalex.cedulascr;

/**
 * Created by alex on 2/19/17.
 */

public class Persona {
    private String cedula;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private char genero;
    private String fechaNacimiento;
    private String fechaVencimiento;
    private byte[] huella1;
    private byte[] huella2;

    Persona() {

    }

    public Persona(String cedula, String nombre, String apellido1, String apellido2, char genero, String fechaNacimiento, String fechaVencimiento, byte[] huella1, byte[] huella2) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaVencimiento = fechaVencimiento;
        this.huella1 = huella1;
        this.huella2 = huella2;
    }

    public void setCedula(String _ced) {
        this.cedula = _ced;
    }

    public void setNombre(String _nom) {
        this.nombre = _nom;
    }

    public void setApellido1(String _ape1) {
        this.apellido1 = _ape1;
    }

    public void setApellido2(String _ape2) {
        this.apellido2 = _ape2;
    }

    public void setGenero(char _gen) {
        this.genero = _gen;
    }

    public void setFechaNacimiento(String _fecN) {
        this.fechaNacimiento = _fecN;
    }

    public void setFechaVencimiento(String _fecV) {
        this.fechaVencimiento = _fecV;
    }

    public String getCedula() {
        return this.cedula;
    }
    public String getNombre() {
        return this.nombre;
    }
    public String getApellido1() {
        return this.apellido1;
    }
    public String getApellido2() {
        return this.apellido2;
    }
    public char getGenero() {
        return this.genero;
    }
    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }
    public String getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    public byte[] getHuella1() {
        return huella1;
    }

    public void setHuella1(byte[] huella1) {
        this.huella1 = huella1;
    }

    public byte[] getHuella2() {
        return huella2;
    }

    public void setHuella2(byte[] huella2) {
        this.huella2 = huella2;
    }

    @Override
    public String toString(){
        return this.cedula +" "+
                this.apellido1 +" "+
                this.apellido2 +" "+
                this.nombre +" "+
                this.fechaNacimiento +" "+
                this.fechaVencimiento;
    }
}
