package heesu.me.springadvanceddemo.trace.v1;

import heesu.me.springadvanceddemo.trace.TraceStatus;
import heesu.me.springadvanceddemo.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final이 붙어있거나 @NonNull이 보장된 필드에 대한 생성자 생성
public class OrderServiceV1 {

    private final OrderRepositoryV1 orderRepositoryV1;
    private final HelloTraceV1 trace;

    public void orderitem(String itemId){

        TraceStatus status = null;

        try {
            status = trace.begin("OrderService.orderitem()");
            orderRepositoryV1.save(itemId);
            trace.end(status);
        } catch (Exception e){
            trace.exception(status, e);
            throw e;
        }


    }
}
