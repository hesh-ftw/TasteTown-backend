package com.chamath.TasteTown.Controller;
import com.chamath.TasteTown.Model.Order;
import com.chamath.TasteTown.Model.User;
import com.chamath.TasteTown.Request.OrderRequest;
import com.chamath.TasteTown.Response.PaymentResponse;
import com.chamath.TasteTown.Service.OrderService;
import com.chamath.TasteTown.Service.PaymentService;
import com.chamath.TasteTown.Service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;


    //endpoints for users
    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createOrder(@RequestHeader("Authorization") String jwt,
                                             @RequestBody OrderRequest request) throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        Order order= orderService.createOrder(request,user);

        //after placing order, create a link to make payments and return it
        PaymentResponse res= paymentService.createPaymentLink(order);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        List<Order> orders= orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }


    //endpoints for admins(restaurants owners)

    @GetMapping("/admin/restaurant/{id}")
    public ResponseEntity<List<Order>> getRestaurantOrderHistory(@PathVariable Long id,
                                                                 @RequestParam(required = false) String order_status,
                                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        List<Order> orders= orderService.getRestaurantsOrder(id,order_status);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }


    @PutMapping("/admin/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                         @PathVariable String orderStatus,
                                                         @RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        Order orders= orderService.updateOrder(orderId,orderStatus);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

}
