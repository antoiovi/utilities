package utilities_.progpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.antoiovi.util.date.AiCalendario;
import com.antoiovi.util.date.AiDate;

public class DecoratorPrenotDay {
	Date day;
	AiDate giorno;
	List<DecoratorPrenot> decoratorprenotList;
	public DecoratorPrenotDay(Date day,List<Prenotazione> prenotazioni) {
		super();
		decoratorprenotList=new ArrayList<DecoratorPrenot>();
		this.day = day;
		giorno=new AiDate(day);	
		
		List<Prenotazione> tmpRemovable=new ArrayList<Prenotazione>();
		for(Prenotazione pren: prenotazioni){
			Date d=pren.getGiorno();
			if(AiCalendario.sameDate(day, d) ){
				Person pers=pren.getPerson();
				DecoratorPrenot dp=new DecoratorPrenot(day, pers.getNome(), pers.getCognome(),pren.getNote());
				decoratorprenotList.add(dp);
				tmpRemovable.add(pren);
				//prenotazioni.remove(pren);
			}
		}// for
		/**
		 * RIMUOVO LE ENTITA' INSERITE NELLA LISTA COSì EVITO IL DOPPIO INSERIMENTO 
		 * E VELOCIZZO LE ITERAZIONI SUCCESSIVE
		 */
		for(Prenotazione pren: tmpRemovable){
			prenotazioni.remove(pren);
		}
		
		if(decoratorprenotList.isEmpty()){
			DecoratorPrenot fp=new DecoratorPrenot(day,"vuoto","vuoto","vuoto");
			decoratorprenotList.add(fp);
		}

	}
	public Date getDay() {
		return day;
	}
	
	
	public List<DecoratorPrenot> getDecoratorprenotList() {
		return decoratorprenotList;
	}
	public AiDate getGiorno() {
		return giorno;
	}
	
	

	
}
