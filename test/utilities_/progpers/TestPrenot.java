package utilities_.progpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;



import com.antoiovi.util.date.AiCalendario;
import com.antoiovi.util.date.AiDate;

public class TestPrenot {

	public static void main(String[] args) {
List<Prenotazione> prenot=new ArrayList<Prenotazione>();
		
		AiCalendario c=new AiCalendario();
		Date oggi=new Date();
		AiDate[] sett=c.createAWeek(oggi);
		for(int x=0;x<sett.length;x++){
			
			System.out.println(x+")"+sett[x].getStr_day_name()+sett[x].getDateAsString() );
		}
		
Person p1=new Person("anto","iovi");
Person p2=new Person("fra","ben");

Prenotazione pr1=new Prenotazione(new Date(), p1, "pren 1");
Prenotazione pr2=new Prenotazione(new Date(), p1, "pren 2");
Prenotazione pr3=new Prenotazione(new Date(), p2, "pren 3");
Date dd=new Date();
Calendar cal=new GregorianCalendar();
cal.add(Calendar.DATE,3);
Prenotazione pr4=new Prenotazione(cal.getTime(), p2, "pren 4");
cal.add(Calendar.DATE,1);
Prenotazione pr5=new Prenotazione(cal.getTime(), p2, "pren 4");
Prenotazione pr6=new Prenotazione(cal.getTime(), p1, "pren 4");
cal.add(Calendar.DATE,1);
Prenotazione pr7=new Prenotazione(cal.getTime(), p2, "pren 4");

prenot.add(pr1);
prenot.add(pr2);
prenot.add(pr3);
prenot.add(pr4);
prenot.add(pr5);
prenot.add(pr6);
prenot.add(pr7);

FacadePrenot ppp=new FacadePrenot(oggi, 10,prenot );

for(DecoratorPrenot fcdp: ppp.getDecoratorprenotList()){
	  System.out.print(fcdp.giorno.getDateAsString());
	  System.out.print("  "+fcdp.getName());
	  System.out.println("  "+fcdp.getCognome());
  }


System.out.println("  TEST DECORATORPRENODAY");
FacadeDecoratoPrenotDay ppdp=new FacadeDecoratoPrenotDay(new Date(), 15,prenot );
	List<DecoratorPrenotDay> lFdp=ppdp.getDecoratorprenotday();
	Iterator i=(Iterator) lFdp.iterator();

	for(DecoratorPrenotDay F : lFdp){
	Date ddx=F.getDay();
	AiDate g=F.getGiorno();
	System.out.println("Data "+g.getStr_day_name()+" "+g.getDateAsString());
	for(DecoratorPrenot Decpd: F.getDecoratorprenotList()){
		String no=Decpd.getName();
		String co=Decpd.getCognome();
		System.out.print("\t\t"+no);
  		System.out.println("\t\t"+co);
	}
	
	}
		
}
		
		
	

}
