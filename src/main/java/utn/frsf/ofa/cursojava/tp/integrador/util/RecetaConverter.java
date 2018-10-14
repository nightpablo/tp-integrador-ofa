package utn.frsf.ofa.cursojava.tp.integrador.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import utn.frsf.ofa.cursojava.tp.integrador.modelo.Receta;

@FacesConverter("recetaConverter")
public class RecetaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String[] datos = value.split(";");
        Receta i = new Receta();
        i.setId(Integer.valueOf(datos[0]));
        i.setTitulo(datos[1]);
        i.setDescripcion(datos[2]);
        if(!datos[3].equals("null"))
        	i.setPrecio(Double.valueOf(datos[3]));
        else
        	i.setPrecio(0.0);
        if(!datos[4].equals("null"))
        	i.setDuracionEstimada(Integer.valueOf(datos[4]));
        else
        	i.setDuracionEstimada(0);
        try {
        	if(!datos[5].equals("null"))
        		i.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse(datos[5]));
        	else
        		i.setFechaCreacion(new Date());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        return i;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}
