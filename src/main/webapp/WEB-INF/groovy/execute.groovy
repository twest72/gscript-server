import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

log.info 'execute script'
log.info "execute script params: $params"

String scriptJsonString = params?.script
log.info "execute script scriptJsonString: $scriptJsonString"

Map script = new JsonSlurper().parseText(scriptJsonString)
log.info "execute script: $script"

String sourcecode = script.sourcecode
log.info "execute script sourcecode: ${ sourcecode }"


ByteArrayOutputStream scriptOutputStream = new ByteArrayOutputStream()

Binding binding = new Binding()
binding.setProperty 'out', new PrintStream(scriptOutputStream)
binding.setProperty 'err', new PrintStream(scriptOutputStream)

GroovyShell shell = new GroovyShell(binding)

Map result = [:]
try {
    def returnValue = shell.evaluate(sourcecode)

    if (returnValue) {
        result.return_value = returnValue.toString()
    }
} catch (Exception e) {


    StringWriter stackTrace = new StringWriter()
    e.printStackTrace(new PrintWriter(stackTrace))

    result.error = [:]
    result.error.stacktrace = stackTrace.toString()
    result.error.message = e.message
}


String scriptOutput = scriptOutputStream.toString('UTF-8')
if (!scriptOutput.isEmpty()) {
    result.output = scriptOutput
}

log.info "execute script result: ${ result }"
log.info "execute script result: ${ result }"

String jsonResult = JsonOutput.toJson result

log.info "execute script returns: ${ jsonResult }"

response.contentType = "application/json"
out << jsonResult

log.info 'execute script end'
