Retrofit
========
Added Request object to RetrofitError so I can get full visibility in my retrofit exceptions.

```java
class ExampleErrorHandler implements ErrorHandler {
    @Override public Throwable handleError(RetrofitError cause) {
        Response response = cause.getResponse();
        Bugsnag.addToTab("Retrofit", "Cause", cause.toString());
        if(response != null) {
            Bugsnag.addToTab("Retrofit", "Response-Headers", convertHeaders(cause.getResponse().getHeaders()));
            Bugsnag.addToTab("Retrofit", "Response-Status", String.format("%d - %s", cause.getResponse().getStatus(), cause.getResponse().getReason()));
            Bugsnag.addToTab("Retrofit", "Response-Body", responseBodyToString(cause.getResponse().getBody()));
        }
        Request request = cause.getRequest();
        if(request != null) {
            Bugsnag.addToTab("Retrofit", "Request-Method", request.getMethod());
            Bugsnag.addToTab("Retrofit", "Request-Url", request.getUrl());
            Bugsnag.addToTab("Retrofit", "Request-Headers", convertHeaders(request.getHeaders()));
            if(request.getMethod().equals("POST") && request.getBody().getClass().equals(MultipartTypedOutput.class)) {
                Bugsnag.addToTab("Retrofit", "Request-Multipart", request.getBody().fileName());
                MultipartTypedOutput body = (MultipartTypedOutput) request.getBody();
                try {
                    List<byte[]> bodyParts = MultiPartMimeHelper.getParts(body);
                    StringBuilder formParams = new StringBuilder();
                    String binaryCheck = "Content-Transfer-Encoding: binary";
                    for(byte[] bytes : bodyParts) {
                        String s = new String(bytes);
                        if(s.contains(binaryCheck)) {
                            formParams.append(s.split(binaryCheck)[0]).append(binaryCheck);
                        } else {
                            formParams.append(s);
                        }
                    }
                    Bugsnag.addToTab("Retrofit", "Request-Body", formParams.toString());
                } catch (Exception ex) {
                    //wanting to bury this exception.
                    ex.printStackTrace();
                }
            } else {
                Bugsnag.addToTab("Retrofit", "Request-Body", request.getBody());
            }
        } else {
            Bugsnag.addToTab("Retrofit", "Url", cause.getUrl());
        }
        return cause;
    }
}
```

Type-safe REST client for Android and Java by Square, Inc.

For more information please see [the website][1].


Download
--------

Download [the latest JAR][2] or grab via Maven:
```xml
<dependency>
  <groupId>com.squareup.retrofit</groupId>
  <artifactId>retrofit</artifactId>
  <version>1.9.0</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.squareup.retrofit:retrofit:1.9.0'
```

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snap].

Retrofit requires at minimum Java 6 or Android 2.3.



License
=======

    Copyright 2013 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [1]: http://square.github.io/retrofit/
 [2]: https://search.maven.org/remote_content?g=com.squareup.retrofit&a=retrofit&v=LATEST
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
