package home.vertx.contact;

import io.advantageous.qbit.annotation.PathVariable;
import io.advantageous.qbit.annotation.RequestMapping;
import io.advantageous.qbit.annotation.RequestMethod;
import io.advantageous.qbit.http.request.HttpBinaryResponse;
import io.advantageous.qbit.reactive.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequestMapping("/contacts")
@Component
public class ContactEndpoint {

    @Autowired
    private ContactRepository repository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, contentType = "application/json")
    public void getResult(Callback<HttpBinaryResponse> callback, @PathVariable("id") Long id){
        repository.getContactById(callback, id);
    }
}
