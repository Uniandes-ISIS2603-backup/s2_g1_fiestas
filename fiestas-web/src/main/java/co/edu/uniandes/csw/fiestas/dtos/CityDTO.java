/*
MIT License

Copyright (c) 2017 ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.CityEntity;

/**
 * CityDTO Objeto de transferencia de datos de Cities. Los DTO contienen las
 * represnetaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "name: string,
 *      "zipcode": string
 *   }
 * </pre>
 * Por ejemplo una ciudad se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 91852,
 *      "name: "Bogota, DC",
 *      "zipcode": "121110"
 *   }
 *
 * </pre>
 * @author ISIS2603
 */
public class CityDTO {

    private Long id;
    private String name;
    private String zipcode;

    /**
     * Constructor por defecto
     */
    public CityDTO() {
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param city: Es la entidad que se va a convertir a DTO
     */
    public CityDTO(CityEntity city) {
        this.id = city.getId();
        this.name = city.getName();
        this.zipcode = city.getZipcode();

    }

    /**
     * @return El ID de la ciudad
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id El nuevo ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return El nombre de la ciudad
     */
    public String getName() {
        return name;
    }

    /**
     * @param name El nuevo nombre
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return El zipcode de la ciudad
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * @param zipcode El nuevo zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public CityEntity toEntity() {
        CityEntity entity = new CityEntity();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setZipcode(this.zipcode);
        return entity;
    }
}
