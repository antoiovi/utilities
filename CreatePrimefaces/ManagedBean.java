
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import com.antoiovi.project1.model.PersoneDao;
import com.antoiovi.project1.model.Persone;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class PersoneBean {

	@Inject
	private PersoneDao personeDao;

	private Persone newpersone;

	public List<Persone> allPersone;

	@PostConstruct
	public void init() {
		allPersone=new ArrayList<Persone>();
		
	}
	
 
	public List<Persone> getAllPersone() {
		return allPersone;
	}

	public void setAllPersone(List<Persone> allPersone) {
		this.allPersone = allPersone;
	}

	public Persone getNewpersone() {
		return newpersone;
	}

	public void setNewpersone(Persone newpersone) {
		this.newpersone =newpersone;
	}
}
