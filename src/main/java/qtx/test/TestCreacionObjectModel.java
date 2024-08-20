package qtx.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.JsonStructure;
import jakarta.json.JsonValue.ValueType;
import jakarta.json.JsonWriter;

import qtx.dominio.Manada;
import qtx.dominio.Perro;

public class TestCreacionObjectModel {
	private static final String PERRO_JSON = "{"
			+ "\"nombre\":\"Firualis\","
			+ "\"raza\":\"Pastor Taquero\","
			+ "\"peso\":36.5"
			+ "}";
	private static final String PERROS_JSON = "["
			+ "{"
			+ "\"nombre\":\"Firualis\","
			+ "\"raza\":\"Pastor Taquero\","
			+ "\"peso\":36.5"
			+ "},"
			+ "{"
			+ "\"nombre\":\"Fifi\","
			+ "\"raza\":\"French Poodle\","
			+ "\"peso\":15.2"
			+ "}"
			+ "]";

	public static void main(String[] args) {
		testCrearObjetoJson();
		testCrearObjetoJsonAnidado();
		testCrearArregloJson();
		testCrearObjetoJsonComplejo();
		testCrearObjetoJsonComplejo02();
		
		System.out.println("\n============ Cadena en Json: ============");
		System.out.println(PERRO_JSON);
		
		testParseoCadenaJson();
		
		testJsonToJava(PERRO_JSON);
		
		testJavaToJson();
		
		testParseoArchivo();
		testEscribirArchivo();
		
		testJavaToJson_Agregado();
		
		int numAleatorio = (int)( (Math.random() * 100) );
		if(numAleatorio % 2 == 0)
			testParseoCadenaJson(PERRO_JSON);
		else
			testParseoCadenaJson(PERROS_JSON);
	}
	private static void testCrearObjetoJson() {
		System.out.println("\n================= Probando creacion manual de JSon: =================");
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("nombre", "Firulais")
			   .add("raza", "Dalmata")
			   .add("peso", 31.5);
		JsonObject objJson = builder.build();
		System.out.println("JSON:" + objJson.toString());
	}

	private static void testCrearObjetoJsonAnidado() {
		System.out.println("\n======== Probando creacion manual de JSon anidado: ========");
		JsonObjectBuilder builderPerro = Json.createObjectBuilder();
		builderPerro.add("nombre", "Esponja")
			   .add("raza", "French Poodle")
			   .add("peso", 15);
		
		JsonObjectBuilder builderVacuna = Json.createObjectBuilder();
		builderVacuna.add("vacuna","rabia")
				 .add("fecha","2019-02-01");
		
		builderPerro.add("ultVacuna", builderVacuna.build());
		JsonObject objJson = builderPerro.build();
		
		System.out.println("JSON:" + objJson.toString());
	}
	
	private static void testCrearArregloJson() {
		System.out.println("\n====== Probando creacion manual de arreglo JSon: ======");
		JsonArrayBuilder builder = Json.createArrayBuilder();
		builder.add("lunes")
			   .add("martes")
			   .add("miercoles")
			   .add("jueves")
			   .add("viernes");
		JsonArray arregloJson = builder.build();
		System.out.println("JSON:" + arregloJson.toString());
		
		String meses[] = {"enero", "febrero", "marzo", "abril", "mayo", "junio"};
		List<String> listMeses = Arrays.asList(meses);
		
		JsonArrayBuilder builderArr = Json.createArrayBuilder(listMeses);
		JsonArray mesesJson = builderArr.build();
		System.out.println("JSON:" + mesesJson.toString());
		
	}

	private static void testCrearObjetoJsonComplejo() {
		System.out.println("\n====== Probando creacion de objeto JSon complejo: ======");
		JsonObjectBuilder objNombreCompleto = Json.createObjectBuilder();
		objNombreCompleto.add("apPaterno", "Sanchez")
					     .add("apMaterno","Mena")
					     .add("nombres", Json.createArrayBuilder()
					    		             .add("Jorge")
					    		             .add("Alberto"));

		JsonObjectBuilder objBuilder = Json.createObjectBuilder();
		objBuilder.add("nombreCompleto", objNombreCompleto)
		          .add("direccion", Json.createObjectBuilder()
		        		   		        .add("calle", "Av. Insurgentes Sur")
		        		   		        .add("numExt", "3452A")
		        		   		        .add("numInt", "201")
		        		   		        .add("colonia", "San Angel Inn"))
		          .add("telefonos", Json.createArrayBuilder()
		        		                .add(Json.createObjectBuilder()
		        		                		 .add("lugar", "casa")
		        		                		 .add("numero", "55-2134-5432"))
		        		                .add(Json.createObjectBuilder()
		        		                		 .add("lugar", "oficina")
		        		                		 .add("numero", "55-2233-7653")))
		          .add("estudia",true)
		          .add("cuotaMes", 3567.70);
		JsonObject personaJson = objBuilder.build();
		System.out.println("JSON:" + personaJson.toString());
		
	}
	private static void testCrearObjetoJsonComplejo02() {
		System.out.println("\n====== Probando creacion de objeto JSon complejo 2: ======");
		Map<String,Object> mapaNomCompleto = new HashMap<>();
		mapaNomCompleto.put("apPaterno", "S�nchez");
		mapaNomCompleto.put("apMaterno", "Mena");
		mapaNomCompleto.put("nombres", Arrays.asList("Jorge","Alberto") );

		Map<String,Object> mapaDireccion = new HashMap<>();
		mapaDireccion.put("calle", "Av. Insurgentes Sur");
		mapaDireccion.put("numExt", "3452A");
		mapaDireccion.put("numInt", "201" );
		mapaDireccion.put("colonia", "San Angel Inn" );
		
		Map<String,Object> mapaPersona = new HashMap<>();
		mapaPersona.put("nombreCompleto", mapaNomCompleto);
		mapaPersona.put("direccion", mapaDireccion);
		mapaPersona.put("nombreCompleto", mapaNomCompleto);
		mapaPersona.put("estudia", true);
		mapaPersona.put("cuotaMes",  3567.70);
		
		JsonObjectBuilder objBuilder = Json.createObjectBuilder(mapaPersona);
		JsonObject personaJson = objBuilder.build();
		System.out.println("JSON:" + personaJson.toString());
		
	}

	private static void testJavaToJson_Agregado() {
		System.out.println("\n========= Probando paso de objeto Java -agregado- a JSon: =========");
		Manada manada = new Manada("los pipos");
		manada.agregarPerro( new Perro("Morita", "Criollo", 28.1f) );
		manada.agregarPerro( new Perro("Pipolin", "Pitbull", 23.1f) );
		manada.agregarPerro( new Perro("Pipo", "Pitbull", 23.7f) );
		manada.agregarPerro( new Perro("Princesa", "French Poodle", 12.4f) );
		
		System.out.println(manada.toJson());
	}

	private static void testParseoArchivo() {
		System.out.println("\n================= Probando parseo de un Archivo JSon: =================");
		try (JsonReader jsonReader = Json.createReader( new FileReader("perros.json") )) {
			JsonArray arrPerrosJson = jsonReader.readArray();
			for(int i=0; i<arrPerrosJson.size(); i++) {
				System.out.println(arrPerrosJson.get(i));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void testEscribirArchivo() {
		System.out.println("\n================= Probando escribir Archivo JSon: =================");
		Manada manada = new Manada("los campeones");
		manada.agregarPerro( new Perro("Boris", "Pastor Belga", 28.1f) );
		manada.agregarPerro( new Perro("Champ", "Boxer", 21.1f) );
		manada.agregarPerro( new Perro("Lucas", "Pitbull", 12.7f) );
		manada.agregarPerro( new Perro("Roby", "Alazka Malamut", 32.1f) );
		
		try (JsonWriter jsonWriter = Json.createWriter( new FileWriter("salida.json") )) {
			jsonWriter.write(manada.toJsonObject());
			System.out.println("Revise salida.json");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void testJavaToJson() {
		System.out.println("\n================= Probando paso de Java a JSon: =================");
		Perro peli = new Perro("Peli","French poodle",15.4f);
		System.out.println(peli);
		System.out.println(peli.toJson());
		
	}

	private static void testJsonToJava(String perroJson) {
		System.out.println("\n================= Probando paso de JSon a Java: =================");
		Perro perro = new Perro(perroJson);
		System.out.println(perro);
	}

	private static void testParseoCadenaJson() {
		System.out.println("\n================= Probando el parseo de un objeto Json: =================");
		JsonReader reader = Json.createReader(new StringReader(PERRO_JSON));
		JsonObject perroJson = reader.readObject();
		System.out.println("nombre:" + perroJson.get("nombre").toString());
		System.out.println("raza:" + ((JsonString) perroJson.get("raza")).getString() );
		System.out.println("peso:" + perroJson.get("peso"));
		// OJO: Observe que el nombre se despliega conteniendo las comillas mientras la raza no
	}

	private static void testParseoCadenaJson(String cadenaJson) {
		System.out.println("\n== Probando el parseo de una cadena que puede ser objeto o arreglo: ==");
		JsonReader reader = Json.createReader(new StringReader(cadenaJson));
		JsonStructure estructuraJson = reader.read();
		ValueType tipoValor = estructuraJson.getValueType();
		if(tipoValor == ValueType.OBJECT) {
			System.out.println("Es un OBJETO");
			JsonObject perroJson = estructuraJson.asJsonObject();
			System.out.println("nombre:" + perroJson.get("nombre"));
			System.out.println("raza:" + perroJson.get("raza"));
			System.out.println("peso:" + perroJson.get("peso"));
		}
		else 
		if(tipoValor == ValueType.ARRAY){
			System.out.println("Es un ARREGLO");
			JsonArray arregloJson = estructuraJson.asJsonArray();
			for(int i=0; i<arregloJson.size(); i++) {
				System.out.println("\nobjeto i:" + i);
				JsonObject perroJson = arregloJson.getJsonObject(i);
				System.out.println("nombre:" + perroJson.get("nombre").toString());
				System.out.println("raza:" + ((JsonString) perroJson.get("raza")).getString() );
				System.out.println("peso:" + perroJson.get("peso"));				
			}
		}
		else
		   System.out.println("\nObjeto mal estructurado");
	}

}
