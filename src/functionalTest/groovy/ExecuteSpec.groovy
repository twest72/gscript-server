import geb.spock.GebSpec

class ExecuteSpec extends GebSpec {
    void "main page title should be 'Gaelyk'"() {
        when:
        go 'execute/%7B%22script%22:%22println%20test%22%7D'

        then:
        title == ''
    }
}
