# twyn-tools!
A collections of helper tools for twyn!

## TwynRestTemplateFactory
Creates a spring RestTemplate with twyn under-the-hood
```java
Twyn twyn = Twyn.forTest();
RestTemplate restTemplate = TwynRestTemplateFactory.create(twyn);
```

## Todo
* Allow for custom HttpMessageConverter:s in TwynRestTemplateFactory