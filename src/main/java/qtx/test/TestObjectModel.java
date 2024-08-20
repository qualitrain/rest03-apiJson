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
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.JsonStructure;
import jakarta.json.JsonWriter;
import jakarta.json.JsonValue.ValueType;
import qtx.dominio.Manada;
import qtx.dominio.Perro;

public class TestObjectModel {
	
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
		
		testJavaToJson();
		testJsonToJava(PERRO_JSON);
		
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
		System.out.println("\n================= testCrearObjetoJson() =================");
		JsonObject objJson = Json.createObjectBuilder().add("nombre", "Firulais")
			                                           .add("raza", "Dalmata")
			                                           .add("peso", 31.5)
			                                           .build();
		System.out.println("JSON:" + objJson.toString());
	}

	private static void testCrearObjetoJsonAnidado() {
		System.out.println("\n======== testCrearObjetoJsonAnidado() ========");
		
		JsonObject vacunaJson = Json.createObjectBuilder().add("vacuna","rabia")
				                                          .add("fecha","2024-02-01")
					                                       .build();
		
		JsonObject objJson = Json.createObjectBuilder().add("nombre", "Esponja")
				                                       .add("raza", "French Poodle")
				                                       .add("peso", 15)
				                                       .add("ultVacuna", vacunaJson)
				                                       .build();
			
		System.out.println("JSON:" + objJson.toString());
	}

	private static void testCrearArregloJson() {
		System.out.println("\n====== testCrearArregloJson() ======");
		
		JsonArray arregloJson = Json.createArrayBuilder().add("lunes")
				                                         .add("martes")
				                                         .add("miercoles")
				                                         .add("jueves")
				                                         .add("viernes")
				                                         .build();		
		System.out.println("JSON:" + arregloJson.toString());
		
		String meses[] = {"enero", "febrero", "marzo", "abril", "mayo", "junio"};
		List<String> listMeses = Arrays.asList(meses);
		JsonArray mesesJson = Json.createArrayBuilder(listMeses).build();
		System.out.println("JSON:" + mesesJson.toString());
		
	}

	private static void testCrearObjetoJsonComplejo() {
		System.out.println("\n====== testCrearObjetoJsonComplejo() ======");
		
		JsonObject objNombreCompleto = Json.createObjectBuilder()
				                     .add("apPaterno", "Sanchez")
			                         .add("apMaterno","Mena")
			                         .add("nombres", Json.createArrayBuilder()
			                        		             .add("Jorge")
					    		                         .add("Alberto")
					    		                         .build())
			                         .build();
		
		JsonObject personaJson = Json.createObjectBuilder()
				                    .add("nombreCompleto", objNombreCompleto)
		                            .add("direccion", Json.createObjectBuilder()
  		   		                                          .add("calle", "Av. Insurgentes Sur")
  		   		                                          .add("numExt", "3452A")
  		   		                                          .add("numInt", "201")
  		   		                                          .add("colonia", "San Angel Inn")
  		   		                                          .build())
		          		            .add("telefonos", Json.createArrayBuilder()
			        		                              .add(Json.createObjectBuilder()
			        		                		               .add("lugar", "casa")
			        		                		               .add("numero", "55-2134-5432")
			        		                		               .build() )
			        		                              .add(Json.createObjectBuilder()
			        		                		               .add("lugar", "oficina")
			        		                		               .add("numero", "55-2233-7653")
			        		                		               .build() )
			        		                              .build())
		          		            .add("estudia",true)
		        		            .add("cuotaMes", 3567.70)
		                            .build();
		
		System.out.println("JSON:" + personaJson.toString());
		
	}
	
	private static void testCrearObjetoJsonComplejo02() {
		System.out.println("\n====== testCrearObjetoJsonComplejo02() ======");
		
		Map<String,Object> mapaNomCompleto = new HashMap<>();
		mapaNomCompleto.put("apPaterno", "Sanchez");
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
		
		JsonObject personaJson = Json.createObjectBuilder(mapaPersona)
				                     .build();
		System.out.println("JSON:" + personaJson.toString());
		
	}
	
	private static void testParseoCadenaJson() {
		System.out.println("\n================= testParseoCadenaJson() =================");
		
		JsonReader reader = Json.createReader(new StringReader(PERRO_JSON));
		JsonObject perroJson = reader.readObject();
		
		// OJO: Observe que el nombre se despliega conteniendo las comillas mientras la raza no
		System.out.println("nombre:" + perroJson.get("nombre").toString());
		System.out.println("raza:" + ((JsonString) perroJson.get("raza")).getString() );
		
		System.out.println("peso:" + perroJson.get("peso"));
	}
	
	private static void testJavaToJson() {
		System.out.println("\n================= testJavaToJson() =================");
		
		Perro peli = new Perro("Peli","French poodle",15.4f);
		System.out.println(peli);
		
		System.out.println(peli.toJson());
	}

	private static void testJsonToJava(String perroJson) {
		System.out.println("\n================= testJsonToJava("
								+ perroJson + ")=================");
		
		Perro perro = new Perro(perroJson);
		System.out.println(perro);
	}	
	
	private static void testParseoArchivo() {
		System.out.println("\n================= testParseoArchivo() =================");
		
		try (JsonReader jsonReader = Json.createReader( new FileReader("perros.json") )) {
			JsonArray arrPerrosJson = jsonReader.readArray();
			for(int i=0; i<arrPerrosJson.size(); i++) {
				System.out.println(arrPerrosJson.get(i));
			}
		} 
		catch (FileNotFoundException e) {
			System.out.println(e.getClass().getName() + ":" + e.getMessage());
		}
	}
	
	private static void testEscribirArchivo() {
		System.out.println("\n================= testEscribirArchivo() =================");
		
		Manada manada = new Manada("los campeones");
		manada.agregarPerro( new Perro("Boris", "Pastor Belga", 28.1f) );
		manada.agregarPerro( new Perro("Champ", "Boxer", 21.1f) );
		manada.agregarPerro( new Perro("Lucas", "Pitbull", 12.7f) );
		manada.agregarPerro( new Perro("Roby", "Alazka Malamut", 32.1f) );
		
		try (JsonWriter jsonWriter = Json.createWriter( new FileWriter("salida.json") )) {
			jsonWriter.write(manada.toJsonObject());
			System.out.println("Revise salida.json");
		} 
		catch (Exception e) {
			System.out.println(e.getClass().getName() + ":" + e.getMessage());
		}
	}
	
	private static void testJavaToJson_Agregado() {
		System.out.println("\n========= testJavaToJson_Agregado() =========");
		Manada manada = new Manada("los pipos");
		manada.agregarPerro( new Perro("Morita", "Criollo", 28.1f) );
		manada.agregarPerro( new Perro("Pipolin", "Pitbull", 23.1f) );
		manada.agregarPerro( new Perro("Pipo", "Pitbull", 23.7f) );
		manada.agregarPerro( new Perro("Princesa", "French Poodle", 12.4f) );
		
		System.out.println(manada.toJson());
	}

	private static void testParseoCadenaJson(String cadenaJson) {
		System.out.println("\n== testParseoCadenaJson(cadJson):Parseo de una cadena que puede ser objeto o arreglo ==");
		
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
