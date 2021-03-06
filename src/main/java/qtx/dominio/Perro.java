package qtx.dominio;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;

import qtx.util.Redondeador;

public class Perro {
	private String nombre;
	private String raza;
	private float peso;
	
	
	public Perro() {
	}

	public Perro(String strPerroJson) {
		JsonReader reader = Json.createReader(new StringReader(strPerroJson));
		JsonObject perroJson = reader.readObject();
		
		JsonString jstrCadena = (JsonString) perroJson.get("nombre");
		this.nombre = jstrCadena.getString();
		
		jstrCadena = (JsonString) perroJson.get("raza");
		this.raza = jstrCadena.getString();
		
		JsonNumber jsNum = (JsonNumber) perroJson.get("peso");
		this.peso = (float) jsNum.doubleValue();
		
	}
	public Perro(String nombre, String raza, float peso) {
		this.nombre = nombre;
		this.raza = raza;
		this.peso = peso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Perro [nombre=" + nombre + ", raza=" + raza + ", peso=" + peso + "]";
	}
	
	public String toJson() {
		return this.toJsonObject().toString();
	}
	public JsonObject toJsonObject() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("nombre", this.nombre)
			   .add("raza", this.raza)
			   .add("peso", Redondeador.redondear(this.peso, 1));
		JsonObject objJson = builder.build();
		return objJson;
	}
	
}
