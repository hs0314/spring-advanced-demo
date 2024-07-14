package heesu.me.springadvanceddemo.trace.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final이 붙어있거나 @NonNull이 보장된 필드에 대한 생성자 생성
public class OrderServiceV0 {

    private final OrderRepositoryV0 orderRepositoryV0;

    public void orderitem(String itemId){
        orderRepositoryV0.save(itemId);
    }
}
