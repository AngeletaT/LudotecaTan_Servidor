package com.ccsw.tutorial.infrastructure.specifications;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.entities.Loan;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class LoanSpecification implements Specification<Loan> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public LoanSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            Path<?> path = getPath(root);
            return builder.equal(path, criteria.getValue());
        } else if (criteria.getOperation().equalsIgnoreCase("<=") && criteria.getValue() != null) {
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(">=") && criteria.getValue() != null) {
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
        }
        return null;
    }

    private Path<?> getPath(Root<Loan> root) {
        String key = criteria.getKey();
        String[] split = key.split("\\.");
        Path<?> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }
        return expression;
    }

    /**
     * Crea una Specification que filtra préstamos por el id del juego.
     *
     * @param gameId El id del juego.
     * @return Specification para filtrar por game.id.
     */
    public static Specification<Loan> gameId(Long gameId) {
        return (root, query, builder) -> builder.equal(root.get("game").get("id"), gameId);
    }

    /**
     * Crea una Specification que filtra préstamos por el id del cliente.
     *
     * @param clientId El id del cliente.
     * @return Specification para filtrar por client.id.
     */
    public static Specification<Loan> clientId(Long clientId) {
        return (root, query, builder) -> builder.equal(root.get("client").get("id"), clientId);
    }

    /**
     * Crea una Specification que filtra los préstamos activos en una fecha
     * determinada.
     * Retorna verdadero si la fecha se encuentra entre rentalDate y returnDate.
     *
     * @param loanDate fecha a filtrar.
     * @return Specification que filtra por fecha activa.
     */
    public static Specification<Loan> activeOnDate(Date loanDate) {
        return (root, query, builder) -> builder.and(
                builder.lessThanOrEqualTo(root.get("rentalDate"), loanDate),
                builder.greaterThanOrEqualTo(root.get("returnDate"), loanDate));
    }
}