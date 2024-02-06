package org.jarenas.ca.java.jdbc;

import org.jarenas.ca.java.jdbc.model.Producto;
import org.jarenas.ca.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.jarenas.ca.java.jdbc.repositorio.Repositorio;
import org.jarenas.ca.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.Scanner;

public class EjemploJdbc {
    public static void main(String[] args) {

        //Instanciando los objetos en los parantesis del try, usamos el autoclose de estos
        try (Connection conn = ConexionBaseDatos.getInstance()) {

            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();

            menu(repositorio);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Producto newProducto() {
        Producto monitor = new Producto();
        Scanner sc = new Scanner(System.in);

        System.out.println("REGISTRAR NUEVO PRODCUTO");


        return monitor;
    }

    public static void menu(Repositorio rp) {
        Scanner sc = new Scanner(System.in);
        int op = 0;
        Producto prod = new Producto();
        Long idProducto = 0L;
        System.out.println(" === BIENVENIDO === ");
        do {
            System.out.println("Ingrese la opcion que desea: \n" +
                    "1. Listar \n" +
                    "2. Buscar por ID \n" +
                    "3. Guardar o Actualizar \n" +
                    "4. Actualizar producto \n" +
                    "5. Eliminar producto \n"+
                    "6. Salir \n");
            op = sc.nextInt();
            System.out.println("===============================================");
            switch (op) {
                case 1:
                    System.out.println(" === LISTADO DE PRODUCTOS ===");
                    rp.listar().forEach(System.out::println);
                    System.out.println("===============================================");
                    break;
                case 2:
                    System.out.println(" === Ingrese el ID del producto ===");
                    idProducto = sc.nextLong();
                    System.out.println(" === PRODUCTO === ");
                    System.out.println(rp.porId(idProducto));
                    System.out.println("===============================================");
                    break;
                case 3:
                    System.out.println(" === Guardar producto === ");
                    System.out.println("Ingrese el Nombre del producto: ");
                    sc.skip("\n");
                    prod.setNombre(sc.nextLine());
                    System.out.println("Ingrese el precio del producto");
                    prod.setPrecio(sc.nextInt());
                    prod.setFechaRegistro(new java.util.Date());
                    rp.guardar(prod);
                    rp.listar().forEach(System.out::println);
                    System.out.println(" PRODUCTO GUARDADO CON EXITO ");
                    System.out.println("===============================================");
                    break;
                case 4:
                    System.out.println(" === Actualizar producto === ");
                    System.out.println(" Ingrese el id del producto: ");
                    prod.setId(sc.nextLong());
                    System.out.println("Ingrese el nuevo nombre: ");
                    sc.skip("\n");
                    prod.setNombre(sc.nextLine());
                    System.out.println("Ingrese el nuevo precio: ");
                    prod.setPrecio(sc.nextInt());
                    rp.guardar(prod);
                    rp.listar().forEach(System.out::println);
                    System.out.println(" PRODUCTO ACTUALIZADO CON EXITO ");
                    break;
                case 5:
                    System.out.println(" === ELIMINAR PRODUCTO === ");
                    System.out.println(" Ingrese el id del producto ");
                    idProducto = sc.nextLong();
                    rp.eliminar(idProducto);
                    rp.listar().forEach(System.out::println);
                    System.out.println("===============================================");
                    break;
            }
        } while (op != 6);

    }

}

