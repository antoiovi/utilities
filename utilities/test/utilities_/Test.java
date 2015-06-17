package utilities_;

import java.util.Date;

import com.antoiovi.util.date.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
AiCalendario c=new AiCalendario();
Date oggi=new Date();
AiDate[] sett=c.createAWeek(oggi);
for(int x=0;x<sett.length;x++){
	
	System.out.println(x+")"+sett[x].getStr_day_name()+sett[x].getDateAsString() );
}
		
		
	}

}
