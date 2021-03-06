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

public class DispositionFields {

	//Sucess
	public static final String DISPLAYED = "displayed";
	
	//Success but minor problems occurred, which did not affect the successful data	delivery
	public static final String DISPLAYED_WARNING = "displayed/warning";
	
	//Error occurred � data could not be delivered
	public static final String DELETED_ERROR = "deleted/error";
	
	//Error occurred � data could not be delivered
	public static final String deleted = "deleted/error";
	
}
