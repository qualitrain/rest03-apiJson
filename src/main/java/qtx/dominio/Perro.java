package qtx.dominio;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;

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
		JsonObject perroJson = Json.createObjectBuilder()
				                   .add("nombre", this.nombre)
				                   .add("raza", this.raza)
				                   .add("peso", Redondeador.redondear(this.peso, 1))
				                   .build();
		return perroJson;
	}
	
}
