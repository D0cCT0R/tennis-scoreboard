package com.example.tennisscoreboard.dao;


import com.example.tennisscoreboard.exception.MatchSaveException;
import com.example.tennisscoreboard.exception.MatchSearchException;
import com.example.tennisscoreboard.models.entity.Match;
import com.example.tennisscoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.query.Query;

import java.util.List;

public class MatchesDao {

    public List<Match> getAll(int page, int size, String playerName) throws MatchSearchException {
        try (Session session = HibernateUtil.getSession()) {
            try {
                String hql = "FROM Match m WHERE (:playerName IS NULL OR m.player1.name LIKE :playerName OR m.player2.name LIKE :playerName)";
                session.beginTransaction();
                Query<Match> matches = session.createQuery(hql, Match.class)
                        .setParameter("playerName", "%" + playerName + "%")
                        .setFirstResult((page - 1) * size)
                        .setMaxResults(size);
                if (playerName == null || playerName.isBlank()) {
                    matches.setParameter("playerName", null);
                }
                session.getTransaction().commit();
                return matches.getResultList();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new MatchSearchException("Не удалось найти матчи");
            }
        }
    }

    public void save(Match match) throws MatchSaveException {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            try {
                session.save(match);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new MatchSaveException("Не удалось добавить матч");
            }
        }
    }

    public Long getTotalProducts(String playerName) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "SELECT COUNT(*) FROM Match m WHERE (:playerName IS NULL OR m.player1.name LIKE :playerName OR m.player2.name LIKE :playerName)";
            Query<Long> query = session.createQuery(hql, Long.class).setParameter("playerName", "%" + playerName + "%");
            if (playerName == null || playerName.isEmpty()) {
                query.setParameter("playerName", playerName);
            }
            return query.uniqueResult();
        }
    }
}
