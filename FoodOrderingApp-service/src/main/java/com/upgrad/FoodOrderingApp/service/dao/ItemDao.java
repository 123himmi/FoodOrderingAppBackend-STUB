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

    public List<String> getTopNItemsForRestaurant(String restaurantId) {
        /*return entityManager.createNativeQuery("select i.uuid from item i, restaurant r, orders o, order_item oi, restaurant_item ri\\n\" +\n" +
                "                \"where r.id = o.restaurant_id\\n\" +\n" +
                "                \"  and o.id = oi.order_id\\n\" +\n" +
                "                \"  and oi.item_id = i.id\\n\" +\n" +
                "                \"  and ri.restaurant_id = r.id\\n\" +\n" +
                "                \"  and ri.item_id = oi.item_id\\n\" +\n" +
                "                \"  and r.uuid = :restaurantId\\n\" +\n" +
                "                \"  group by i.uuid order by total_count desc\\n\" +\n" +
                "                \"  limit 5").setParameter("restaurantId", restaurantId).getResultList();*/
        return null;
    }

    public ItemEntity getItem(String itemId) {
        return entityManager.createQuery("getItem", ItemEntity.class).getSingleResult();
    }
}
