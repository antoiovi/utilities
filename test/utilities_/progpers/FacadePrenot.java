package utilities_.progpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.antoiovi.util.date.AiCalendario;
import com.antoiovi.util.date.AiDate;
/***
 * Facade pattern hides the complexities of the system and provides an 
 * interface to the client using which the client can access the system. 
 * This type of design pattern comes under structural pattern as this pattern 
 * adds an interface to existing system to hide its complexities.
 * 
 * @author antoiovi
 *
 */
public class FacadePrenot {
Date start;
int ndays;
AiDate[] periodo;
	List<DecoratorPrenot> decoratorprenotList;
	List<Prenotazione> prenotazioni;
	
	public FacadePrenot(Date start_,int n,List<Prenotazione> prenotazioni_){
		decoratorprenotList=new ArrayList<DecoratorPrenot>();
		prenotazioni=prenotazioni_;
		periodo=AiCalendario.createAPeriod(start_, n);
		
		for(int x=0;x<periodo.length;x++){
			Date d=periodo[x].getDate_day();
			int npr=0;
			for(Prenotazione pr : prenotazioni){
				Date d2=pr.getGiorno();
				if(AiCalendario.sameDate(d, d2)){
					DecoratorPrenot fp=new DecoratorPrenot(d2,pr.getPerson().nome,pr.getPerson().cognome,pr.getNote());
					decoratorprenotList.add(fp);
					npr++;
				}
			}
			if(npr==0){
				DecoratorPrenot fp=new DecoratorPrenot(d,"vuoto","vuoto","vuoto");
				decoratorprenotList.add(fp);
			}
			npr=0;
			
		}
	}

	public List<DecoratorPrenot> getDecoratorprenotList() {
		return decoratorprenotList;
	}

	
	

}
