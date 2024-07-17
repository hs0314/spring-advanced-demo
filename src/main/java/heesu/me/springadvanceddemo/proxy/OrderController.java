package heesu.me.springadvanceddemo.proxy;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/proxy")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/request")
    public String request(@RequestParam("itemId") String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/no-log")
    public String noLog() {
        return "ok";
    }
}
