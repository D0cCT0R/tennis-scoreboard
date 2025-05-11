package com.example.tennisscoreboard.dao;

import com.example.tennisscoreboard.models.entity.Player;
import com.example.tennisscoreboard.util.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;


public class PlayersDao {

    public Player findOrCreate(String name) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            String hql = "from Player where name = :name";
            Player player = session.createQuery(hql, Player.class)
                    .setParameter("name", name)
                    .uniqueResult();
            if (player == null) {
                player = Player.builder()
                        .name(name)
                        .build();
                try {
                    session.persist(player);
                    session.flush();
                } catch (PersistenceException e) {
                    session.getTransaction().rollback();
                    return findOrCreate(name);
                }
            }
            session.getTransaction().commit();
            return player;
        }

    }
}
