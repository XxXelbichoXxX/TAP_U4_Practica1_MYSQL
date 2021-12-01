/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author black
 */
public class Producto {
    String descripcion;
    float precio;
    int idProducto, existencia;

    public Producto() {

    }

    public Producto(String d, float p, int e) {
        descripcion = d;
        precio = p;
        existencia = e;
    }
}
