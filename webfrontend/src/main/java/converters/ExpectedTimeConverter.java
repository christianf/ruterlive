package converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ExpectedTimeConverter implements Converter{
	public ExpectedTimeConverter()
	{
		
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		// TODO Auto-generated method stub
		Date date = (Date)object;
		long timeLeft = (date.getTime() - new Date().getTime())/60000;
		if(timeLeft > 15) {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			return format.format(date);
		}
		
		else if(timeLeft<1) return new String("Nå");
		else return new String(timeLeft + " min");
	}

}
