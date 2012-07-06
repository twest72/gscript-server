import org.junit.Test
import groovyx.net.http.RESTClient
import groovy.json.JsonBuilder

/**
 * Created with IntelliJ IDEA.
 * User: westphal
 * Date: 14.06.12
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */
class ExcecuteIntegrationTest {

    //private static final String GAE_URL = 'http://gscript-server.appspot.com'
    private static final String GAE_URL = 'http://localhost:8080/execute'

    @Test
    void testExecute() {

        String scriptSourcecode = """
println 'hello from println server'
def returnValue = 4 + 7
return "hello from server \$returnValue"
        """

        JsonBuilder builder = new JsonBuilder()
        builder {
            'sourcecode' scriptSourcecode
        }
        def script =  builder.toString()


        RESTClient gscriptServer = new RESTClient(GAE_URL)

        def response = gscriptServer.get ( query : [script: script] )

        assert response.status == 200
        assert response.contentType == 'application/json'

        println response.data
    }


    @Test
    void testExecuteError() {

        String scriptSourcecode = """
println 'hello from println server'
def returnValue = 4 + 7
return "hello from server \$returnValueEEEE"
        """

        JsonBuilder builder = new JsonBuilder()
        builder {
            'sourcecode' scriptSourcecode
        }
        def script =  builder.toString()


        RESTClient gscriptServer = new RESTClient(GAE_URL)

        def response = gscriptServer.get ( path : 'execute', query : [script: script] )

        assert response.status == 200
        assert response.contentType == 'application/json'

        println response.data
    }
}
