package com.infiniteskills.data;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

import com.infiniteskills.data.entities.Transaction;

public class HqlApplication {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SessionFactory factory = null;
        Session session = null;
        org.hibernate.Transaction tx = null;

        int pageNumber = 3;
        int pageSize = 4;

        try {
            factory = HibernateUtil.getSessionFactory();
            session = factory.openSession();
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(Transaction.class)
                    .addOrder(Order.asc("title"));
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);

            List<Transaction> transactions = criteria.list();

            for (Transaction t : transactions) {
                System.out.println(t.getTitle());
            }

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
            factory.close();
            scanner.close();
        }
    }
}
