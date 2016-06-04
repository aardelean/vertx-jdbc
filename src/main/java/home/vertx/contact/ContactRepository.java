package home.vertx.contact;

import io.advantageous.qbit.http.request.HttpBinaryResponse;
import io.advantageous.qbit.http.request.HttpResponseBuilder;
import io.advantageous.qbit.reactive.Callback;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ContactRepository {
    private static Logger logger = LoggerFactory.getLogger(ContactRepository.class);
    @Autowired
    private JDBCClient client;

    public void getContactById(Callback<HttpBinaryResponse> callback, Long id){
        client.getConnection(res -> {
            if (res.succeeded()) {

                SQLConnection connection = res.result();

                connection.query("SELECT * FROM contact WHERE id=" + id, res2 -> {
                    if (res2.succeeded()) {
                        ResultSet rs = res2.result();
                        callback.resolve(HttpResponseBuilder.httpResponseBuilder()
                                .setBody(rs.getResults()).setContentType("application/json")
                                .setCode(200)
                                .buildBinaryResponse());
                    }
                });
            } else {
                logger.error("error fetching the contact");
            }
        });
    }
}
