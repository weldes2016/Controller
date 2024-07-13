package br.com.controller.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConexaoHibernate {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("directus");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void main(String[] args) {
        System.err.println(emf.getMetamodel().toString());
    }
}
