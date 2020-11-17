package qtx.dominio;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Manada {
	private String nombre;
	private List<Perro> perros;
	
	public Manada(String nombre) {
		super();
		this.nombre = nombre;
		this.perros = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void agregarPerro(Perro perro) {
		this.perros.add(perro);
	}
	
	public JsonObject toJsonObject() {
		JsonObjectBuilder builderManada = Json.createObjectBuilder();
		JsonArrayBuilder builderPerros = Json.createArrayBuilder();
		for(Perro perroI:this.perros) {
			builderPerros.add(perroI.toJsonObject());
		}
		return builderManada.add("nombre", this.nombre)
					 .add("perros", builderPerros)
					 .build();
	}
	public String toJson() {
		return this.toJsonObject().toString();
	}
}
