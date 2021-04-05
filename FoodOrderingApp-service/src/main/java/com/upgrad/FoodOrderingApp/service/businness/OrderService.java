package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CustomerAdminBusinessService customerAdminBusinessService;

    @Transactional
    public CouponEntity getCouponByCouponName(String couponName ) {
        return orderDao.getCouponByName(couponName);
    }

    @Transactional
    public List<OrderEntity> getOrdersByCustomers(String uuid) {

        return orderDao.getOrdersByUUID(uuid);
    }

    @Transactional
    public OrderEntity saveOrder(OrderEntity orderEntity ) {
        return orderDao.saveOrder(orderEntity);
    }

    @Transactional
    public CouponEntity getCouponByCouponId(String couponName ) {
        return couponDao.getCouponByCouponId(couponName);
    }


    @Transactional
    public OrderItemEntity saveOrderItem(OrderItemEntity orderItemEntity ) {
        return orderDao.saveOrder(orderItemEntity);
    }

}
