package com.curso.gateway.server.filters.factory;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ExampleGatewayFilterFactory
		extends AbstractGatewayFilterFactory<ExampleGatewayFilterFactory.Configuration> {

	private final Logger logger = LoggerFactory.getLogger(ExampleGatewayFilterFactory.class);

	public ExampleGatewayFilterFactory() {
		super(Configuration.class);
	}

	@Override
	public GatewayFilter apply(Configuration config) {
		return (exchange, chain) -> {
			logger.info("Ejecutando pre Gateway Filter Factory: " + config.mensaje);

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				logger.info("Ejecutando post Gateway Filter Factory: " + config.mensaje);
				Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
					exchange.getResponse()
							.addCookie(ResponseCookie.from(config.cookieNombre, config.cookieValor).build());
				});

			}));
		};
	}

	public class Configuration {

		private String mensaje;
		private String cookieValor;
		private String cookieNombre;

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}

		public String getCookieValor() {
			return cookieValor;
		}

		public void setCookieValor(String cookieValor) {
			this.cookieValor = cookieValor;
		}

		public String getCookieNombre() {
			return cookieNombre;
		}

		public void setCookieNombre(String cookieNombre) {
			this.cookieNombre = cookieNombre;
		}

	}

}
