package com.example.admin.test2;

public class MantConstructor {
    private int id;
    private String sucursal;
    private String area;
    private String nombre;
    private String tipo;
    private String empresa;
    private String fecha;

    public MantConstructor(){

    }

    public MantConstructor(int id, String sucursal, String area, String nombre, String tipo, String empresa, String fecha) {
        this.id = id;
        this.sucursal = sucursal;
        this.area = area;
        this.nombre = nombre;
        this.tipo = tipo;
        this.empresa = empresa;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public String getSucursal() {
        return sucursal;
    }

    public String getArea() {
        return area;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getFecha() {
        return fecha;
    }
}
