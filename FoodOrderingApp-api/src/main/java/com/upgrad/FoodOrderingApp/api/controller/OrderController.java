package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping

public class OrderController {

    private Object couponName;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private  CustomerAdminBusinessService customerAdminBusinessService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private RestaurantService restaurantService;


    @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{coupon_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCoupon(@RequestHeader("authorization") final String authorization,
                                                           @PathVariable("coupon_name") final String couponName) throws AuthorizationFailedException, CouponNotFoundException {

        String[] bearerToken = authorization.split( "Bearer ");
        CustomerEntity customerEntity = customerService.getCustomer(bearerToken[1]);

        if(couponName == null || couponName.isEmpty() || couponName.equalsIgnoreCase("\"\"")){
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");

        }

        CouponEntity couponEntity = orderService.getCouponByCouponName(couponName);

        if (couponEntity == null) {
            throw new  CouponNotFoundException("CPF -001", "No coupon by this name");
        }

        CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse().id(UUID.fromString(couponEntity.getUuid()))
                .couponName(couponEntity.getCouponName()).percent(couponEntity.getPercent());

        return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, path = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CustomerOrderResponse> getCustomerOrders(@RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException {

        String[] bearerToken = authorization.split( "Bearer ");

        // Validates the access token retrieved from database
        customerAdminBusinessService.validateAccessToken(bearerToken[1]);

        // Gets the customerAuthToken details from customerDao
        CustomerAuthEntity customerAuthEntity = customerAdminBusinessService.getCustomerAuthToken(bearerToken[1]);

        // Gets the customer details from customerAuthTokenEntity
        CustomerEntity customerEntity = customerAuthEntity.getCustomer();

        // Gets all the past orders of the customer
        final List<OrderEntity> orderEntityList = orderService.getOrdersByCustomers(customerAuthEntity.getUuid().toString());

        CustomerOrderResponse customerOrderResponse = new CustomerOrderResponse();

        List<OrderList> orderDetailsList = new ArrayList<OrderList>();

        for (OrderEntity orderEntity : orderEntityList) {

            OrderListCustomer orderListCustomer = new OrderListCustomer();
            orderListCustomer.setId(UUID.fromString(orderEntity.getCustomer().getUuid()));
            orderListCustomer.setFirstName(orderEntity.getCustomer().getFirstName());
            orderListCustomer.setLastName(orderEntity.getCustomer().getLastName());
            orderListCustomer.setContactNumber(orderEntity.getCustomer().getContactNumber());
            orderListCustomer.setEmailAddress(orderEntity.getCustomer().getEmail());

            OrderListAddressState orderListAddressState = new OrderListAddressState();
            orderListAddressState.setId(UUID.fromString(orderEntity.getAddress().getState().getUuid()));
            orderListAddressState.setStateName(orderEntity.getAddress().getState().getStateName());

            OrderListAddress orderListAddress = new OrderListAddress();
            orderListAddress.setId(UUID.fromString(orderEntity.getAddress().getUuid()));
            orderListAddress.setFlatBuildingName(orderEntity.getAddress().getFlatBuildingNumber());
            orderListAddress.setLocality(orderEntity.getAddress().getLocality());
            orderListAddress.setCity(orderEntity.getAddress().getCity());
            orderListAddress.setPincode(orderEntity.getAddress().getPincode());
            orderListAddress.setState(orderListAddressState);

            OrderListCoupon orderListCoupon = new OrderListCoupon();
            orderListCoupon.setId(UUID.fromString(orderEntity.getCoupon().getUuid()));
            orderListCoupon.setCouponName(orderEntity.getCoupon().getCouponName());
            orderListCoupon.setPercent(orderEntity.getCoupon().getPercent());

            OrderListPayment orderListPayment = new OrderListPayment();
            orderListPayment.setId(UUID.fromString(orderEntity.getUuid()));
            orderListPayment.setPaymentName(orderEntity.getPayment().getPaymentName());

            OrderList orderList = new OrderList();
            orderList.setId(UUID.fromString(orderEntity.getUuid()));
            orderList.setDate(orderEntity.getDate().toString());
            orderList.setAddress(orderListAddress);
            orderList.setCustomer(orderListCustomer);
            orderList.setPayment(orderListPayment);
            orderList.setCoupon(orderListCoupon);
            orderList.setBill(BigDecimal.valueOf(orderEntity.getBill()));
            orderList.setDiscount(BigDecimal.valueOf(orderEntity.getDiscount()));

            for (OrderItemEntity orderItemEntity : itemService.getItemsByOrder(orderEntity)) {

                ItemQuantityResponseItem itemQuantityResponseItem = new ItemQuantityResponseItem();
                itemQuantityResponseItem.setId(UUID.fromString(orderItemEntity.getItem().getUuid()));
                itemQuantityResponseItem.setItemName(orderItemEntity.getItem().getItemName());
                itemQuantityResponseItem.setItemPrice(orderItemEntity.getItem().getPrice());
                itemQuantityResponseItem.setType(ItemQuantityResponseItem.TypeEnum.valueOf(orderItemEntity.getItem().getType().toString()));

                ItemQuantityResponse itemQuantityResponse = new ItemQuantityResponse();
                itemQuantityResponse.setItem(itemQuantityResponseItem);
                itemQuantityResponse.setPrice(orderItemEntity.getPrice());
                itemQuantityResponse.setQuantity(orderItemEntity.getQuantity());

                orderList.addItemQuantitiesItem(itemQuantityResponse);
            }

            customerOrderResponse.addOrdersItem(orderList);

        }

        return new ResponseEntity<CustomerOrderResponse>(customerOrderResponse, HttpStatus.OK);

    }

   @RequestMapping(method = RequestMethod.POST, path = "/order",  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE ,produces =MediaType.APPLICATION_JSON_UTF8_VALUE )
	public ResponseEntity<SaveOrderResponse> saveOrders(@RequestHeader("authorization") final String authorization,
														final SaveOrderRequest saveOrderRequest) throws AuthorizationFailedException, PaymentMethodNotFoundException, RestaurantNotFoundException, AddressNotFoundException {

        String[] bearerToken = authorization.split( "Bearer ");

        // Validates the access token retrieved from database
//        customerAdminBusinessService.validateAccessToken(bearerToken[1]);

        // Gets the customerAuthToken details from customerDao
        CustomerAuthEntity customerAuthEntity = customerAdminBusinessService.getCustomerAuthToken(bearerToken[1]);

        final OrderEntity orderEntity = new OrderEntity();

        //Calls orderService getCouponByCouponId method to get the CouponEntity by it uuid.
        CouponEntity couponEntity = orderService.getCouponByCouponId(saveOrderRequest.getCouponId().toString());

        //Calls addressService getAddressByUUID method to get the AddressEntity by it uuid.
       CustomerEntity customerEntity = customerService.getCustomer(bearerToken[1]);
        AddressEntity addressEntity = addressService.getAddressByUUID(saveOrderRequest.getAddressId(),customerEntity);

        //Calls paymentService getPaymentByUUID method to get the PaymentEntity by it uuid.
        PaymentEntity paymentEntity = paymentService.getPaymentByUUID(saveOrderRequest.getPaymentId().toString());

        //Calls restaurantService restaurantByUUID method to get the RestaurantEntity by it uuid.
        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(saveOrderRequest.getRestaurantId().toString());


        // Adds all the attributes provided to the order entity
        orderEntity.setUuid(UUID.randomUUID().toString());
        orderEntity.setBill(saveOrderRequest.getBill().floatValue());
        orderEntity.setCoupon(couponEntity);
        orderEntity.setAddress(addressEntity);
        orderEntity.setDiscount(saveOrderRequest.getDiscount().doubleValue());
        orderEntity.setRestaurant(restaurantEntity);
        orderEntity.setPayment(paymentEntity);
        ;

       List<ItemQuantity> itemQuantities= saveOrderRequest.getItemQuantities();

		final OrderEntity createdordersEntity = orderService.saveOrder(orderEntity);

       for (ItemQuantity itemQuantity : itemQuantities) {

           OrderItemEntity orderItemEntity = new OrderItemEntity();

           ItemEntity itemEntity = itemService.getItem(itemQuantity.getItemId().toString());

           orderItemEntity.setItem(itemEntity);
           orderItemEntity.setOrders(orderEntity);
           orderItemEntity.setPrice(itemQuantity.getPrice());
           orderItemEntity.setQuantity(itemQuantity.getQuantity());

           OrderItemEntity savedOrderItem = orderService.saveOrderItem(orderItemEntity);

       }


       // Loads the SaveOrderResponse with the uuid of the new order created and the respective status message
		SaveOrderResponse saveOrderResponse = new SaveOrderResponse().id(createdordersEntity.getUuid())
				.status("ORDER SUCCESSFULLY PLACED");

        return new ResponseEntity<SaveOrderResponse>(saveOrderResponse,HttpStatus.CREATED);
     }
}
