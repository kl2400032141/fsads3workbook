package com.klu.app;

import org.hibernate.*;
import com.klu.model.Product;
import com.klu.util.HibernateUtil;

public class MainApp {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // CREATE
        Product p1 = new Product("Laptop", "HP Laptop", 55000, 10);
        Product p2 = new Product("Mouse", "Wireless Mouse", 1200, 40);
        session.save(p1);
        session.save(p2);
        tx.commit();

        // READ
        Product p = session.get(Product.class, 1);
        System.out.println("Retrieved: " + p.getName());

        // UPDATE
        tx = session.beginTransaction();
        p.setPrice(53000);
        session.update(p);
        tx.commit();

        // DELETE
        tx = session.beginTransaction();
        Product del = session.get(Product.class, 2);
        session.delete(del);
        tx.commit();

        session.close();
        HibernateUtil.getSessionFactory().close();
    }
}
