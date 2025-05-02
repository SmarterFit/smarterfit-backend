package com.smarterfit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/// TODO: Evitar uso de contadores
/// TODO: Evitar que um service chame outro service: utilizar eventos.
/// 	/// TODO: Separar serviceHandler do service normal. 
/// TODO: Criar testes de requisições no Hoppscotch
/// TODO: Criar checkin das turmas
/// TODO: Links checkins com pontuação do training group: Pedro Lucas
/// /// TODO: A pontuação possui beneficios baseados na quantidade de dias em sequência.
/// TODO: Registro de horários dos profissionais e possibilidade de agendamento individual.
/// /// TODO: Registro da presença no agendamento é necessário.
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class SmarterFitApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(SmarterFitApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("SmarterFit API is running");
		System.out.println("Acesse: http://localhost:8081/");
	}
}
