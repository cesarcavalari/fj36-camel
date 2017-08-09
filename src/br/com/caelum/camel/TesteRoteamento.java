package br.com.caelum.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TesteRoteamento {
	
	public static void main(String[] args) throws Exception {
		DefaultCamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:entrada?delay=5s")
				.log(LoggingLevel.INFO, "Processando mensagem ${id}")
				.to("file:saída");
			}
		});
		context.start();
		Thread.sleep(30 * 1000);
		context.stop();
	}

}
