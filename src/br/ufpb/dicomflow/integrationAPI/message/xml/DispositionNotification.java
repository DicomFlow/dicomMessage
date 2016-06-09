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

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="object")
public class DispositionNotification extends Service {
	
	public static final String DISPOSITION_NOTIFICATION_NAME = "Storage";
	public static final String DISPOSITION_NOTIFICATION_ACTION = "Save";
	public static final String DISPOSITION_NOTIFICATION_VERSION = "1.0";
		
	private List<Notification> notifications;
	
	public DispositionNotification(){
		super.setName(DISPOSITION_NOTIFICATION_NAME);
		super.setAction(DISPOSITION_NOTIFICATION_ACTION);
		super.setVersion(DISPOSITION_NOTIFICATION_VERSION);
		super.setType(ServiceIF.DISPOSITION_NOTIFICATION);
	}

	@XmlElementWrapper(name="notifications")
	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
			

}
