package reqres.users;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
public class ExamplesOfReturnsTest {

    @Test
    public void getTopTrades(){

        //DONUSTURMELERI AI'DAN AL TAMAMLAAA
        //!!!!!!!!!!!!!!
        // AI;DAN FARKLARINI AL OZELLIKLE List<Map> data ve List<Map<String,Object>> data nin

        //1-
        List<Map<String, Object>> data1 = get("http://localhost:9000/api/tops/last")
                .body().jsonPath().getList("$");
        //2-
        List<Map<String, Object>> data2 = get("http://localhost:9000/api/tops/last")
                .body().jsonPath().get("$");
        System.out.println(data2.get(0));
        Object symbol = data2.get(0).get("symbol");
        String name = data2.get(0).get("symbol").toString();
        //or
        String name2 = symbol.toString();
        //or
        String name3 = (String) symbol;
        //3-
        List<Map> data3 = get("http://localhost:9000/api/tops/last")
                .body().jsonPath().getList("$", Map.class);
        System.out.println(data3.get(0));
        data3.get(0).get("symbol");

    }
}
