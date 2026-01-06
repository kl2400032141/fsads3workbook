package com.klu.app;

import java.util.List;
import org.hibernate.*;
import com.klu.model.Product;
import com.klu.util.HibernateUtil;

public class HQLApp {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // Insert products
        session.save(new Product("Laptop", "Electronics", 55000, 10));
        session.save(new Product("Mouse", "Electronics", 1200, 40));
        session.save(new Product("Keyboard", "Electronics", 1500, 30));
        session.save(new Product("Pen", "Stationery", 20, 100));
        session.save(new Product("Notebook", "Stationery", 80, 60));
        session.save(new Product("Chair", "Furniture", 3000, 8));

        tx.commit();

        // Sorting
        session.createQuery("from Product p order by p.price asc", Product.class)
                .list().forEach(p -> System.out.println(p.getName()+" "+p.getPrice()));

        session.createQuery("from Product p order by p.quantity desc", Product.class)
                .list().forEach(p -> System.out.println(p.getName()+" "+p.getQuantity()));

        // Pagination
        Query<Product> page1 = session.createQuery("from Product", Product.class);
        page1.setFirstResult(0);
        page1.setMaxResults(3);

        Query<Product> page2 = session.createQuery("from Product", Product.class);
        page2.setFirstResult(3);
        page2.setMaxResults(3);

        // Aggregates
        Long count = (Long) session.createQuery("select count(p) from Product p").uniqueResult();
        Object[] minMax = (Object[]) session.createQuery(
                "select min(p.price), max(p.price) from Product p").uniqueResult();

        // Group By
        List<Object[]> group =
                session.createQuery("select p.description, count(p) from Product p group by p.description").list();

        // LIKE
        session.createQuery("from Product p where p.name like 'M%'", Product.class)
                .list().forEach(p -> System.out.println(p.getName()));

        session.close();
        HibernateUtil.getSessionFactory().close();
    }
}
