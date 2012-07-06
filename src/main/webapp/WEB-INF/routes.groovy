
get "/", forward: "/WEB-INF/pages/index.gtpl"
get "/datetime", forward: "/datetime.groovy"

get "/favicon.ico", redirect: "/images/gaelyk-small-favicon.png"

get "/execute", forward: "/WEB-INF/groovy/execute.groovy"
