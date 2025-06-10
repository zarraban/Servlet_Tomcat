package org.example.app.repository.userImpl;

import org.example.app.config.HibernateUtil;
import org.example.app.entity.Product;
import org.example.app.request.RequestProductDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    private static  final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);
    @Override
    public void save(RequestProductDTO request) {
        Transaction transaction = null;
    try(Session session = HibernateUtil.getSessionFactory().openSession()){
        transaction = session.beginTransaction();

        Product product = new Product();
        product.setName(request.name());
        product.setCost(request.cost());
        product.setOrderId(request.order_id());

        session.persist(product);
        transaction.commit();
    }catch (Exception e){
        logError(e);
        Objects.requireNonNull(transaction).rollback();
    }


    }

    @Override
    public Optional<List<Product>> getAll() {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            transaction = session.beginTransaction();
            String hql = "FROM Product";
            List<Product> list = session.createQuery(hql, Product.class).list();
            transaction.commit();
           return Optional.of(list);
        }catch (Exception e){
            logError(e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> getById(Long id) {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Query<Product> query =
                    session.createQuery("FROM Product WHERE id = :id", Product.class);
            query.setParameter("id", id);
            query.setMaxResults(1);
            Product product = query.uniqueResult();

            transaction.commit();

            return Optional.ofNullable(product);
        } catch (Exception e) {

            return Optional.empty();
        }
    }

    @Override
    public void update(Long id, RequestProductDTO request) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            String hql = "UPDATE Product SET  name = :name," +
                    " cost = :cost, orderId = :order_id" +
                    " WHERE id = :id";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("name", request.name());
            query.setParameter("cost", request.cost());
            query.setParameter("order_id", request.order_id());
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            String hql = "DELETE FROM Product WHERE id = :id";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public Optional<Product> getLastEntity() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            Query<Product> query =
                    session.createQuery("FROM Product ORDER BY id DESC", Product.class);
            query.setMaxResults(1);
            Product product = query.uniqueResult();
            transaction.commit();
            return Optional.of(product);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return Optional.empty();
        }
    }

    private static void logError(Exception e){
        logger.error("Error occurred: {}",e.getMessage());
    }
}
