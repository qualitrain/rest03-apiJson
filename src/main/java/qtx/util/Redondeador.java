package qtx.util;

import java.math.BigDecimal;

public class Redondeador {
	public static BigDecimal redondear(float num, int nDigitos) {
		for(int i=0; i<nDigitos; i++) {
			num *= 10;
		}
		long entero = Math.round(num);
		BigDecimal numRedondeado = new BigDecimal(entero);
		BigDecimal divisor = new BigDecimal(10);
		for(int i=0; i<nDigitos; i++) {
			numRedondeado = numRedondeado.divide(divisor);
		}

		return numRedondeado;
	}

}
