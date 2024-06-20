package heesu.me.springadvanceddemo.v2;

import heesu.me.springadvanceddemo.trace.TraceId;
import heesu.me.springadvanceddemo.trace.TraceStatus;
import heesu.me.springadvanceddemo.trace.hellotrace.HelloTraceV1;
import heesu.me.springadvanceddemo.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final이 붙어있거나 @NonNull이 보장된 필드에 대한 생성자 생성
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepositoryV2;
    private final HelloTraceV2 trace;

    public void orderitem(String itemId, TraceId beforeTraceId){

        TraceStatus status = null;

        try {
            status = trace.beginSync(beforeTraceId, "OrderService.orderitem()");
            orderRepositoryV2.save(itemId, status.getTraceId());
            trace.end(status);
        } catch (Exception e){
            trace.exception(status, e);
            throw e;
        }


    }
}
