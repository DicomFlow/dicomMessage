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
@XmlType(name="patient" , propOrder={"id", "name", "gender", "birthdate", "study"})
public class Patient {
	
	private String id;
	private String name;
	private String gender;
	private String birthdate;
	
	private List<Study> study;
	
	public Patient(){
		this.study = new ArrayList<Study>();
	}

	public String getId() {
		return id;
	}

	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	@XmlAttribute
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthdate() {
		return birthdate;
	}

	@XmlAttribute
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public List<Study> getStudy() {
		return study;
	}

	public void setStudy(List<Study> study) {
		this.study = study;
	}
	
	public void addStudy(Study study){
		this.study.add(study);
	}
	
	
	

}
