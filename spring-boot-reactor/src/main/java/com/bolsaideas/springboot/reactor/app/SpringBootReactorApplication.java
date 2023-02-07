package com.bolsaideas.springboot.reactor.app;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsaideas.springboot.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<String> usuariosList = new ArrayList<>();
		usuariosList.add("Andres Moreno");
		usuariosList.add("Maria Gomez");
		usuariosList.add("Sandra Rodriguez");
		usuariosList.add("Lucia Torres");
		usuariosList.add("Bruce Lee");
		usuariosList.add("Bruce Willis");
		
		Flux<String> nombresList= Flux.fromIterable(usuariosList);
		
		//Flux es un Publisher es decir un Observable y equivale a una lista si es Mono equivale a un unico objeto
		Flux<String> nombres = Flux.just("Andres Moreno", "Maria Gomez", "Sandra Rodriguez", "Lucia Torres", "Bruce Lee", "Bruce Willis");
				//si no se colocan las llaves con el return se infiere que retorna en este caso string
		Flux<Usuario> usuarios=		nombresList.map(nombre -> nombre.toLowerCase())
				//Se convierte todo a usuario
				.map(nombre -> new Usuario(nombre.split(" ")[0].toLowerCase(), nombre.split(" ")[1].toLowerCase()))
				//Metodo para filtrar, toma el usuario que se obtiene en el paso anterior y retorna un boolean
				.filter(usuario -> {return usuario.getNombre().toLowerCase().equals("bruce");})
				//Metodo que se va a ejecutar cada vez que se añada un elemento al observable, si no se hubiera convertido a usuario seria un string
				.doOnNext(elemento -> {
					if(elemento==null) {
						throw new RuntimeException("No puede ser vacio");
					}
					else {
						System.out.println(elemento.getNombre());
					}
				//teniendo en cuenta que el flux es inmutable, un map puede hacer cambios sobre los datos retornando siempre un nuevo valor
				}).map(nombre -> {
				
							nombre.setNombre(nombre.getNombre().toUpperCase());
							return nombre;
				});
				
						
				
		//en este caso el suscribe se ejecuta a la par de la funcion doOnNext y con la ultima transformaciòn hecha sobre los datos
		usuarios.subscribe(usuario -> log.info(usuario.getNombre()),
				error-> log.error(error.getMessage()),
				new Runnable() {
					
					@Override
					public void run() {
						log.info("ha terminado la ejecuciòn del flujo");
						
					}
				});
		 
	}

}
