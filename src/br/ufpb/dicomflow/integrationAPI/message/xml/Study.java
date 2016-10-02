/*
 * 	This file is part of DicomFlow.
 * 
 * 	DicomFlow is free software: you can redistribute it and/or modify
 * 	it under the terms of the GNU General Public License as published by
 * 	the Free Software Foundation, either version 3 of the License, or
 * 	(at your option) any later version.
 * 
 * 	This program is distributed in the hope that it will be useful,
 * 	but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 	GNU General Public License for more details.
 * 
 * 	You should have received a copy of the GNU General Public License
 * 	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package br.ufpb.dicomflow.integrationAPI.message.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author Danilo
 *
 */

@XmlType(name="study", propOrder={"id", "type", "description", "datetime", "size", "serie"})
public class Study {
	
	private String id;
	private String type;
	private String description;
	private String datetime;
	private long size;
	
	private List<Serie> serie;
	
	public Study(){
		this.serie = new ArrayList<Serie>();
	}

	public String getId() {
		return id;
	}

	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	@XmlAttribute
	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	@XmlAttribute
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDatetime() {
		return datetime;
	}

	@XmlAttribute
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public long getSize() {
		return size;
	}

	@XmlAttribute
	public void setSize(long size) {
		this.size = size;
	}

	public List<Serie> getSerie() {
		return serie;
	}

	public void setSerie(List<Serie> serie) {
		this.serie = serie;
	}

	public void addSerie(Serie serie){
		this.serie.add(serie);
	}
	
	
}
