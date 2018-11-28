package utilities_.progpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.antoiovi.util.date.AiCalendario;
import com.antoiovi.util.date.AiDate;

public class FacadeDecoratoPrenotDay {

	List<DecoratorPrenotDay> decoratorprenotday;
	AiDate[] periodo;

	public FacadeDecoratoPrenotDay(Date start, int ndays,
			List<Prenotazione> prenotazioni) {
		super();
		decoratorprenotday = new ArrayList<DecoratorPrenotDay>();
		periodo = AiCalendario.createAPeriod(start, ndays);
		List<Prenotazione> pren_temp=new ArrayList<Prenotazione>(prenotazioni);
		for (int x = 0; x < periodo.length; x++) {
			Date d = periodo[x].getDate_day();
			DecoratorPrenotDay fp = new DecoratorPrenotDay(d, pren_temp);
			decoratorprenotday .add(fp);
		}

	}

	public List<DecoratorPrenotDay> getDecoratorprenotday() {
		return decoratorprenotday;
	}

}
