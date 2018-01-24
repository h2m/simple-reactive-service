# Simple Rector based Web Service

This service demonstrates a reactive web service retrieving raw weather data from `http://api.met.no/weatherapi`.

Running this service under MacOS and putting it under some load results in exceptions that do not occur under Linux.

## How to run it locally

    $ gradle bootRun

## How to run it in Cloudfoundry

    $ gradle build
    $ cf push
    
This uses the manifest.yml to deploy

    ---
    applications:
     - name: simple-reactive-service
       random-route: true
       path: build/libs/simple-reactive-service-0.0.1.jar
       memory: 256M
       instances: 1

## Generate Load
    
To generate some load, you might use [loadtest](https://www.npmjs.com/package/loadtest), on IBM Cloud, the endpoint looks like this: https://simple-reactive-service-paragraphistical-bori.eu-gb.mybluemix.net/weather?lat=53.551086&lon=9.993682

    $ loadtest -n 100 -c 10 "http://localhost:8080/weather?lat=53.551086&lon=9.993682"
    
    [Mon Jan 22 2018 16:47:51 GMT+0100 (CET)] INFO Requests: 0 (0%), requests per second: 0, mean latency: 0 ms
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO Target URL:          http://localhost:8080/weather?lat=53.551086&lon=9.993682
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO Max requests:        100
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO Concurrency level:   10
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO Agent:               none
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO Completed requests:  100
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO Total errors:        2
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO Total time:          2.2917826999999997 s
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO Requests per second: 44
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO Mean latency:        218.8 ms
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO Percentage of the requests served within a certain time
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO   50%      126 ms
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO   90%      805 ms
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO   95%      823 ms
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO   99%      1578 ms
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO  100%      1578 ms (longest request)
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO  100%      1578 ms (longest request)
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO
    [Mon Jan 22 2018 16:47:53 GMT+0100 (CET)] INFO   500:   2 errors    


The following exception do occur on my machine:

    2018-01-22 16:47:53.390  INFO 41319 --- [ctor-http-nio-7] n.e.webservice.reactive.DemoController   : got weather data for location [53.551086, 9.993682] (157 kB)
    2018-01-22 16:47:53.403  INFO 41319 --- [ctor-http-nio-4] n.e.webservice.reactive.DemoController   : got weather data for location [53.551086, 9.993682] (157 kB)
    2018-01-22 16:47:53.404  INFO 41319 --- [ctor-http-nio-6] n.e.webservice.reactive.DemoController   : got weather data for location [53.551086, 9.993682] (157 kB)
    2018-01-22 16:47:53.405  INFO 41319 --- [ctor-http-nio-2] n.e.webservice.reactive.DemoController   : got weather data for location [53.551086, 9.993682] (157 kB)
    2018-01-22 16:47:53.406  INFO 41319 --- [ctor-http-nio-8] n.e.webservice.reactive.DemoController   : got weather data for location [53.551086, 9.993682] (157 kB)
    2018-01-22 16:47:53.408  INFO 41319 --- [ctor-http-nio-3] n.e.webservice.reactive.DemoController   : got weather data for location [53.551086, 9.993682] (157 kB)
    2018-01-22 16:47:53.409 ERROR 41319 --- [ctor-http-nio-8] n.e.w.reactive.WeatherDataService        : Status code is null
    2018-01-22 16:47:53.411  INFO 41319 --- [ctor-http-nio-3] n.e.webservice.reactive.DemoController   : got weather data for location [53.551086, 9.993682] (157 kB)
    2018-01-22 16:47:53.411 ERROR 41319 --- [ctor-http-nio-8] .a.w.r.e.DefaultErrorWebExceptionHandler : Failed to handle request [GET http://localhost:8080/weather?lat=53.551086&lon=9.993682]
    
    java.lang.NullPointerException: null
            at org.springframework.http.client.reactive.ReactorClientHttpResponse.getHeaders(ReactorClientHttpResponse.java:65) ~[spring-web-5.0.2.RELEASE.jar:5.0.2.RELEASE]
            at org.springframework.web.reactive.function.BodyExtractors.contentType(BodyExtractors.java:279) ~[spring-webflux-5.0.2.RELEASE.jar:5.0.2.RELEASE]
            at org.springframework.web.reactive.function.BodyExtractors.readWithMessageReaders(BodyExtractors.java:250) ~[spring-webflux-5.0.2.RELEASE.jar:5.0.2.RELEASE]
            at org.springframework.web.reactive.function.BodyExtractors.lambda$toMono$2(BodyExtractors.java:96) ~[spring-webflux-5.0.2.RELEASE.jar:5.0.2.RELEASE]
            at org.springframework.web.reactive.function.client.DefaultClientResponse.body(DefaultClientResponse.java:82) ~[spring-webflux-5.0.2.RELEASE.jar:5.0.2.RELEASE]
            at org.springframework.web.reactive.function.client.DefaultClientResponse.bodyToMono(DefaultClientResponse.java:106) ~[spring-webflux-5.0.2.RELEASE.jar:5.0.2.RELEASE]
            at org.springframework.web.reactive.function.client.ClientResponse$bodyToMono$3.call(Unknown Source) ~[na:na]
            at net.example.webservice.reactive.WeatherDataService$_retrieveWeatherData_closure1.doCall(WeatherDataService.groovy:31) ~[main/:na]
            at sun.reflect.GeneratedMethodAccessor22.invoke(Unknown Source) ~[na:na]
            at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_152]
            at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_152]
            at org.codehaus.groovy.reflection.CachedMethod.invoke(CachedMethod.java:98) ~[groovy-2.5.0-beta-2.jar:2.5.0-beta-2]
            at groovy.lang.MetaMethod.doMethodInvoke(MetaMethod.java:325) ~[groovy-2.5.0-beta-2.jar:2.5.0-beta-2]
            at org.codehaus.groovy.runtime.metaclass.ClosureMetaClass.invokeMethod(ClosureMetaClass.java:294) ~[groovy-2.5.0-beta-2.jar:2.5.0-beta-2]
            at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1033) ~[groovy-2.5.0-beta-2.jar:2.5.0-beta-2]
            at groovy.lang.Closure.call(Closure.java:415) ~[groovy-2.5.0-beta-2.jar:2.5.0-beta-2]
            at org.codehaus.groovy.runtime.ConvertedClosure.invokeCustom(ConvertedClosure.java:54) ~[groovy-2.5.0-beta-2.jar:2.5.0-beta-2]
            at org.codehaus.groovy.runtime.ConversionHandler.invoke(ConversionHandler.java:124) ~[groovy-2.5.0-beta-2.jar:2.5.0-beta-2]
            at com.sun.proxy.$Proxy69.apply(Unknown Source) ~[na:na]
            at reactor.core.publisher.MonoFlatMap$FlatMapMain.onNext(MonoFlatMap.java:118) ~[reactor-core-3.1.2.RELEASE.jar:3.1.2.RELEASE]
            at reactor.core.publisher.FluxSwitchIfEmpty$SwitchIfEmptySubscriber.onNext(FluxSwitchIfEmpty.java:67) ~[reactor-core-3.1.2.RELEASE.jar:3.1.2.RELEASE]
            at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108) ~[reactor-core-3.1.2.RELEASE.jar:3.1.2.RELEASE]
            at reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:185) ~[reactor-core-3.1.2.RELEASE.jar:3.1.2.RELEASE]
            at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108) ~[reactor-core-3.1.2.RELEASE.jar:3.1.2.RELEASE]
            at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108) ~[reactor-core-3.1.2.RELEASE.jar:3.1.2.RELEASE]
            at reactor.core.publisher.FluxRetryPredicate$RetryPredicateSubscriber.onNext(FluxRetryPredicate.java:81) ~[reactor-core-3.1.2.RELEASE.jar:3.1.2.RELEASE]
            at reactor.core.publisher.MonoCreate$DefaultMonoSink.success(MonoCreate.java:139) ~[reactor-core-3.1.2.RELEASE.jar:3.1.2.RELEASE]
            at reactor.ipc.netty.channel.PooledClientContextHandler.fireContextActive(PooledClientContextHandler.java:84) ~[reactor-netty-0.7.2.RELEASE.jar:0.7.2.RELEASE]
            at reactor.ipc.netty.channel.FluxReceive.drainReceiver(FluxReceive.java:176) ~[reactor-netty-0.7.2.RELEASE.jar:0.7.2.RELEASE]
            at reactor.ipc.netty.channel.FluxReceive.onInboundComplete(FluxReceive.java:342) ~[reactor-netty-0.7.2.RELEASE.jar:0.7.2.RELEASE]
            at reactor.ipc.netty.channel.ChannelOperations.onHandlerTerminate(ChannelOperations.java:420) ~[reactor-netty-0.7.2.RELEASE.jar:0.7.2.RELEASE]
            at reactor.ipc.netty.channel.ChannelOperationsHandler.channelInactive(ChannelOperationsHandler.java:108) ~[reactor-netty-0.7.2.RELEASE.jar:0.7.2.RELEASE]
            at io.netty.channel.AbstractChannelHandlerContext.invokeChannelInactive(AbstractChannelHandlerContext.java:245) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.AbstractChannelHandlerContext.invokeChannelInactive(AbstractChannelHandlerContext.java:231) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.AbstractChannelHandlerContext.fireChannelInactive(AbstractChannelHandlerContext.java:224) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.ChannelInboundHandlerAdapter.channelInactive(ChannelInboundHandlerAdapter.java:75) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.handler.codec.http.HttpContentDecoder.channelInactive(HttpContentDecoder.java:205) ~[netty-codec-http-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.AbstractChannelHandlerContext.invokeChannelInactive(AbstractChannelHandlerContext.java:245) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.AbstractChannelHandlerContext.invokeChannelInactive(AbstractChannelHandlerContext.java:231) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.AbstractChannelHandlerContext.fireChannelInactive(AbstractChannelHandlerContext.java:224) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.CombinedChannelDuplexHandler$DelegatingChannelHandlerContext.fireChannelInactive(CombinedChannelDuplexHandler.java:420) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.handler.codec.ByteToMessageDecoder.channelInputClosed(ByteToMessageDecoder.java:377) ~[netty-codec-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.handler.codec.ByteToMessageDecoder.channelInactive(ByteToMessageDecoder.java:342) ~[netty-codec-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.handler.codec.http.HttpClientCodec$Decoder.channelInactive(HttpClientCodec.java:282) ~[netty-codec-http-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.CombinedChannelDuplexHandler.channelInactive(CombinedChannelDuplexHandler.java:223) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.AbstractChannelHandlerContext.invokeChannelInactive(AbstractChannelHandlerContext.java:245) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.AbstractChannelHandlerContext.invokeChannelInactive(AbstractChannelHandlerContext.java:231) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.AbstractChannelHandlerContext.fireChannelInactive(AbstractChannelHandlerContext.java:224) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.DefaultChannelPipeline$HeadContext.channelInactive(DefaultChannelPipeline.java:1354) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.AbstractChannelHandlerContext.invokeChannelInactive(AbstractChannelHandlerContext.java:245) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.AbstractChannelHandlerContext.invokeChannelInactive(AbstractChannelHandlerContext.java:231) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.DefaultChannelPipeline.fireChannelInactive(DefaultChannelPipeline.java:917) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.AbstractChannel$AbstractUnsafe$8.run(AbstractChannel.java:822) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:163) ~[netty-common-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:403) ~[netty-common-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:463) ~[netty-transport-4.1.17.Final.jar:4.1.17.Final]
            at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:858) ~[netty-common-4.1.17.Final.jar:4.1.17.Final]
            at java.lang.Thread.run(Thread.java:748) ~[na:1.8.0_152]
    
    2018-01-22 16:47:53.440  INFO 41319 --- [ctor-http-nio-4] n.e.webservice.reactive.DemoController   : got weather data for location [53.551086, 9.993682] (157 kB)
    2018-01-22 16:47:53.469  INFO 41319 --- [ctor-http-nio-7] n.e.webservice.reactive.DemoController   : got weather data for location [53.551086, 9.993682] (157 kB)
    2018-01-22 16:47:53.471  INFO 41319 --- [ctor-http-nio-3] n.e.webservice.reactive.DemoController   : got weather data for location [53.551086, 9.993682] (157 kB)
    2018-01-22 16:47:53.512  INFO 41319 --- [ctor-http-nio-3] n.e.webservice.reactive.DemoController   : got weather data for location [53.551086, 9.993682] (157 kB)
