package br.com.caelum.camel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import br.com.caelum.livraria.modelo.Livro;

public class TestePooling {
	
	public static void main(String[] args) throws Exception {
		DefaultCamelContext ctx = new DefaultCamelContext();
		ctx.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("http://localhost:8088/fj36-livraria/loja/livros/mais-vendidos")
				.delay(1000)
				.unmarshal()
				.json()
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) {
						List<?> listaDeLivros = (List<?>) exchange.getIn().getBody();
						ArrayList<Livro> livros = (ArrayList<Livro>) listaDeLivros.get(0);
						Message message = exchange.getIn();
						message.setBody(livros);
					}
				})
				.log("${body}")
				.to("mock:livros");
			}			
		});
		ctx.start();
		new Scanner(System.in).nextLine();
		ctx.stop();
	}

}
