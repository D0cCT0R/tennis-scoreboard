package com.example.tennisscoreboard.dao;

import com.example.tennisscoreboard.exception.PlayerCreateException;
import com.example.tennisscoreboard.exception.PlayerSearchException;
import com.example.tennisscoreboard.models.entity.Player;
import com.example.tennisscoreboard.util.HibernateUtil;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;


public class PlayersDao {

    public Player findOrCreate(String name) throws PlayerCreateException, PlayerSearchException {
        try (Session session = HibernateUtil.getSession()) {
            try {
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
                    } catch (ConstraintViolationException e) {
                        session.getTransaction().rollback();
                        return findOrCreate(name);
                    } catch (JDBCException e) {
                        session.getTransaction().rollback();
                        throw new PlayerCreateException("Не удалось создать игрока");
                    }
                }
                session.getTransaction().commit();
                return player;
            } catch (JDBCException e) {
                session.getTransaction().rollback();
                throw new PlayerSearchException("Не удалось найти игрока");
            }
        }
    }
}
