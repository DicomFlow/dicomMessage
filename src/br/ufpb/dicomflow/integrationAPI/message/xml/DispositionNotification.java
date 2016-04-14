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
