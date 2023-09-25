//package org.acme.kafka.worker;
//
//import io.quarkus.scheduler.Scheduled;
//import io.quarkus.scheduler.Scheduler;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.transaction.Transactional;
//import org.acme.kafka.entity.Order;
//import org.acme.kafka.repository.OrderRepository;
//import org.acme.kafka.service.OrderService;
//
//import java.util.List;
//
//@ApplicationScoped
//public class OrderMatchingJob {
//
//    @Inject
//    OrderRepository orderRepository;
//    @Inject
//    OrderService orderService;
//
//    @Inject
//    Scheduler scheduler;
//
//    // Cấu hình để chạy công việc mỗi X giây (tuỳ chọn)
//    @Scheduled(every = "5s")
//    @Transactional
//    public void matchOrders() {
//        try {
//            List<Order> orderOpens = orderRepository.findListOrderOpen();
//            for (Order openOrder : orderOpens) {
//              orderService.matchOrder(openOrder);
//            }
//        } catch (Exception e) {
//            // Xử lý lỗi nếu cần
//        }
//    }
//}
