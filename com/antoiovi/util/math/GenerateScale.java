package com.antoiovi.util.math;

public class GenerateScale {

	public static double[] Scale(double init, double end, double step) {
		int x = (int) ((end - init) / step + 1 / step);

		double a[] = new double[x];
		double s = 0;
		for (int y = 0; y < x; y++) {
			a[y] = s + step;
			s = a[y];
		}
		return a;
	}

	public static double[] ScaleLog10(double init, double end, double step) {
		int x = (int) ((end - init) / step + 1 / step);

		double a[] = new double[x];
		double s = init;
		for (int y = 0; y < x; y++) {
			a[y] = Math.log10(s);
			s = s + step;
		}
		return a;
	}

	/**
	 * Restituisce un array di double che contiene una scala logaritmica
	 * dal primo logaritmo che contiene init, fiono al successivo che contine end
	 * per esempio init = 25 -->a[0]=10
	 * 				end = 125 -->a[last]=1000
	 * init deve essere minore di end
	 * @param init	 deve essere minore di end e diverso da zero
	 * @param end				deve essere diverso da 0
	 * @return
	 */
	public static double[] ScaleLog10(double init, double end) {
		long iStart = (long) Math.log10(init);
		long iEnd = (long) Math.log10(end);
		double fPart = Math.log10(end) - iEnd;
		// se maggiore di un multiplo della base(10) arrotonda il limite al multiplo
		// successivo
		if (fPart > 0.00001)
			iEnd++;
		// numero di logaritmi
		int z = (int) iEnd - (int) iStart;
		double a[] = new double[z * 9 + 1];

		try {
			int k = 0;
			for (int x = 0; x < z; x++) {
				double d = Math.pow(10, x + iStart);
				// Il ciclo delle y finisce con 8, 
				//  10+ 10 20 30 40 50 60 70 80 
				//y     1  2  3  4  5  6  7  8 
				//quindi il ciclo yinizia con 20 e finisce con 90
				// quindi quando x=0 k=0 e a[k]=10, dopo k+1 per fare il 100
				if (x == 0) {
					a[k] = Math.log10(d);
					} else {
					a[k + 1] = Math.log10(d);
				}
				for (int y = 1; y < 9; y++) {
					// diecine+unita, - decine precedenti, in quanto 
					//	la fine di una diecina è l'inizio della successiva
					k = 10 * x + y - x;
					double dd = d + y * d;
					a[k] = Math.log10(dd);
				}
			}
			a[k + 1] = z + iStart;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
}
