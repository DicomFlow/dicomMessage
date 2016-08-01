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
package br.ufpb.dicomflow.integrationAPI.mail;

import java.io.File;

import javax.mail.Message;
import javax.mail.Multipart;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public interface MailContentBuilderIF {
	
	public static final int SMTP_SIMPLE_CONTENT_STRATEGY = 1;
	
	public static final int SMTP_CIPHER_CONTENT_STRATEGY = 2;
	
	public Message buildContent(Message message, ServiceIF service);
	
	public Message buildContent(Message message, ServiceIF service, File attachment);

	public int getType();

	public ServiceIF getService(Multipart content, int type);

	public byte[] getAttach(Multipart content);
	
}
