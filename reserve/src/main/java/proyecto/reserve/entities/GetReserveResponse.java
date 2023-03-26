//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.03.25 a las 08:34:55 PM COT 
//


package proyecto.reserve.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="reserve" type="{http://www.example.com/demo/entities}reserve"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reserve"
})
@XmlRootElement(name = "getReserveResponse")
public class GetReserveResponse {

    @XmlElement(required = true)
    protected Reserve reserve;

    /**
     * Obtiene el valor de la propiedad reserve.
     * 
     * @return
     *     possible object is
     *     {@link Reserve }
     *     
     */
    public Reserve getReserve() {
        return reserve;
    }

    /**
     * Define el valor de la propiedad reserve.
     * 
     * @param value
     *     allowed object is
     *     {@link Reserve }
     *     
     */
    public void setReserve(Reserve value) {
        this.reserve = value;
    }

}
