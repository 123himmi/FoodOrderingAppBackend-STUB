package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository

public class ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    public ItemEntity getItem(String itemId) {
        return entityManager.createQuery("getItem", ItemEntity.class).getSingleResult();
    }

    public List<ItemEntity> getItemsByRestaurant(String restaurantId) {
            List<ItemEntity> itemRawEntities = entityManager.
                    createQuery("SELECT i from ItemEntity i JOIN FETCH i.itemsInRestaurant ir WHERE ir.uuid = :restaurantId", ItemEntity.class)
                    .setParameter("restaurantId", restaurantId).getResultList();
            return itemRawEntities;
    }

    public List<String> getItemsIdsByPopularity(String restaurantId) {

        return entityManager.createNativeQuery("select i.uuid from item i, restaurant r, orders o, order_item oi, restaurant_item ri " +
                " where r.id = o.restaurant_id " +
                " and o.id = oi.order_id " +
                " and oi.item_id = i.id " +
                " and ri.restaurant_id = r.id " +
                " and ri.item_id = oi.item_id " +
                " and r.uuid = :restaurantId " +
                " group by i.uuid order by count(*) desc " +
                " limit 5").setParameter("restaurantId", restaurantId).getResultList();
    }
}

